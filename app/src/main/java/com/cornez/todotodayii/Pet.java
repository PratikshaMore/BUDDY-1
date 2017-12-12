package com.cornez.todotodayii;

class Pet{

    //MEMBER ATTRIBUTES
    private int id;
    private String name;
    private String breed;
    private int age;
    private String ownerName;
    private String imagePath;
    private String contact;

    public Pet() {
    }

    public Pet(int id, String name, String breed, int age, String ownerName, String imagePath, String contact) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.ownerName = ownerName;
        this.imagePath = imagePath;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age;}

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
