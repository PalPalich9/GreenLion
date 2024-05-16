package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

//      Класс детального просмотра препарата (и activity)

public class CheckItem extends AppCompatActivity {

    //ГРафические элементы
    private TextView textName, textVolume, textDescription, textScount, textCost;
    private ImageView image;
    private ImageButton btnBack;


    //Метод генерации элеменитов при загрузке activity CheckItem
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textName = (TextView) findViewById(R.id.textName);
        textVolume = (TextView) findViewById(R.id.textVolume);
        textDescription = (TextView) findViewById(R.id.textDescription);
        textScount = (TextView) findViewById(R.id.textScount);
        textCost = (TextView) findViewById(R.id.textCost);

        image = (ImageView) findViewById(R.id.imImage);

        btnBack = (ImageButton) findViewById(R.id.btnBack);


        //Получаем данные отправленные из других фрагментов (CatalogFtagment и BacketFragment)
        Bundle arguments = getIntent().getExtras();
        textName.setText(arguments.get("name").toString());
        textVolume.setText(String.format("Форма выпуска: %s", arguments.get("volume").toString()));
        textDescription.setText(String.format("Описание: %s",arguments.get("description").toString()));
        textScount.setText(String.format("В наличии %s шт" ,arguments.get("scount").toString()));
        textCost.setText(String.format("Цена: %s руб" ,arguments.get("cost").toString()));

        Picasso.get().load(arguments.get("image").toString()).into(image);


        //Обработчик на нажатие кнопки 'Назад'
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}