package ru.geekbrains.dungeon.game;

import lombok.Data;

@Data
public class Armour {
    public enum Type {
        // SPEAR, SWORD, MACE, AXE, BOW
        SHIELD, HELMET, CHAIN_ARMOUR
    }

    Type type;
    int[] resistance;

    public Armour(Type type, int[] resistance) {
        this.type = type;
        this.resistance = resistance;
    }
}
