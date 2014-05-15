/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.HashMap;

/**
 *
 * @author Nuwan Prabhath
 */
public class Players {

    private HashMap playerPositions;

    /*
    String data is the received string by server starting from G:P1...
    */
    
    public Players(String data) {
        this.playerPositions = new HashMap<String, Client>();
        updatePlayers(data);
    }
    
    public Players(){
        this.playerPositions = new HashMap<String, Client>();
    }

    public final void  updatePlayers(String data) {
        String[] pData;
        String[] players = data.split(":");
        for (int i = 1; i < 6; i++) {
            pData = players[i].split(";");
            Client c = new Client(pData[0], Integer.parseInt(pData[1].split(",")[0]), Integer.parseInt(pData[1].split(",")[1]), Integer.parseInt(pData[2]), Integer.parseInt(pData[3]), Integer.parseInt(pData[4]), Integer.parseInt(pData[5]), Integer.parseInt(pData[6]));
            playerPositions.put(pData[0], c);

        }

    }
    
    /*
    This will give all current player locations. Can use to update GUI
    */
    public Client getPlayerInfo(String name){
        return (Client) playerPositions.get(name);
    }
    
    public HashMap getAllPlayerInfo()
    {
        return this.playerPositions;
    }
}
