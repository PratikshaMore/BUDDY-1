package com.cornez.todotodayii;

class History{

    private int id;
    private int pid;
    private int age;
    private int weight;
    private String description;

    public History() {
    }

    public History(int id, int pid, int age, int weight, String description) {
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


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
