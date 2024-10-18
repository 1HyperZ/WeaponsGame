package Weapons;

import Entitys.Weapon;

public class Fireball extends Weapon {
    public Fireball(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean stronger(Weapon other) {
        if(other == null || other instanceof Sword)
            return true;
        return false;
    }

    
}