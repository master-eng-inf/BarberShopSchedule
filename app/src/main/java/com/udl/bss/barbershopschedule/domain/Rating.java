package com.udl.bss.barbershopschedule.domain;

/**
 * Created by Alex on 02/12/2017.
 */

public enum Rating {
    VERY_BAD(0),
    BAD(1),
    NOT_BAD(2),
    GOOD(3),
    VERY_GOOD(4);

    private final int value;

    Rating(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
