package com.adafruit.bluefruit.le.connect.Patient;

/**
 * Created by Jordan on 2/19/17.
 */

public class Exercise {
    private String name;
    private Boolean completed;
    private Boolean isLegs;

    public Exercise(String name, Boolean completed, Boolean isLegs) {
        this.name = name;
        this.completed = completed;
        this.isLegs = isLegs;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getIsLegs() {
        return this.isLegs;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
