package Weapons;

import Entitys.Weapon;

public class MagicRing extends Weapon {
    public MagicRing(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean stronger(Weapon other) {
        if(other == null || other instanceof Fireball)
            return true;
        return false;
    }
}