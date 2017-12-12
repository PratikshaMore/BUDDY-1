package com.cornez.todotodayii;

import java.io.Serializable;

@SuppressWarnings("serial")
class History implements Serializable{

    private int id;
    private int pid;
    private int age;
    private float weight;
    private String description;

    public History() {
    }

    public History(int id, int pid, int age, float weight, String description) {
        this.id = id;
        this.pid = pid;
        this.age = age;
        this.weight = weight;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
