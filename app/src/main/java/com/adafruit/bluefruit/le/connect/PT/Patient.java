package com.adafruit.bluefruit.le.connect.PT;

/**
 * Created by yhuang on 2/21/2017.
 */

public class Patient {
    private String name;
    private Integer age;
    private String gender;
    private Integer weight;
    private Integer height;
    private int priority;
    // TODO: add more info for patients

    public Patient(String name, Integer age, String gender, Integer weight, Integer height, int priority) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.priority = priority;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public Integer getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }
}
