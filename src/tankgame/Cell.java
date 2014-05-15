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
/*
Used to give path from one location to another using this cell array
*/

public class Cell {
    
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return this.x+","+this.y; 
    }
    
    
}
