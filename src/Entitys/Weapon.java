package Entitys;

import Utils.Fightingable;

public abstract class Weapon extends Entity implements Fightingable{
    public Weapon(int x, int y) {
        super(x, y);
    }
}