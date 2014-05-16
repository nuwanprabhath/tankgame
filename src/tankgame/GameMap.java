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
public class GameMap {

    String[][] map; //In map, horizontal axis is x(Columns in array) and vertical axis is y(raw in array).

    /*
     Give array of brick,stone,water locations splitted data(Last 3) from string 
     starting from I:.. received by server
     */
    public GameMap(String[] data) {
        this.map = new String[20][20];
        String[] brick = data[0].split(";");
        String[] stone = data[1].split(";");
        String[] water = data[2].split(";");

        for (String b : brick) {
            map[Integer.parseInt(b.split(",")[1])][Integer.parseInt(b.split(",")[0])] = "B";
        }
        for (String s : stone) {
            map[Integer.parseInt(s.split(",")[1])][Integer.parseInt(s.split(",")[0])] = "S";
        }
        for (String w : water) {
            map[Integer.parseInt(w.split(",")[1])][Integer.parseInt(w.split(",")[0])] = "W";
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == null) {
                    map[i][j] = "-";
                }
            }

        }
    }

    /*
     Showing map in console. Only stable obstacles.
     */
    public void showMap() {
        int raw = -1;
        for (String[] row : map) {
            raw++;
            System.out.print(raw + " ");
            for (String block : row) {
                System.out.print(block);
            }

            System.out.println("");
        }

    }

    /*
     Can use to create GUI map
     */
    public String[][] getMapArray() {
        return this.map;
    }

    /*
     This method will give a path by cells for starting and ending locations 
     ommiting obstacles. Any exceptions raised by method should be handle.
     There is timer check to stop infinite loop problems and too long calculations.
     */
    public ArrayList getPath(int stx, int sty, int endx, int endy) {
        int currentX = stx;
        int currentY = sty;
        ArrayList path = new ArrayList();
        long startTime = System.currentTimeMillis();   //To stop having infinite loops
        //map[sty][stx] = "#";
        while ((currentX != endx || currentY != endy) && (System.currentTimeMillis() - startTime) < 600) {

            if ((endx - currentX) > 0) {                //Go right

                if (map[currentY][currentX + 1] == "-") {
                    currentX++;
                    //map[currentY][currentX] = "*";
                } else {                                //Barrier
                    if ((endy - currentY) < 0) {        //should go up
                        while (map[currentY][currentX + 1] != "-" && (map[currentY - 1][currentX] == "-" )) { //go up until right side barieer finish or find horizontal barrier
                            currentY--;
                           // map[currentY][currentX] = "*";
                        }
                        if (map[currentY - 1][currentX] != "-") {             //found a horizontal barrier upside
                            while (map[currentY - 1][currentX] != "-") {      //Go left until it pass
                                currentX--;
                                //map[currentY][currentX] = "*";
                            }
                            currentY--;
                            //map[currentY][currentX] = "*";
                        }

                    } else {                                                  //Go down
                        while (map[currentY][currentX + 1] != "-" && (map[currentY + 1][currentX] == "-" || map[currentY + 1][currentX] == "*")) { //go down until right side barieer finish or find horizontal barrier * is for testing purpose
                            currentY++;
                            //map[currentY][currentX] = "*";
                        }
                        if (map[currentY + 1][currentX] != "-") {             //found a horizontal barrier downside
                            while (map[currentY + 1][currentX] != "-") {      //Go left until it pass
                                currentX--;
                                //map[currentY][currentX] = "*";
                            }
                            currentY++;                                       //After pass go one step down
                            //map[currentY][currentX] = "*";
                        }

                    }

                }

            } else if ((endx - currentX) < 0) {                                 //Go left
                if (map[currentY][currentX - 1] == "-") {
                    currentX--;
                    //map[currentY][currentX] = "*";
                } else {                                                        //Barrier
                    if ((endy - currentY) < 0) {                                //should go up
                        while (map[currentY][currentX - 1] != "-" && map[currentY - 1][currentX] == "-" ) { //go up until right side barieer finish or find horizontal barrier
                            currentY--;
                            //map[currentY][currentX] = "*";
                        }
                        if (map[currentY - 1][currentX] != "-") {               //found a horizontal barrier upside
                            while (map[currentY - 1][currentX] != "-") {        //Go left until it pass
                                currentX++;
                                //map[currentY][currentX] = "*";
                            }
                            currentY--;
                            //map[currentY][currentX] = "*";
                        }

                    } else {                                                    //Go down
                        while (map[currentY][currentX - 1] != "-" && (map[currentY + 1][currentX] == "-" || map[currentY + 1][currentX] == "*")) { //go down until right side barieer finish or find horizontal barrier * is for testing purpose
                            currentY++;
                            //map[currentY][currentX] = "*";
                        }
                        if (map[currentY + 1][currentX] != "-") {               //found a horizontal barrier downside
                            while (map[currentY + 1][currentX] != "-") {        //Go left until it pass
                                currentX++;
                                //map[currentY][currentX] = "*";
                            }
                            currentY++;                                         //After pass go one step down
                            //map[currentY][currentX] = "*";
                        }

                    }

                }

            } else if ((endx - currentX) == 0) {                                //Target is up or down

                if ((endy - currentY) < 0) {                                    //Target is up
                    if (map[currentY - 1][currentX] == "-") {
                        currentY--;
                        //map[currentY][currentX] = "*";
                    } else {
                        while (map[currentY - 1][currentX] != "-") {            //Go left until it pass
                            currentX--;

                            //map[currentY][currentX] = "*";
                        }
                        currentY--;
                        //map[currentY][currentX] = "*";

                    }

                } else {                                                        //target is down
                    if (map[currentY + 1][currentX] == "-") {
                        currentY++;
                        //map[currentY][currentX] = "*";
                    } else {                                                    //Found a barrier 
                        while (map[currentY + 1][currentX] != "-") {            //Go left until it pass
                            currentX--;
                            //map[currentY][currentX] = "*";
                        }
                        currentY++;                                             //After pass go one step down
                        //map[currentY][currentX] = "*";

                    }

                }

            }

            Cell c = new Cell(currentX, currentY);
            path.add(c);

        }
        return path;
    }

    /*
     This method will give the direction of shooting for brick walls to accure coins.
     */
    public int directionForShoot(int x, int y) {
        int cx = x;
        int cy = y;
        boolean found = false;
        int shootDirection = -1;

        cy = y - 1;
        while (found == false && cy >= 0) {
            if (map[cy][cx] == "B") {
                found = true;
                shootDirection = 0;
                break;
            }
            if (map[cy][cx] == "S") {
                break;
            }
            cy--;
        }
        cy = y + 1;
        while (found == false && cy <= 19) {
            if (map[cy][cx] == "B") {
                found = true;
                shootDirection = 2;
                break;
            }
            if (map[cy][cx] == "S") {
                break;
            }
            cy++;
        }
        cy = y;
        cx = cx + 1;
        while (found == false && cx <= 19) {
            if (map[cy][cx] == "B") {
                found = true;
                shootDirection = 1;
                break;
            }
            if (map[cy][cx] == "S") {
                break;
            }
            cx++;
        }
        cx = x;
        cx = cx - 1;
        while (found == false && cx >= 0) {
            if (map[cy][cx] == "B") {
                found = true;
                shootDirection = 3;
                break;
            }
            if (map[cy][cx] == "S") {
                break;
            }
            cx--;
        }
        return shootDirection;

    }

    /*
     This method will give any valid position that can move into. This method can 
     use if the getPath method gives error or lead in to infinite loop this method 
     can use to go to anather location and try again.
     */
    public ArrayList getAnyValidPosition(int cx, int cy) {
        ArrayList position = new ArrayList();
        boolean found = false;
        if (found == false) {
            if (map[cy - 1][cx] == "-" && (cy - 1) >= 0) {
                found = true;
                position.add(new Cell(cx, cy - 1));
            }
        }
        if (found == false) {
            if (map[cy + 1][cx] == "-" && (cy + 1) <= 19) {
                found = true;
                position.add(new Cell(cx, cy + 1));
            }
        }
        if (found == false) {
            if (map[cy][cx + 1] == "-" && (cx + 1) <= 19) {
                found = true;
                position.add(new Cell(cx + 1, cy));
            }
        }
        if (found == false) {
            if (map[cy][cx - 1] == "-" && (cx - 1) >= 0) {
                found = true;
                position.add(new Cell(cx - 1, cy));
            }
        }

        return position;
    }

}
