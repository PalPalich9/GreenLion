package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;



///
///        Класс для добавления элементов в БД FireBase
///


public class AdminDataBase extends AppCompatActivity {


    // Объявление графических элементов

    private EditText edName, edCount, edDescription, edVolume, edCost;
    private ImageView imImage;
    private DatabaseReference mDataBase;
    private StorageReference mStorageReference;

    //Хранит значение изображение
    private Uri uploadUri;
    //Ключ к таблице Medications
    private String MEDICATION_KEY = "Medications";




    // Класс создания Activity впервые
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_data_base);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    // Инициализирует графические элементы
    private void init() {
        mStorageReference = FirebaseStorage.getInstance().getReference("ImageDB");
        edName = findViewById(R.id.edName);
        edCount = findViewById(R.id.edCount);
        edDescription = findViewById(R.id.edDescription);
        edVolume = findViewById(R.id.edVolume);
        edCost = findViewById(R.id.edCost);
        mDataBase = FirebaseDatabase.getInstance().getReference(MEDICATION_KEY);
        imImage = findViewById(R.id.imImage);

    }

    //Добавляем препарат в БД при нажатии на кнопку
    public void onClickAddMedication(View view) {
        uploadImage();
    }
    public void onClickReadMedication(View view) {

    }
    public void onClickChooseImage(View view) {
        getImage();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null)
        {
            if (resultCode == RESULT_OK){
                imImage.setImageURI(data.getData());
            }
        }
    }




    //загружаем картинку
    private void uploadImage() {
        Bitmap bitmap = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference mRef = mStorageReference.child(System.currentTimeMillis() + "myImage");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
                saveMedication();
            }
        });

    }


    //Выбор картинки из проводника
    private void getImage() {
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser, 1);


    }
    //Сохраняем препарат в БД
    private void saveMedication() {
        String id = mDataBase.push().getKey();
        String name = edName.getText().toString();
        String scount = edCount.getText().toString();
        String volume = edVolume.getText().toString();
        String description = edDescription.getText().toString();
        String cost = edCost.getText().toString();
        Medication medication = new Medication(id, name, volume, description, uploadUri.toString(), scount, cost);


        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(volume)) {
            if (id != null)mDataBase.child(id).setValue(medication);

            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Пустое поле", Toast.LENGTH_SHORT).show();

        }
    }


}