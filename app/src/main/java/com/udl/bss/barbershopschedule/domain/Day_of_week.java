package com.udl.bss.barbershopschedule.domain;

/**
 * Created by Alex on 25/11/2017.
 */

public enum Day_of_week {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private final int value;

    Day_of_week(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
