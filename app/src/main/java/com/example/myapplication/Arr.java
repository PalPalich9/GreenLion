package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



//   Класс для хранения информации

public  class Arr {

    //Список препаратов, отображаемых в каталоге
    static ArrayList<Medication> medications = new ArrayList<>();


    //Список препаратов, отображаемых в корзине
    static ArrayList<BacketMedication> backetMedications = new ArrayList<>();


    //Спиок для отображение названий препаратов во вкладке 'Календарь'
    static  ArrayList<String> names = new ArrayList<>();


    //Спиок для отображение установленных уведомлений
    static ArrayList<Notif> notifs = new ArrayList<>();


    //геттер для загрузки названия препаратов в список names
    static public void getNames() {
        if (names.isEmpty()) {
            for (Medication m : medications) {
                names.add(m.getName());
            }
        }

    }


    //считываем номер последнего id для уведомлений из файла idSet
   static public String readNumFile(Context context) {
       try {
           File file = new File(context.getFilesDir(), "idSet");
           if(file.exists()) {
               FileInputStream fileInputStream;
               fileInputStream = context.openFileInput("idSet");
               InputStreamReader reader = new InputStreamReader(fileInputStream);
               BufferedReader bufferedReader = new BufferedReader(reader);
               StringBuffer stringBuffer = new StringBuffer();
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   stringBuffer.append(line);
               }
               bufferedReader.close();
               fileInputStream.close();
               return stringBuffer.toString();
           } else {
               FileOutputStream fos = context.openFileOutput("idSet", Context.MODE_PRIVATE);
               fos.write(String.valueOf(1).getBytes());
               fos.close();
               return "1";
           }
       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }








        /*try {
            FileInputStream fileInputStream;
            fileInputStream = context.openFileInput("idSet");
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

    //записываем следующий номер для уведомления в файл idSet
    static public void writeNumFile(Context context) {

        String num = readNumFile(context);

        num = Integer.toString(Integer.parseInt(num) + 1);
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("idSet", MODE_PRIVATE);
            fileOutputStream.write(num.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
