package com.example.myapplication;

import static com.example.myapplication.Arr.backetMedications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class CatalogAdapter extends ArrayAdapter<Medication> {

    //Я всё еще здесь, и всё ещё 'туда.......'
    private Context context;

    //внутренний список класса для инициализации адаптера
    private ArrayList<Medication> medications;

    //обработчик нажатия на иконку препарата
    private OnItemClickListener listener;

    //Конструктор адаптера
    public CatalogAdapter(Context context, ArrayList<Medication>  medications) {
        super(context, R.layout.catalog_layout, medications);
        this.context = context;
        this.medications = medications;
    }

    //обработчик нажатия на элемент к корзине
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.catalog_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Устанавливаем значения согласно кодексу аптеки

        viewHolder.textViewNameCatalog.setText(this.medications.get(position).getName());

        if (Integer.parseInt(this.medications.get(position).getScount()) != 0) {
            viewHolder.textViewCountCatalog.setText(String.format("В наличии %s шт.", this.medications.get(position).getScount()));
        }
        else {
            viewHolder.textViewCountCatalog.setText("Нет в наличии ");
        }


        viewHolder.textViewCostCatalog.setText(String.format("%s руб", this.medications.get(position).getCost()));


        Picasso.get().load(this.medications.get(position).getImage_id()).into(viewHolder.btnImageCatalog);


        //Обработичк на нажатие кнопки плючик (добавить в корзину)
        viewHolder.addButtonToBacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(medications.get(position).getScount()) != 0) {
                    int checkId = -1;
                    for (BacketMedication bMM : backetMedications) {
                        int index = backetMedications.indexOf(bMM);
                        if (bMM.getId() == medications.get(position).getId()) {
                            checkId = index;
                            break;
                        }
                    }

                    if ( checkId == -1) {
                        backetMedications.add(new BacketMedication(medications.get(position).getId(), 1, medications.get(position).getName(), medications.get(position).getScount(), medications.get(position).getImage_id() , medications.get(position).getCost()));
                        Toast.makeText(getContext(),"Товар добавлен в корзину", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        if ( Integer.parseInt(backetMedications.get(checkId).getScount().toString()) > backetMedications.get(checkId).getCount()) {

                            backetMedications.get(checkId).setCount(1);
                            Toast.makeText(getContext(),"Добавлен в корзину", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getContext(), "Превышение лимита", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getContext(), "Товара нет в наличии", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //Слушатель нажатий на элемент (полчаем элемент, на который нажали)
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        return convertView;
    }


    //класс для инициализации графических объектов
    private static class ViewHolder {
        final ImageView btnImageCatalog;
        final ImageButton addButtonToBacket;
        final TextView textViewCountCatalog, textViewNameCatalog, textViewCostCatalog;

        //Инициализация графических объектов
        ViewHolder(View view){
            btnImageCatalog = (ImageView) view.findViewById(R.id.btnImageCatalog);
            addButtonToBacket = (ImageButton) view.findViewById(R.id.btnAdd);
            textViewCountCatalog = (TextView) view.findViewById(R.id.textViewCountCatalog);
            textViewNameCatalog =  (TextView) view.findViewById(R.id.textViewNameCatalog);
            textViewCostCatalog =  (TextView) view.findViewById(R.id.textViewCostCatalog);

        }
    }

}
