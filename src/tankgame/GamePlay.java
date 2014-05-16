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
    static Communicator connection = Communicator.GetInstance();     //Use to communicate with the server
    static GameEngine engine = null;                                //To get the commands to send to server
    static GameMap map = null;                                      //To get shooting directions, path for location etc.
    static Players players = new Players();                  //To store and retrieve locations of the players
    static CoinManager coins = new CoinManager();            //To manage coins
    static LifePackManager lifePacks = new LifePackManager();//To manage life packs

    static boolean gameStarted = false;
    static boolean initialized = false;    //Check got message I:
    static boolean serverMessage = false;  //Check got message S:
    static String ourPlayer = null;          //To get our player from message I: and get initial by S: message

    public static void main(String[] args) {

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
                System.out.println("Game map initialized..");
            } else if (reply.charAt(0) == 'S') {
                System.out.println("Game engine initialized..");
                String[] b = reply.split(":");              //*****FOR DUMMY*** ELSE GET SINGLE S:
                for (String c : b) {
                    if (ourPlayer != null && c.contains(ourPlayer)) {
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
            } else if (reply.equals("ALREADY_ADDED#")) {
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

        if (initialized == true && serverMessage == true) {     //Checking Map and the game engine both initialized
            gameStarted = true;
            map.showMap();                                      //Showing the map
            System.out.println("Game Engine ready to start.. Engine info "+ engine.toString());
        }

        System.out.println("\n-------LET THE GAME BEGINS------\n");

        ArrayList commands = new ArrayList();                   //To store commands that should send to server
        int sendingCommand = 0;
        while (gameStarted) {

            String message = connection.receiveMessage();       //Checking send message from server
            System.out.println("Received: " + message);

            if (message.charAt(0) == 'G') {          //Global update should update players
                
                if (commands == null) {
                    commands = generateCommands();
                    sendingCommand = 0;
                } else {
                    if (sendingCommand < commands.size()) {
                        System.out.println("Start sending commands");
                        connection.sendToServer((String) commands.get(sendingCommand));
                        sendingCommand++;
                    } else {
                        commands = null;
                    }

                }

                players.updatePlayers(message);        //Update the location of all players

                //Update our location
                engine.setCurrentX(players.getPlayerInfo(engine.getName()).getX());
                engine.setCurrentY(players.getPlayerInfo(engine.getName()).getY());
                engine.setDirection(players.getPlayerInfo(engine.getName()).getDirection());

            } else if (message.charAt(0) == 'C') {          //Coin pile update 
                coins.add(message);
                System.out.println("Coin pile added");
            } else if (message.charAt(0) == 'L') {
                lifePacks.add(message);
                System.out.println("Life pack added");
            } else if(message.equals("TOO_QUICK#")){
                System.out.println("Slowing down..");
                if(sendingCommand!=0){
                sendingCommand--;
                }
            }

        }

    }

    public static ArrayList generateCommands() {
        System.out.println("AI alculating started....");
        ArrayList sendBuffer = new ArrayList();                 //Used to store commands to send to server

        //GAMING AI PART
        int shootDirection;
        try{
         shootDirection = map.directionForShoot(engine.getCurrentX(), engine.getCurrentY()); //First try to shoot from curent location
        }catch(Exception e){                                                                //Fail safe
            shootDirection=-1;
        }
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
                pathToPile = map.getAnyValidPosition(engine.getCurrentX(), engine.getCurrentY());
            }

            if (pathToPile.isEmpty() == false) {
                sendBuffer.addAll(engine.getCommandList(pathToPile));  //Add command list to go to pile(Convert pathToPile into commad list using engine)
            } else {
                System.out.println("empty path to coin");
            }

        } else if (lifePacks.lifePacksEmpty() == false) { //No coin piles to follow try for heath pack

            LifePack closestLifePack = lifePacks.getCosestLifePack(engine.getCurrentX(), engine.getCurrentY());
            ArrayList pathToLifePack;   //To ger cell array to life pack

            try {
                pathToLifePack = map.getPath(engine.getCurrentX(), engine.getCurrentY(), closestLifePack.getX(), closestLifePack.getY());
            } catch (Exception e) {
                System.out.println("Problem calculating path to life pack");
                pathToLifePack = map.getAnyValidPosition(engine.getCurrentX(), engine.getCurrentY());
            }

            if (pathToLifePack.isEmpty() == false) {
                sendBuffer.addAll(engine.getCommandList(pathToLifePack));  //Convert cell path into command array
            } else {
                System.out.println("Empty path for health");
            }

        } else {          //No health packs or coins just try to move and shoot
            ArrayList validPosition;
            validPosition = map.getAnyValidPosition(engine.getCurrentX(), engine.getCurrentY()); //get cell path to any valid cell
            if (validPosition.isEmpty() == false) {
                sendBuffer.addAll(engine.getCommandList(validPosition));
                sendBuffer.add("SHOOT#");   //just shoot
            }
        }
        
        System.out.println("AI calculation finished..");
        return sendBuffer;

    }

}
