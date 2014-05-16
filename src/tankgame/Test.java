/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Nuwan Prabhath
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        String data = "G:P0;0,0;2;0;100;0;0:P1;0,19;2;0;100;0;0:P2;19,0;0;0;100;0;0:P3;19,19;0;0;100;0;0:P4;5,5;0;0;100;0;0:7,14,0;11,18,0;15,2,0;9,6,0;13,17,0;17,4,0;1,8,0;5,12,0;19,16,0;3,7,0;13,4,0;8,5,0;12,16,0;16,3,0;0,7,0#";
//        HashMap playerPositions = new HashMap<String, Client>();
//
//        String[] pData;
//        String[] players = data.split(":");
//        for (int i = 1; i < 6; i++) {
//            System.out.println(players[i]);
//            pData = players[i].split(";");
//            Client c = new Client(pData[0], Integer.parseInt(pData[1].split(",")[0]), Integer.parseInt(pData[1].split(",")[1]), Integer.parseInt(pData[2]), Integer.parseInt(pData[3]), Integer.parseInt(pData[4]), Integer.parseInt(pData[5]), Integer.parseInt(pData[6]));
//            playerPositions.put(pData[0], c);
//
//        }
//        System.out.println("aaaaa");
//        for (Object key : playerPositions.keySet()) {
//            System.out.println(playerPositions.get(key));
//
//        }
//       CoinManager co = new CoinManager();
//       co.add("C:20,20:59517:1534#");
//       co.add("C:4,2:59517:1534#");
//       co.add("C:2,2:59517:1534#");
//       co.add("C:0,10:59517:1534#");
//       System.out.println(co.getCosestPile(10, 10));
//        String []data=new String[]{"0,2;0,0;0,19;12,3;12,4;12,5;12,6;12,7;12,8;12,9;12,10;11,3;10,3;10,2","3,0;8,0;10,0","5,5;10,10;15,15;5,6;5,7;6,7"};//brick,stone,water
//        GameMap m = new GameMap(data);
//        //ArrayList path = m.getPath(6,5,3,2);
//        m.showMap();
//        //System.out.println(m.directionForShoot(10, 1));
//        
//        GameEngine g = new GameEngine("S:P0;0,0;2#");
//        ArrayList availablePosition = m.getAnyValidPosition(11,4);
//        System.out.println(g);
//        
//        
//        Iterator i = g.getCommandList(availablePosition).iterator();
//        while(i.hasNext()){
//            System.out.println(i.next());
//        }
        /*
         Communicator connection= Communicator.GetInstance();
         String reply = connection.sendToServer("JOIN#");
         while(true){
         //connection.sendToServer("JOIN#");
         System.out.println(connection.receiveMessage());
         }
         */
//        GameMap map;
//        String reply = "I:P0:6,13;0,17;17,4;1,8;15,12;19,16;16,3;10,7;14,11;18,5;5,2;9,5;13,17;0,8;8,15;12,15;3,7;14,18;2,9:5,13;15,11;19,15;3,6;6,18;5,1;13,16;16,8;2,6;14,17;17,5;1,9;5,12;0,16;4,7;7,15;11,15;15,2;10,6;18,15;10,17;8,5:7,14;11,18;9,6;12,16;0,7;12,3;4,1;8,4;19,7;18,14#";
//        reply = reply.substring(0, reply.length() - 1);
//        String[] allData = reply.split(":");
//        String[] mData = Arrays.copyOfRange(allData, 2, allData.length); //Get last part of meaasge I: to get stone,brick, water locations
//        map = new GameMap(mData);
//        //System.out.println(map);
//        for (String a : mData) {
//            System.out.println(a);
//        }
//        map.showMap();
        
        /*
        Communicator connection = Communicator.GetInstance();     //Use to communicate with the server
        GameEngine engine = null;                                //To get the commands to send to server
        GameMap map = null;                                      //To get shooting directions, path for location etc.
        Players players = new Players();                  //To store and retrieve locations of the players
        CoinManager coins = new CoinManager();            //To manage coins
        LifePackManager lifePacks = new LifePackManager();//To manage life packs

        boolean gameStarted = false;
        boolean initialized = false;    //Check got message I:
        boolean serverMessage = false;  //Check got message S:
        String ourPlayer=null;          //To get our player from message I: and get initial by S: message

        connection.sendToServer("JOIN#");
        while (initialized == false || serverMessage == false) {    //Do the initializing map by I: and init game engine by s:
            
            //Joining the game
            String reply = connection.receiveMessage();
            if (reply.charAt(0) == 'I') {
                reply = reply.substring(0, reply.length() - 1); //removing last # sign
                String[] allData = reply.split(":");
                String[] mData = Arrays.copyOfRange(allData, 2, allData.length); //Get last part of meaasge I: to get stone,brick, water locations
                map = new GameMap(mData);                               //Initializing map of the game
                ourPlayer = allData[1];
                initialized = true;
            } else if (reply.charAt(0) == 'S') {
                
                String[] b = reply.split(":");
                for (String c : b) {
                    if ( ourPlayer!=null && c.contains(ourPlayer)) {
                        if (c.contains("#")) {
                            engine = new GameEngine(c);                  //Initializing current position and location of our client
                            serverMessage = true;
                            break;
                        } else {
                            engine = new GameEngine(c + "#"); 
                            serverMessage = true;
                            break;
                        }
                    }
                    
                }
                }else if (reply.equals("ALREADY_ADDED#")) {
                System.out.println(" Already joined ");
                break;
            } else if (reply.equals("PLAYERS_FULL#")) {
                System.out.println(" players are full!");
                break;
            } else if (reply.equals("GAME_ALREADY_STARTED#")) {
                System.out.println("Game is already started!");
                break;
            }

            }

            if (initialized == true && serverMessage == true) {
                gameStarted = true;
            }
            map.showMap();
            System.out.println("Engine info"+engine.toString());
            
            ArrayList commands = new ArrayList();
            commands.add("SHOOT#");
            commands.add("SHOOT#");
            commands.add("SHOOT#");
            connection.sendBuffer(commands);
        */
        
        
        
        }

    }
