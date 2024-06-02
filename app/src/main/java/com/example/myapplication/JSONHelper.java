package com.example.myapplication;

import android.content.Context;
import com.google.gson.Gson;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



import org.json.JSONException;



//      Класс для работы c Json файлами

class JSONHelper {

    //Куда/откуда  загружаем/получаем данные (название файла)
    private static final String FILE_NAME = "data5.json";


    //Метод загрузки данных в файл
    static boolean exportToJSON(Context context, ArrayList<Notif> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setNotif(dataList);
        String jsonString = gson.toJson(dataItems);

        try(FileOutputStream fileOutputStream =
                    context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    //Метод получения данных из файла
    static void importFromJSON(Context context) {

        File file = new File(FILE_NAME);

        // Проверка, что файл JSON не пустой
        if (file != null && file.length() > 0) {
            try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
                InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

                Gson gson = new Gson();
                DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
                Arr.notifs = dataItems.getNotif();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }



    }


    //Метод для работы со стримами
    private static class DataItems {
        private ArrayList<Notif> not;

        ArrayList<Notif> getNotif() {
            return not;
        }
        void setNotif(ArrayList<Notif> not) {
            this.not = not;
        }
    }
}