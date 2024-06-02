package com.example.myapplication;


//Класс для объектов в корзине

public class BacketMedication {

    protected String id;
    protected String scount;
    protected String name;
    protected String image_id;
    protected String cost;
    protected Integer count = 0;


    //Конструктор для FireBase
    public BacketMedication() {
    }


    //Конструктор для инициализации объекта
    public BacketMedication(String id, Integer count, String name, String scount, String image_id, String cost) {
        this.id = id;
        this.count = count;
        this.name = name;
        this.scount = scount;
        this.image_id = image_id;
        this.cost = cost;


    }


    //аксессоры
    public String getId() {
        return id;
    }
    public String getCost() {
        return cost;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count += count;
    }


    public String getScount() {
        return scount;
    }

    public String getName() {
        return name;
    }

    public String getImage_id() {
        return image_id;
    }
}
