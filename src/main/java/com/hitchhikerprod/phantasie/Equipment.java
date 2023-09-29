package com.hitchhikerprod.phantasie;

import java.util.EnumSet;

public enum Equipment {
    GLOVE(1);

    private final int diskValue;

    Equipment(int diskValue) { this.diskValue = diskValue; }

    private static Equipment from(int val) {
        return EnumSet.allOf(Equipment.class).stream()
            .filter(a -> a.diskValue == val)
            .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    private int toInt() { return diskValue; }

    public String toAbbr() {
        return name().substring(0,3);
    }

}
