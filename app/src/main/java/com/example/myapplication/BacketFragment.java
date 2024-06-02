package com.example.myapplication;

import static com.example.myapplication.Arr.backetMedications;
import static com.example.myapplication.Arr.medications;
import static com.example.myapplication.User.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



//      Класс корзины

public class BacketFragment extends Fragment {

    //Графические элементы
    private ListView listView;

    private Button btnToOrder;
    private TextView textViewSum;

    //Кастомны адаптер для отображения элементов в корзине
    private BacketAdapter adapter;


    //Базовый метод вызывающийся при создании фрагмента
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //Базовый метод вызывающийся при возобновлении отображения фрагмента
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        viewSum();

    }
    //Базовый метод вызывающийся при отображении фрагмента
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //инициализируем графические элементы
        View v =inflater.inflate(R.layout.fragment_backet, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        btnToOrder = (Button) v.findViewById(R.id.btnToOrder);
        textViewSum = (TextView) v.findViewById(R.id.textViewSum);
        viewSum();
        adapter = new BacketAdapter(getContext(), Arr.backetMedications, textViewSum);
        adapter.notifyDataSetChanged();


        //обработчик нажаия на элемент в корзине
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BacketMedication backetM = backetMedications.get(position);
                int pos = -1;
                for (Medication test : medications) {
                    if (backetM.getId() == test.getId()) {
                        pos = medications.indexOf(test);
                    }
                }

                //передаем данные о выбранном элементе в activity ChechItem
                Medication selectedItem = medications.get(pos);
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

        //устанавливем адаптер для отображенния данных в listView
        listView.setAdapter(adapter);


        //Обработчик нажатия на кнопку 'Заказ'
        btnToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!backetMedications.isEmpty()) {

                    //Передаем данные о покупателе OrderActivity
                    Intent intent = new Intent(getContext(), OrderActivity.class);
                    if (!user.isEmpty()) {
                        intent.putExtra("name", user.get(0).getName());
                        intent.putExtra("lastname", user.get(0).getLastname());
                        intent.putExtra("thirdname", user.get(0).getThirdname());
                        intent.putExtra("email", user.get(0).getEmail());
                        intent.putExtra("address", user.get(0).getAddress());
                        intent.putExtra("SNILS", user.get(0).getSNILS());
                        intent.putExtra("strahPolis", user.get(0).getStrahPolis());
                        intent.putExtra("phoneNumber", user.get(0).getPhoneNumber());
                    }
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "Пустая корзина", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    //Метод для отображения суммы заказа
    public void viewSum() {
        if(getSum(Arr.backetMedications) != 0) {
            textViewSum.setText(Integer.toString(getSum(Arr.backetMedications)));
        }
        else {
            textViewSum.setText("");
        }
    }

    //Метод получения суммы заказа
     int getSum(ArrayList<BacketMedication> bMedication) {
        int sum = 0;
        if (!bMedication.isEmpty()) {
            for (BacketMedication bm : bMedication) {
                sum += (Integer.parseInt(bm.getCost().toString())) * (Integer.parseInt(bm.getCount().toString()));
            }
        }
        return sum;
    }


}