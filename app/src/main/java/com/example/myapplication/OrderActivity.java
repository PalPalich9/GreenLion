package com.example.myapplication;

import static com.example.myapplication.Arr.backetMedications;
import static com.example.myapplication.Arr.medications;
import static com.example.myapplication.User.user;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


//      Класс для страницы оформления заказа (activity)

public class OrderActivity extends AppCompatActivity {

    //Графические элементы
    private  TextView textViewTitle, textViewEnd;
    private EditText edName, edLastname, edThirdname, edPhoneNumber, edEMail, edSNILS, edStrahPolis, edAddress;
    private ListView listView;
    private Button btnOrder, btnCancel;

    //Переменные обращения к БД
    private DatabaseReference mDataBase;
    private DatabaseReference oDataBase;
    private DatabaseReference pDataBase;

    //Ключи таблиц
    private String MEDICATION_KEY = "Medications";
    private String ORDER_KEY = "Orders";
    private String PRODUCT_KEY = "Products";




    //Метод создания activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });

        //Инициализация графических элементов
        mDataBase = FirebaseDatabase.getInstance().getReference(MEDICATION_KEY);
        oDataBase = FirebaseDatabase.getInstance().getReference(ORDER_KEY);
        pDataBase = FirebaseDatabase.getInstance().getReference(PRODUCT_KEY);


        textViewTitle =  (TextView) findViewById(R.id.textViewTitle);
        textViewEnd =  (TextView) findViewById(R.id.textViewEnd);

        edName = (EditText) findViewById(R.id.edName);
        edLastname= (EditText)findViewById(R.id.edLastname);
        edThirdname= (EditText)findViewById(R.id.edThirdname);
        edPhoneNumber= (EditText)findViewById(R.id.edPhoneNumber);
        edAddress= (EditText)findViewById(R.id.edAddress);
        edSNILS= (EditText)findViewById(R.id.edSNILS);
        edStrahPolis= (EditText)findViewById(R.id.edStrahPolis);
        edEMail= (EditText)findViewById(R.id.edEMail);

        listView= (ListView) findViewById(R.id.listView);

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnCancel = (Button) findViewById(R.id.btnCancel);


        //Если зарегистрировали пользователя, то он автоматически отобразится
        if(!user.isEmpty()) {
            Bundle arguments = getIntent().getExtras();
            edName.setText(arguments.get("name").toString());
            edEMail.setText(arguments.get("email").toString());
            edStrahPolis.setText(arguments.get("strahPolis").toString());
            edAddress.setText(arguments.get("address").toString());
            edSNILS.setText(arguments.get("SNILS").toString());
            edPhoneNumber.setText(arguments.get("phoneNumber").toString());
            edThirdname.setText(arguments.get("thirdname").toString());
            edLastname.setText(arguments.get("lastname").toString());

        }


        textViewEnd.setText(String.format("Итог:\n%s руб", Integer.toString(getSum(Arr.backetMedications))));

        OrderAdapter adapter = new OrderAdapter(this, Arr.backetMedications);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        //Обработчик нажатия на кнопку 'Отмена'
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Обработчик нажатия на кнопку 'Заказать'
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edName.getText().length() != 0
                        && edLastname.getText().length() != 0
                        && edThirdname.getText().length() != 0
                        && edEMail.getText().length() != 0
                        && edPhoneNumber.getText().length() != 0
                        && edStrahPolis.getText().length() != 0
                        && edSNILS.getText().length() != 0
                        && edAddress.getText().length() != 0) {
                    writeVoucher(edName.getText().toString(),
                            edLastname.getText().toString(),
                            edThirdname.getText().toString(),
                            edPhoneNumber.getText().toString(),
                            edEMail.getText().toString(),
                            edSNILS.getText().toString(),
                            edStrahPolis.getText().toString(),
                            edAddress.getText().toString(),
                            backetMedications);

                    writeNumFile();
                    addVoucher();
                    updateMedications();
                    finish();
                    Toast.makeText(getBaseContext(),"Заказ оформлен", Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(getBaseContext(),"Незаполненное поле", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    //Отображаем сумму заказа
    int getSum(ArrayList<BacketMedication> bMedication) {
        int sum = 0;
        for (BacketMedication bm : bMedication) {
            sum += (Integer.parseInt(bm.getCost().toString())) * (Integer.parseInt(bm.getCount().toString()));
        }
        return sum;
    }


    //Обновляем данные после оформления заказа
    public void updateMedications() {
        String iid;
        for (BacketMedication bac : Arr.backetMedications) {
            iid = bac.getId();
            for (Medication test : medications) {
                if (iid == test.getId()) {
                    String name = test.getName();
                    String scount = Integer.toString(Integer.parseInt(test.getScount()) - bac.getCount());
                    String volume = test.getVolume();
                    String description = test.getDescription();
                    String cost = test.getCost();
                    String image = test.getImage_id();

                    Medication medication = new Medication(iid, name, volume, description, image, scount, cost);

                    mDataBase.child(iid).setValue(medication);
                }
            }
        }
        backetMedications.clear();
        medications.clear();
    }

    //Добавляем заказ в БД
    public void addVoucher() {
        String id = oDataBase.push().getKey();
        String name = edName.getText().toString();
        String lastname = edLastname.getText().toString();
        String thirdname = edThirdname.getText().toString();
        String phoneNumber = edPhoneNumber.getText().toString();
        String email = edEMail.getText().toString();
        String address = edAddress.getText().toString();
        String strahPolis = edStrahPolis.getText().toString();
        String SNILS = edSNILS.getText().toString();
        String sum = Integer.toString(getSum(Arr.backetMedications));
        ArrayList<Order> orders = new ArrayList<>();
        for (BacketMedication bM : backetMedications) {
            Order o = new Order(bM);
            o.setStrCount(bM.count);
            orders.add(o);
        }

        User user1 = new User(name, lastname, thirdname, email, SNILS, strahPolis, phoneNumber, address, id, sum, orders);


        if (id != null)oDataBase.child(id).setValue(user1);
    }


    //Файл для хранения номеров заказа
    public String readNumFile() {
        try {
            FileInputStream fileInputStream = openFileInput("numSet");
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

    //Файл для генерации нового номера
    public void writeNumFile() {
        String num = readNumFile();
        num = Integer.toString(Integer.parseInt(num) + 1);
        try {
            FileOutputStream fileOutputStream = openFileOutput("numSet", MODE_PRIVATE);
            fileOutputStream.write(num.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Записываем чек в файл GreenLionVoucher
    public void writeVoucher(String name,  String lastname, String thirdname, String phoneNumber, String email, String SNILS, String strahPolis, String address, ArrayList<BacketMedication> bm) {
        String num = readNumFile();
        String text = String.format(
                "/n/n/t/tФИО: %s %s %s" +
                        "/n/n/t/te-mail:%s" +
                        "/n/n/t/tТелефон:%s" +
                        "/n/n/t/tСНИЛС:%s" +
                        "/n/n/t/tСтраховой полис:%s" +
                        "/n/n/t/tАдрес:%s",
                lastname, name, thirdname, email, phoneNumber, SNILS, strahPolis, address);
        try {
            FileOutputStream fileOutputStream = openFileOutput(String.format("GreenLionVoucher%s", num), MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}