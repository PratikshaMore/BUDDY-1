package com.cornez.todotodayii;

class Pet_Details {

    //MEMBER ATTRIBUTES
    private int _id;
    private String history;
    private String name;
    private int age;
    private int weight;
    private String ownerName;
    private String contact;
    private String Breed;
    private int Is_done;

    public Pet_Details() {
    }

    public Pet_Details(String name1, String breed, int age1, int weight1, String owner, String phone_no, String hist, int done) {

        name = name1;
        Breed = breed;
        age = age1;
        weight = weight1;
        ownerName = owner;
        contact = phone_no;
        history = hist;
        Is_done = done;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }
    public int getIs_done() {
        return Is_done;
    }

    public void setIs_done(int is_done) {
        Is_done = is_done;
    }
}
