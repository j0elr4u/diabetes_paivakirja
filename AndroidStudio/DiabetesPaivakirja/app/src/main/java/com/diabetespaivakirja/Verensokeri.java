package com.diabetespaivakirja;

public class Verensokeri {
    private int minute, hour;
    private int day, month, year;
    private double value;

    Verensokeri(double value, int minute, int hour, int day, int month, int year) {
        this.value = value;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public double getVerensokeri() {
        return this.value;
    }

    public String getVerensokeriID() {
        return "" + this.year + this.month + this.day + this.hour + this.minute;
    }


}
