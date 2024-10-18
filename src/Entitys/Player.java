package Entitys;
import java.util.LinkedList;
import java.util.Queue;

import Utils.Point;

public class Player extends Entity {
    private String name;
    private int playerNumber;
    private Queue<Weapon> weapons;

    public Player(String name, int playerNumber, int x, int y) {
        super(x, y);
        this.name = name;
        this.playerNumber = playerNumber;
        this.weapons = new LinkedList<>();
    }
    
    public Player(String name, int playerNumber, Point point) {
        super(point);
        this.name = name;
        this.playerNumber = playerNumber;
        this.weapons = new LinkedList<>();
    }

    public int getPlayerNumber(){ 
        return playerNumber; 
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public Weapon getWeapon() {            
        return weapons.peek();
    }

    public Queue<Weapon> getWeapons() {            
        return weapons;
    }

    public String getName() { return name; }

    public Weapon removeTopWeapon() {
        return weapons.poll();
    }
}