/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.util.ArrayList;

/**
 *
 * @author Nuwan Prabhath
 */
/**This class is used to manage coin piles 
*and get the closest coin pile relative to current location
*/
public class CoinManager {   

    ArrayList coinPiles;

    public CoinManager() {
        coinPiles = new ArrayList();
    }

    public void add(String data) {
        String[] pile = data.split(":");
        CoinPile c = new CoinPile(Integer.parseInt(pile[1].split(",")[0]), Integer.parseInt(pile[1].split(",")[1]), Integer.parseInt(pile[2]), Integer.parseInt(pile[3].substring(0, pile[3].length() - 1)), System.currentTimeMillis());
        coinPiles.add(c);
    }

    private void refresh() {             //Remove time out coin piles from the list 
        CoinPile coin;
        ArrayList timeoutPiles = new ArrayList();
        for (Object c : coinPiles) {
            coin = (CoinPile) c;
            if (System.currentTimeMillis() - coin.getAppearedTime() >= coin.getLt()) {
                timeoutPiles.add(coin);
            }
        }

        for (Object c : timeoutPiles) {
            coin = (CoinPile) c;
            coinPiles.remove(coin);
        }

    }
    
    public CoinPile getCosestPile(int x,int y){ //Return closest coin pile. Should use coinPileEmpty to check before use.
        this.refresh();
        double distance=801;
        CoinPile closestPile=null;
        for (Object c : coinPiles){
            CoinPile currentPile=(CoinPile) c;
            double currentDistance= getDistance(x, y, currentPile.getX(),currentPile.getY());
            if(currentDistance<distance){
                closestPile=currentPile;
                distance=currentDistance;
            }
        }
        return closestPile;
    }
    
    public double getDistance(int stx,int sty,int endx,int endy){
        
        return Math.pow((endx-stx),2)+Math.pow((endy-sty),2);
    }
    
    public boolean coinPilesEmpty(){            //To check any coin piles to follow
        return this.coinPiles.isEmpty();
    }
    
    public ArrayList getCoinArray(){     //Can use to generate GUI map coin positions
        this.refresh();
        return this.coinPiles; 
    }
    
    
    
    
    
    

}
