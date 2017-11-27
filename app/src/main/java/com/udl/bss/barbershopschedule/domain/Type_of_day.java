package com.udl.bss.barbershopschedule.domain;

/**
 * Created by Alex on 25/11/2017.
 */

public enum Type_of_day {
    NORMAL(0),
    NATIONAL_FESTIVITY(1),
    LOCAL_FESTIVITY(2),
    PERSONAL_FESTIVITY(3);

    private final int value;

    Type_of_day(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
