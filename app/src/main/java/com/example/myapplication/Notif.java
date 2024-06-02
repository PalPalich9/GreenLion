package com.example.myapplication;

import static com.example.myapplication.Arr.*;

//          Класс описания уведомления

public class Notif {
    Integer id;
    String name, time;
    long milliTime;

    public Notif() {
    }

    //Конструктор
    public Notif(Integer id, String name, String time, long milliTime) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.milliTime = milliTime;
    }

    //Аксессоры
    public Integer getId() {
        return id;
    }
    static public int setId() {
        if (!notifs.isEmpty()) {
            int iid = notifs.get(notifs.size() - 1).getId();
            return iid + 1;
        }
        return 1;
    }


    public String getName() {
        return name;
    }
    public long getMilliTime() {return milliTime;}

    public String getTime() {
        return time;
    }

}
