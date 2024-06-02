package com.example.myapplication;

import java.util.ArrayList;


//Класс для описания пользователя


public class User {
    protected String name, lastname, thirdname, SNILS, strahPolis, phoneNumber, address ,email, id, sum;
    private ArrayList<Order> order;
    static public ArrayList<User> user = new ArrayList<>();;

    public User() {
    }

    public String getId() {
        return id;
    }




    public User(String name, String lastname, String thirdname, String email, String SNILS, String strahPolis, String phoneNumber, String address, String id, String sum, ArrayList<Order> orders) {
        this.name = name;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.SNILS = SNILS;
        this.strahPolis = strahPolis;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.id = id;
        this.order = orders;
        this.sum = sum;
    }
    public User(String name, String lastname, String thirdname, String email, String SNILS, String strahPolis, String phoneNumber, String address) {
        this.name = name;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.SNILS = SNILS;
        this.strahPolis = strahPolis;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getThirdname() {
        return thirdname;
    }

    public String getSNILS() {
        return SNILS;
    }

    public String getStrahPolis() {
        return strahPolis;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
