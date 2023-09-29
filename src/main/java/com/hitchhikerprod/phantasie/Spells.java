package com.hitchhikerprod.phantasie;

import java.util.EnumSet;

public enum Spells {
    HEALING1(1), HEALING2(2), HEALING3(3), HEALING4(4),
    FIREFLASH1(5), FIREFLASH2(6), FIREFLASH3(7), FIREFLASH4(8),
    QUICKNESS1(9), QUICKNESS2(10), QUICKNESS3(11), QUICKNESS4(12),
    STRENGTH1(13), STRENGTH2(14), STRENGTH3(15), STRENGTH4(16),
    PROTECTION1(17), PROTECTION2(18), PROTECTION3(19), PROTECTION4(20),
    CONFUSION1(21), CONFUSION2(22), CONFUSION3(23), CONFUSION4(24),
    WEAKNESS1(25), WEAKNESS2(26), WEAKNESS3(27), WEAKNESS4(28),
    BINDING1(29), BINDING2(30), BINDING3(31), BINDING4(32),
    MINDBLAST1(33), MINDBLAST2(34), MINDBLAST3(35), MINDBLAST4(36),
    FLAMEBOLT1(37), FLAMEBOLT2(38), FLAMEBOLT3(39), FLAMEBOLT4(40),
    CHARM(41), SLEEP(42), TELEPORTATION(43), RESURRECTION(44),
    NINJA2(45), FEAR(46), DISSOLVE(47), SUMMON(48),
    DISPEL(49), NINJA1(50), AWAKEN(51);

    private final int diskValue;

    Spells(int diskValue) { this.diskValue = diskValue; }

    private static Spells from(int val) {
        return EnumSet.allOf(Spells.class).stream()
            .filter(a -> a.diskValue == val)
            .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    private int toInt() { return diskValue; }

    public String toAbbr() {
        return name().substring(0,3);
    }

}
