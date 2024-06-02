package com.example.myapplication;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//          Кастомный адаптер для отображения товаров на страничке оформление заказа

public class OrderAdapter extends ArrayAdapter<BacketMedication> {

    //'туда............'
    private Context context;

    //Список для создания адаптера
    public ArrayList<BacketMedication> bM;

    //Конструктор адаптера
    public OrderAdapter(Context context, ArrayList<BacketMedication> bM) {
        super(context, R.layout.order_layout, bM);
        this.context = context;
        this.bM = bM;
    }


    //Метод отображения адаптера
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final OrderAdapter.ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.order_layout, parent, false);
            viewHolder = new OrderAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (OrderAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.textViewName.setText(this.bM.get(position).getName());

        viewHolder.textViewCost.setText(String.format("%s руб", this.bM.get(position).getCost().toString()));

        viewHolder.textViewCount
                .setText(String.format("%s шт.", this.bM
                        .get(position)
                        .getCount().toString()));

        Picasso.get().load(this.bM.get(position)
                        .getImage_id())
                .into(viewHolder.image);
        return convertView;
    }


    //Класс инициализации графических элементов
    private static class ViewHolder {
        final ImageView image;
        final TextView textViewCount, textViewName, textViewCost;

        ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.image);
            textViewCount = (TextView) view.findViewById(R.id.textViewCount);
            textViewName =  (TextView) view.findViewById(R.id.textViewName);
            textViewName.setMovementMethod(new ScrollingMovementMethod());
            textViewCost =  (TextView) view.findViewById(R.id.textViewCost);
        }
    }
}
