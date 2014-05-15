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
public class LifePackManager {
    
    ArrayList lifePacks;

    public LifePackManager() {
        lifePacks= new ArrayList();
    }
    
    public void add(String data) {
        String[] pack = data.split(":");
        LifePack c = new LifePack(Integer.parseInt(pack[1].split(",")[0]), Integer.parseInt(pack[1].split(",")[1]), Integer.parseInt(pack[2].substring(0, pack[2].length() - 1)), System.currentTimeMillis());
        lifePacks.add(c);
    }
    
    private void refresh() {             //Remove time out life packs from the list 
        LifePack pack;
        ArrayList timeoutLifePacks = new ArrayList();
        for (Object p : lifePacks) {
            pack = (LifePack)p;
            if (System.currentTimeMillis() - pack.getAppearedTime() >= pack.getLt()) {
                timeoutLifePacks.add(pack);
            }
        }

        for (Object p : timeoutLifePacks) {
            pack = (LifePack) p;
            lifePacks.remove(pack);
        }

    }
    
    public boolean lifePacksEmpty(){
        return this.lifePacks.isEmpty();
        
    }
    
    public LifePack getCosestLifePack(int x,int y){ //Return closest lifePack. Should use coinPileEmpty to check before use.
        this.refresh();
        double distance=801;
        LifePack closestPack=null;
        for (Object c : lifePacks){
            LifePack currentPack=(LifePack)c;
            double currentDistance= getDistance(x, y, currentPack.getX(),currentPack.getY());
            if(currentDistance<distance){
                closestPack=currentPack;
                distance=currentDistance;
            }
        }
        return closestPack;
    }
    
    private double getDistance(int stx,int sty,int endx,int endy){
        return Math.pow((endx-stx),2)+Math.pow((endy-sty),2);
    }
    
    public ArrayList getLifePackArray(){     //Can use to generate GUI map coin positions
        this.refresh();
        return this.lifePacks; 
    }
    
    
}
