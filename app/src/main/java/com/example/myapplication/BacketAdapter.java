package com.example.myapplication;



import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//      Класс для кастомизации отображения элементов в корзине


public class BacketAdapter extends ArrayAdapter<BacketMedication> {

    //переменная для ответа на бездонный вопрос от приложения 'куда?'
    //и мы с бесконечным спокойствием, с астральной атараксией отвечаем 'туда.........'
    private Context context;

    //внутренний список класса для инициализации адаптера
    public ArrayList<BacketMedication> bM;


    //обработчик нажатия на иконку препарата
    private OnItemClickListener listener;


    //Надпись для отображении суммы заказа
    public TextView tv;


    //Конструктор адаптера
    public BacketAdapter(Context context, ArrayList<BacketMedication>  bM, TextView tv) {
        super(context, R.layout.backet_layout, bM);
        this.context = context;
        this.bM = bM;
        this.tv = tv;
    }

    //обработчик нажатия на элемент к корзине
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //стандартный метод отображения адаптера
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BacketAdapter.ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.backet_layout, parent, false);
            viewHolder = new BacketAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (BacketAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.textViewName.setText(this.bM.get(position).getName());
        viewHolder.textViewCurCount.setText(this.bM.get(position).getCount().toString());

        viewHolder.textViewCost.setText(String.format("%s руб", this.bM.get(position).getCost().toString()));

        viewHolder.textViewCount
                .setText(String.format("В наличии %s шт.", this.bM
                        .get(position)
                        .getScount()));

        Picasso.get().load(this.bM.get(position)
                .getImage_id())
                .into(viewHolder.imageBacket);


        //Обработчик нажатия на кнопку увеличения товара в корзине
        viewHolder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( Integer.parseInt(bM.get(position).getScount().toString()) > bM.get(position).getCount()) {
                    bM.get(position).setCount(1);
                    viewHolder.textViewCurCount.setText(bM.get(position).getCount().toString());

                    tv.setText(Integer.toString(getSum(bM)));


                }
                else {
                    Toast.makeText(getContext(), "Превышение лимита", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Обработчик нажатия на кнопку уменьшения товара в корзине
        viewHolder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bM.get(position).getCount()  > 1) {
                    bM.get(position).setCount(-1);
                    viewHolder.textViewCurCount.setText(bM.get(position).getCount().toString());
                    if(getSum(bM) != 0) {
                        tv.setText(Integer.toString(getSum(bM)));
                    }
                    else {
                        tv.setText("");
                    }
                }
                else {
                    bM.remove(position);
                    notifyDataSetChanged();
                    if(getSum(bM) != 0) {
                        tv.setText(Integer.toString(getSum(bM)));
                    }
                    else {
                        tv.setText("");
                    }

                    Toast.makeText(getContext(), "Успешно удален", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Обработчик нажатия на иконку удаления товара из корзины
        viewHolder.btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bM.remove(position);
                    notifyDataSetChanged();

                if(getSum(bM) != 0) {
                    tv.setText(Integer.toString(getSum(bM)));
                }
                else {
                    tv.setText("");
                }
                    Toast.makeText(getContext(), "Успешно удален", Toast.LENGTH_SHORT).show();

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


    //Метод для получения суммы заказа
    int getSum(ArrayList<BacketMedication> bMedication) {
        int sum = 0;
        for (BacketMedication bm : bMedication) {
            sum += (Integer.parseInt(bm.getCost().toString())) * (Integer.parseInt(bm.getCount().toString()));
        }
        return sum;
    }

    //класс для инициализации графических объектов
    private static class ViewHolder {
        final ImageView imageBacket;
        final ImageButton btnTrash;
        final TextView textViewCount, textViewName, textViewCurCount, textViewCost;
        final Button btnDecrement, btnIncrement;



        //Инициализация графических объектов
        ViewHolder(View view){
            imageBacket = (ImageView) view.findViewById(R.id.imageBacket);
            btnTrash = (ImageButton) view.findViewById(R.id.btnTrash);
            textViewCount = (TextView) view.findViewById(R.id.textViewCount);
            textViewName =  (TextView) view.findViewById(R.id.textViewName);
            textViewName.setMovementMethod(new ScrollingMovementMethod());
            textViewCost =  (TextView) view.findViewById(R.id.textViewCost);
            textViewCurCount =  (TextView) view.findViewById(R.id.textViewCurCount);
            btnDecrement =  (Button) view.findViewById(R.id.btnDecremet);
            btnIncrement =  (Button) view.findViewById(R.id.btnIncrement);

        }
    }
}
