/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Nuwan Prabhath
 */

/*
This class is used to get requred commands to send to server.
*/
public class GameEngine {

    private int direction;//0-Noath, 1-east, 2- south, 3-West
    private String name;
    private int currentX;
    private int currentY;
    
    /*
     data is the string get after joining the game S:P<num>; < player location  x>
     ,< player location  y>:<Direction>#
    S:P0;0,0;2#
     */

    public GameEngine(String data) {
        String[] info = data.split(";");
        this.name = info[0];
        this.currentX = Integer.parseInt(info[1].split(",")[0]);
        this.currentY = Integer.parseInt(info[1].split(",")[1]);
        this.direction = Integer.parseInt(info[2].substring(0, info[2].length() - 1));
    }

    /*
     Input is the path created by cell arrayList using GameMap.getPath() and 
     returns command list to send to server directly
     */
    public ArrayList getCommandList(ArrayList path) {
        int cx = this.currentX;
        int cy = this.currentY;
        int cdirection = this.direction;
        ArrayList commandList = new ArrayList(); //Hold final command to go to target
        for (Object c : path) {
            Cell cu = (Cell) c;
            ArrayList nextStep = getRotateDirection(cdirection, cx, cy, cu.getX(), cu.getY());
            commandList.addAll(nextStep);
            cx = cu.getX();
            cy = cu.getY();

            if (cdirection == 0) {
                if (nextStep.contains("LEFT#")) {
                    cdirection = 3;
                } else if (nextStep.contains("RIGHT#")) {
                    cdirection = 1;
                } else if (nextStep.contains("DOWN#")) {
                    cdirection = 2;
                }

            } else if (cdirection == 1) {
                if (nextStep.contains("LEFT#")) {
                    cdirection = 0;
                } else if (nextStep.contains("RIGHT#")) {
                    cdirection = 2;
                } else if (nextStep.contains("DOWN#")) {
                    cdirection = 3;
                }
            } else if (cdirection == 2) {
                if (nextStep.contains("LEFT#")) {
                    cdirection = 1;
                } else if (nextStep.contains("RIGHT#")) {
                    cdirection = 3;
                } else if (nextStep.contains("DOWN#")) {
                    cdirection = 0;
                }
            } else if (cdirection == 3) {
                if (nextStep.contains("LEFT#")) {
                    cdirection = 2;
                } else if (nextStep.contains("RIGHT#")) {
                    cdirection = 0;
                } else if (nextStep.contains("DOWN#")) {
                    cdirection = 1;
                }
            }

        }
        return commandList;
    }

    /*
    This method is used to get commands to rotate and go to any desired adjasent
    cell.This method is mostly used by method getCommandList() in this class.
    */
    private ArrayList getRotateDirection(int current, int cx, int cy, int nx, int ny) {
        ArrayList command = new ArrayList();

        if (ny == cy - 1) {//should go noath
            if (current == 0) {
                command.add("UP#");
            } else if (current == 1) {
                command.add("LEFT#");
                command.add("UP#");
            } else if (current == 2) {
                command.add("DOWN#");
                command.add("UP#");
            } else if (current == 3) {
                command.add("RIGHT#");
                command.add("UP#");
            }

        } else if (ny == cy + 1) {//should go south
            if (current == 0) {
                command.add("DOWN#");
                command.add("UP#");
            } else if (current == 1) {
                command.add("RIGHT#");
                command.add("UP#");
            } else if (current == 2) {
                command.add("UP#");
            } else if (current == 3) {
                command.add("LEFT#");
                command.add("UP#");
            }

        } else if (nx == cx + 1) { //should go east 
            if (current == 0) {
                command.add("RIGHT#");
                command.add("UP#");
            } else if (current == 1) {
                command.add("UP#");
            } else if (current == 2) {
                command.add("LEFT#");
                command.add("UP#");
            } else if (current == 3) {
                command.add("DOWN#");
                command.add("UP#");
            }

        } else if (nx == cx - 1) {  //should go west

            if (current == 0) {
                command.add("LEFT#");
                command.add("UP#");
            } else if (current == 1) {
                command.add("DOWN#");
                command.add("UP#");
            } else if (current == 2) {
                command.add("RIGHT#");
                command.add("UP#");
            } else if (current == 3) {
                command.add("UP#");
            }

        }
        return command;

    }
    
    /*
    This method will give required command to rotate tank to given direction.
    Specially for shooting. Should check current direction is diffent from required 
    direction. If different use this method to change otherwie it will returns null.
    Direction can be taken by GameMap.directionForShoot()
    */
    public String rotate(int newDirection){
        String commandToRotate = null;             
        if(this.direction==0){
            if(newDirection==1){
                commandToRotate="RIGHT#";
            }else if(newDirection==2){
                commandToRotate="DOWN#";
            }else if(newDirection==3){
                commandToRotate="LEFT#";
            }
        }else if(this.direction==1){
            if(newDirection==2){
                commandToRotate="RIGHT#";
            }else if(newDirection==3){
                commandToRotate="DOWN#";
            }else if(newDirection==0){
                commandToRotate="LEFT#";
            }
        }else if(this.direction==2){
            if(newDirection==3){
                commandToRotate="RIGHT#";
            }else if(newDirection==0){
                commandToRotate="DOWN#";
            }else if(newDirection==1){
                commandToRotate="LEFT#";
            }
        }else if(this.direction==3){
            if(newDirection==0){
                commandToRotate="RIGHT#";
            }else if(newDirection==1){
                commandToRotate="DOWN#";
            }else if(newDirection==2){
                commandToRotate="LEFT#";
            }
        }
        
        
        return commandToRotate; 
    }

    public int getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.currentX + "," + this.currentY + ":" + this.direction;
    }

}
