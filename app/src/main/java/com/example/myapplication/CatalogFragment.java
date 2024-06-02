package com.example.myapplication;


import static com.example.myapplication.Arr.medications;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;


//      Класс каталога

public class CatalogFragment extends Fragment {

    //графические элементы
    private ListView listView;

    private DBManager dbManager = new DBManager();

    //Адаптер
    private CatalogAdapter adapter;



    //Открываем БД
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager.initDB();
    }

    //При возвращении в каталог обновляем данные адаптера (самого каталога)
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }

    //Инициализируем графические объекты
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_catalog, container, false);
        listView = (ListView) v.findViewById(R.id.listViewCatalog);
        adapter = new CatalogAdapter(getContext(), medications);

        //Загружаем данные из БД в список
        dbManager.getDataFromDB(medications, adapter);


        //Обработчик нажаия на иконку препарата
        adapter.setOnItemClickListener(new OnItemClickListener() {

            //Передаем данные в сешмшен СруслШеуь
            @Override
            public void onItemClick(int position) {
                Medication selectedItem = medications.get(position);
                Intent intent = new Intent(getContext(), CheckItem.class);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("name", selectedItem.getName());
                intent.putExtra("scount", selectedItem.getScount());
                intent.putExtra("volume", selectedItem.getVolume());
                intent.putExtra("description", selectedItem.getDescription());
                intent.putExtra("image", selectedItem.getImage_id());
                intent.putExtra("cost", selectedItem.getCost());

                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);

        return v;
    }


}