package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;




//      Класс отображения формы заполнения данных о пользователе


public class UserFragment extends Fragment {



    //Графические элементы
    private EditText edName, edLastname, edThirdname, edPhoneNumber, edSNILS, edStrahPolis, edAddress, edEMail;

    private Button btnSaveUser;

    private ImageView image;


    //Метод генерации фрагмента
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Генерация графических элементов
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        edName = v.findViewById(R.id.edName);
        edLastname = v.findViewById(R.id.edLastname);
        edThirdname = v.findViewById(R.id.edThirdname);

        edPhoneNumber = v.findViewById(R.id.edPhoneNumber);
        edSNILS = v.findViewById(R.id.edSNILS);
        edStrahPolis = v.findViewById(R.id.edStrahPolis);
        edAddress = v.findViewById(R.id.edAddress);
        edEMail = v.findViewById(R.id.edEMail);


        image = v.findViewById(R.id.imageView);

        image.setImageResource(R.drawable.greean_user);

        btnSaveUser = v.findViewById(R.id.btnSaveUser);


        //Обработчик нажатия на кнопку 'Сохранить'
        btnSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edName.getText().length() != 0
                        && edLastname.getText().length() != 0
                        && edThirdname.getText().length() != 0
                        && edPhoneNumber.getText().length() != 0
                        && edAddress.getText().length() != 0
                        && edSNILS.getText().length() == 11
                        && edStrahPolis.getText().length() == 16
                        && edEMail.getText().length() != 0) {

                    if (User.user.isEmpty()) {
                        User.user.add(new User(
                                edName.getText().toString(),
                                edLastname.getText().toString(),
                                edThirdname.getText().toString(),
                                edEMail.getText().toString(),
                                edSNILS.getText().toString(),
                                edStrahPolis.getText().toString(),
                                edPhoneNumber.getText().toString(),
                                edAddress.getText().toString()));
                        Toast.makeText(getContext(), "Пользователь создан", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User.user.set(0, new User(
                                edName.getText().toString(),
                                edLastname.getText().toString(),
                                edThirdname.getText().toString(),
                                edEMail.getText().toString(),
                                edSNILS.getText().toString(),
                                edStrahPolis.getText().toString(),
                                edPhoneNumber.getText().toString(),
                                edAddress.getText().toString()));
                        Toast.makeText(getContext(), "Пользователь изменен", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (edStrahPolis.getText().length() != 16) {
                        Toast.makeText(getContext(), "Длина страх полиса - 16", Toast.LENGTH_SHORT).show();

                    }
                    if (edSNILS.getText().length() != 11) {
                        Toast.makeText(getContext(), "Длина СНИЛС - 11", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getContext(), "Незаполненное поле", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
        return v;
    }
}