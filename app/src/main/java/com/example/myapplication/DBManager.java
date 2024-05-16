package com.example.myapplication;


import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


//          Класс для работы с БД

public class DBManager {

    //Переменные БД
    private DatabaseReference mDataBase;

    //Ключ к таблице препараты
    private String MEDICATION_KEY = "Medications";

    //Метод подключения к БД
    public void initDB() {
        mDataBase = FirebaseDatabase.getInstance().getReference(MEDICATION_KEY);
    }

    //Метод загрузки данных из таблицы в список medications
    public void getDataFromDB(ArrayList<Medication> medications, ArrayAdapter<Medication> adapter) {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (medications != null && medications.size() > 0) medications.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Medication medication = ds.getValue(Medication.class);
                    assert medication != null;
                    medications.add( new Medication(medication));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        mDataBase.addValueEventListener(vListener);
    }
}
