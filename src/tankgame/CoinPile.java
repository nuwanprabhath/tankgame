/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tankgame;

/**
 *
 * @author Nuwan Prabhath
 */
public class CoinPile {
    private int x;
    private int y;
    private int lt;                      //Life time
    private int val;                     //value
    private long appearedTime;           //to remove old dissapeared coin piles.

    public CoinPile(int x, int y, int lt, int val, long appearedTime) {
        this.x = x;
        this.y = y;
        this.lt = lt;
        this.val = val;
        this.appearedTime = appearedTime;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLt() {
        return lt;
    }

    public int getVal() {
        return val;
    }

    public long getAppearedTime() {
        return appearedTime;
    }
    
    @Override
    public String toString(){
        return this.x+","+this.y+":"+this.lt+":"+this.val+":"+this.appearedTime;
    }
    
}
