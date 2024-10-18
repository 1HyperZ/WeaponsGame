package Weapons;

import Entitys.Weapon;

public class Sword extends Weapon {
    public Sword(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean stronger(Weapon other) {
        if(other == null || other instanceof MagicRing)
            return true;
        return false;
    }
}