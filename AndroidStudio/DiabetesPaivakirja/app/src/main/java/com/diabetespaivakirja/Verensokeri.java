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

    Verensokeri() {
        this.value = -1;
        this.minute = -1;
        this.hour = -1;
        this.day = -1;
        this.month = -1;
        this.year = -1;
    }

    public double getVerensokeri() {
        return this.value;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public String getVerensokeriID() {
        return "" + this.year + this.month + this.day + this.hour + this.minute;
    }


}
