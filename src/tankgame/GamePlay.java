/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Nuwan Prabhath
 */

/*
 This class is used to play the game using communicator class which use TCP
 */
public class GamePlay {

    /**
     * @param args the command line arguments
     */
    ;

    public static void main(String[] args) {
        Communicator connection = new Communicator();     //Use to communicate with the server
        GameEngine engine = null;                                //To get the commands to send to server
        GameMap map = null;                                      //To get shooting directions, path for location etc.
        Players players = new Players();                  //To store and retrieve locations of the players
        CoinManager coins = new CoinManager();            //To manage coins
        LifePackManager lifePacks = new LifePackManager();//To manage life packs

        boolean gameStarted = false;

        //Joining the game
        String reply = connection.sendToServer("JOIN#");
        if (reply.equals("PLAYERS_FULL#")) {
            System.out.println(" players are full");
        } else if (reply.equals("ALREADY_ADDED#")) {
            System.out.println(" Already joined ");

        } else if (reply.equals("GAME_ALREADY_STARTED#")) {
            System.out.println("Game is already started");
        } else {                                              //successfull connection
            engine = new GameEngine(reply);                  //Initializing current position and location of our client
            gameStarted = true;
        }

        //This is only a prototype of how commands should send. SHOULD
        //NOT BE IN THIS FORMAT.
        String replyAfterJoin = connection.receiveMessage();  //This message(Starting form I: ) contains the initializing all map and players
        String[] mData;                                       //But only map will update player location will added by G: message
        String[] allData = replyAfterJoin.split(":");
        mData = Arrays.copyOfRange(allData, 6, allData.length); //Get last part of meaasge I: to get stone,brick, water locations
        map = new GameMap(mData);                               //Initializing map of the game

        //This SHOULD NOT BE DONE LIKE THIS. CAN USE OBSERVERS TO CHECK NEW MESSAGES FROM SERVER
        
        while (gameStarted) {
            
            String message = connection.receiveMessage();       //Checking send message from server
            
            
            if (message == null) {                         //When there is no message to handle callculate the next step
                ArrayList sendBuffer = new ArrayList();                 //Used to store commands to send to server
                
                
                
                //GAMING PART
                if(connection.isSendBufferEmpty()){  // Only do calculations and play if previous commands were  sent
                    
                
                    int shootDirection = map.directionForShoot(engine.getCurrentX(), engine.getCurrentY()); //First try to shoot prom curent location
                    if (shootDirection != -1) {                                    //Check is there target to shoot
                        if (engine.getDirection() != shootDirection) {             //Check current direction is shooting direction
                            String commandChangeDiretion = engine.rotate(shootDirection); //If not get change direction command
                            sendBuffer.add(commandChangeDiretion);
                            sendBuffer.add("SHOOT#");                              //shoot two times
                            sendBuffer.add("SHOOT#");
                        } else {                                                   //Same direction then shoot
                            sendBuffer.add("SHOOT#");
                            sendBuffer.add("SHOOT#");
                        }
                    }

                    //After checking for shoot then try to move to coin pile or health pack
                    if (coins.coinPilesEmpty() == false) { //Check any coin piles there
                        CoinPile closestPile = coins.getCosestPile(engine.getCurrentX(), engine.getCurrentY());
                        ArrayList pathToPile;       //To get cell array of path to get commad list


                        try {
                            pathToPile = map.getPath(engine.getCurrentX(), engine.getCurrentY(), closestPile.getX(), closestPile.getY());
                        } catch (Exception e) {
                            System.out.println("Problem calculating path to coin");
                            pathToPile=map.getAnyValidPosition(engine.getCurrentX(), engine.getCurrentY());
                        }

                        if(pathToPile.isEmpty()==false){
                            sendBuffer.addAll(engine.getCommandList(pathToPile));  //Add command list to go to pile(Convert pathToPile into commad list using engine)
                        }else{
                            System.out.println("empty path to coin");
                        }

                    }else if(lifePacks.lifePacksEmpty()==false){ //No coin piles to follow try for heath pack

                        LifePack closestLifePack = lifePacks.getCosestLifePack(engine.getCurrentX(), engine.getCurrentY());
                        ArrayList pathToLifePack;   //To ger cell array to life pack

                        try{
                            pathToLifePack = map.getPath(engine.getCurrentX(), engine.getCurrentY(), closestLifePack.getX(), closestLifePack.getY());
                        }catch(Exception e){
                            System.out.println("Problem calculating path to life pack");
                            pathToLifePack=map.getAnyValidPosition(engine.getCurrentX(), engine.getCurrentY());
                        }

                        if(pathToLifePack.isEmpty()==false){
                            sendBuffer.addAll(engine.getCommandList(pathToLifePack));  //Convert cell path into command array
                        }else{
                            System.out.println("Empty path for health");
                        }

                    }else{          //No health packs or coins just try to move and shoot
                        ArrayList validPosition;
                        validPosition=map.getAnyValidPosition(engine.getCurrentX(), engine.getCurrentY()); //get cell path to any valid cell
                        if(validPosition.isEmpty()==false){
                            sendBuffer.addAll(engine.getCommandList(validPosition));
                            sendBuffer.add("SHOOT#");   //just shoot
                        }
                    }
                    
                    connection.sendBuffer(sendBuffer);  //Sending command list to server
                
                }
                
                
                

            } else if (message.charAt(0) == 'G') {          //Global update should update players
                players.updatePlayers(message);        //Update the location of all players
                
                //Update our location
                engine.setCurrentX(players.getPlayerInfo(engine.getName()).getX());
                engine.setCurrentY(players.getPlayerInfo(engine.getName()).getY());
                engine.setDirection(players.getPlayerInfo(engine.getName()).getDirection());
                
            } else if (message.charAt(0) == 'C') {          //Coin pile update 
                coins.add(message);
            } else if (message.charAt(0) == 'L') {
                lifePacks.add(message);
            }

        }

    }

}
