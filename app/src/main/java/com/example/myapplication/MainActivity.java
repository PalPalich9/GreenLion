package com.example.myapplication;

import android.os.Bundle;
import android.view.View;



import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


//          Класс основной страницы (Header)


public class MainActivity extends AppCompatActivity {

    //фрагменты
    private CatalogFragment catalogFragment = new CatalogFragment();
    private BacketFragment backetFragment = new BacketFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private UserFragment userFragment = new UserFragment();



    //Метод создания activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setNewFragment(catalogFragment);
        readNumFile();
    }

    //Метод установки нового фрагмента
    public void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    //Обработчик нажатия на корзину
    public void onClickBacket(View view) {

        setNewFragment(backetFragment);
    }

    //Обработчик нажатия на инконку Львенка
    public void onClickCatalog(View view) {

        setNewFragment(catalogFragment);
    }

    //Обработчик нажатия на иконку календаря
    public void onClickCalendar(View view) {

        setNewFragment(calendarFragment);
    }

    //Обработчик нажатия на иконку пользователя
    public void onClickUser(View view) {
        setNewFragment(userFragment);
    }

    //Метод чтения данных из файла idSet для получения id уведомлений
    public String readNumFile() {
        try {
            FileInputStream fileInputStream = openFileInput("idSet");
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
        }

    }

}