package com.example.myapplication;



//          Класс описания препаратов
public class Medication {
    private String id, name, volume, description, image_id, scount, cost;


    //Конструктор для FireBase
    public Medication() {

    }


    //Конструктор препарата
    public Medication(String id, String name, String volume, String description, String image_id, String scount, String cost) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.description = description;
        this.image_id = image_id;
        this.scount = scount;
        this.cost = cost;
    }

        public Medication(Medication med) {
        this.id = med.id;
        this.name = med.name;
        this.volume = med.volume;
        this.description = med.description;
        this.image_id = med.image_id;
        this.scount = med.scount;
        this.cost = med.cost;
        }

        //Аксессоры
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVolume() {
        return volume;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_id() {
        return image_id;
    }

    public String getScount() {
        return scount;
    }

    public String getCost() {
        return cost;
    }


}
