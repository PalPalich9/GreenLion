package com.example.myapplication;

//          Класс описания заказа
public class Order extends BacketMedication{
    String strCount;

    //Конструктор для FireBase
    public Order() {

    }

    //Конструктор заказа
    public Order(BacketMedication bM) {
        this.id = bM.getId();
        this.name = bM.getName();
        this.cost = bM.getCost();
        this.count = bM.getCount();
    }
    //Сэттер
    public void setStrCount(Integer strCount) {
        this.strCount = Integer.toString(strCount);
    }
}
