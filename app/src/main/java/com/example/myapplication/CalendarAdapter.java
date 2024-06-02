package com.example.myapplication;


import android.content.Context;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;




import java.util.ArrayList;


//     Класс для кастомизации отображения поставленных уведомлений

public class CalendarAdapter extends ArrayAdapter<Notif> {


    //снова и снова, снова и снова, снова и снова нужно отвечать 'туда.......'
    private Context context;

    //внутренний список класса для инициализации адаптера
    public ArrayList<Notif> notif;

    //Конструктор адаптера
    public CalendarAdapter(Context context, ArrayList<Notif>  notif) {
        super(context, R.layout.calendar_layout, notif);
        this.context = context;
        this.notif = notif;
    }

    //стандартный метод отображения адаптера
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final CalendarAdapter.ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.calendar_layout, parent, false);
            viewHolder = new CalendarAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (CalendarAdapter.ViewHolder) convertView.getTag();
        }


        //устанавливаем значения названия препарата и времени, когда его нужно принять
        viewHolder.textViewName.setText(this.notif.get(position).getName());


        viewHolder.textViewTime.setText(this.notif.get(position).getTime());


        //Вечный памятник тому, чему не суждено сбыться, потому что я выбрал быть счастливым

        /*viewHolder.btnDeleteNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                *//*NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                Integer notifyId = notifs.get(position).getId() - 1;
                notificationManager.cancel(notifyId);*//*
                notifs.remove(position);
                notifyDataSetChanged();

                Toast.makeText(getContext(), "Уведомление удалено", Toast.LENGTH_SHORT).show();
            }
        });*/


        return convertView;
    }

    //класс для инициализации графических объектов
    private static class ViewHolder {
        final TextView textViewName, textViewTime;
        //final Button btnDeleteNotification;


        ViewHolder(View view){

            textViewName =  (TextView) view.findViewById(R.id.textViewName);
            textViewName.setMovementMethod(new ScrollingMovementMethod());
            textViewTime =  (TextView) view.findViewById(R.id.textViewTime);
           // btnDeleteNotification =  (Button) view.findViewById(R.id.btnDeleteNotification);
        }
    }
}

