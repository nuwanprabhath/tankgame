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
 This class is used to store different client information and mainly used by Players 
 class
 */
public class Client {

    private String name;
    private int x;
    private int y;
    private int shot;
    private int health;
    private int coins;
    private int points;
    private int direction;

    public Client(String name, int x, int y, int direction, int shot, int health, int coins, int points) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.shot = shot;
        this.health = health;
        this.coins = coins;
        this.points = points;
        this.direction = direction;
    }

    public void update(String name, int x, int y, int direction, int shot, int health, int coins, int points) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.shot = shot;
        this.health = health;
        this.coins = coins;
        this.points = points;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int isShot() {
        return shot;
    }

    public int getHealth() {
        return health;
    }

    public int getCoins() {
        return coins;
    }

    public int getPoints() {
        return points;
    }

    public int getShot() {
        return shot;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return this.name + ";" + this.x + "," + this.y + ";" + this.direction + ";" + this.shot + ";" + this.health + ";" + this.coins + ";" + this.points;
    }

}
