/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.ArrayList;
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

        String []data=new String[]{"0,2;0,0;0,19;12,3;12,4;12,5;12,6;12,7;12,8;12,9;12,10;11,3;10,3;10,2","3,0;8,0;10,0","5,5;10,10;15,15;5,6;5,7;6,7"};//brick,stone,water
        GameMap m = new GameMap(data);
        //ArrayList path = m.getPath(6,5,3,2);
        m.showMap();
        //System.out.println(m.directionForShoot(10, 1));
        
        GameEngine g = new GameEngine("S:P1:11,4:0#");
        ArrayList availablePosition = m.getAnyValidPosition(11,4);
        //System.out.println(g);
        
        
        Iterator i = g.getCommandList(availablePosition).iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
        
        
        
    }

}
