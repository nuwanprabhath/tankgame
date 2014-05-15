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
public class LifePack {
    private int x;
    private int y;
    private int lt;
    private long appearedTime;

    public LifePack(int x, int y, int lt, long appearedTime) {
        this.x = x;
        this.y = y;
        this.lt = lt;
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

    public long getAppearedTime() {
        return appearedTime;
    }
    
    @Override
    public String toString(){
        return this.x+","+this.y+":"+this.lt+":"+this.appearedTime;
    }
    
}
