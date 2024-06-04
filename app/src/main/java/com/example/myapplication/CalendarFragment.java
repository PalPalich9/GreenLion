package com.example.myapplication;




import static com.example.myapplication.Arr.names;
import static com.example.myapplication.Arr.notifs;


import android.app.AlarmManager;



import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;


//  Класс календаря

public class CalendarFragment extends Fragment {


    //переменные графических обхектов
    private CalendarAdapter adapter;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner spinner;
    private Button btnSaveNotification;
    private ListView listView;

    //При создании загружаем данные о установленных уведолениях ранее
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         notifs = JSONHelper.importFromJSON(getContext());

    }

    //Не показываем уведомления, которые уже в прошлом
    public void curItems() {
        ArrayList<Notif> del = new ArrayList<>();
        if (!notifs.isEmpty()) {
            for (Notif nt : notifs) {
                if (nt.getMilliTime() < System.currentTimeMillis() + 60000) {
                    //notifs.remove(notifs.indexOf(nt));
                    del.add(nt);
                }
            }
            for (Notif nt : del) {
                notifs.remove(notifs.indexOf(nt));
            }
        }

    }


    //Метод возобновляющий данные фрагмента
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        curItems();

    }

    //Метод генерации фрагемента
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        btnSaveNotification = (Button) v.findViewById(R.id.btnSaveNotification);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        timePicker = (TimePicker) v.findViewById(R.id.timePicker);

        adapter = new CalendarAdapter(getContext(), Arr.notifs);
        adapter.notifyDataSetChanged();

        Arr.getNames();

        ArrayAdapter<String> spinAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, names);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinAdapter);

        listView.setAdapter(adapter);



        //Обработчик нажатия на кнопку 'Сохранить'
        btnSaveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        timePicker.getHour(), timePicker.getMinute());

                if (calendar.getTimeInMillis() < System.currentTimeMillis() + 60000) {
                    Toast.makeText(getContext(), "Невозможно создать уведомление на прошедшее время", Toast.LENGTH_SHORT).show();
                }
                else {
                    notifs.add(new Notif(Integer.parseInt(Arr.readNumFile(getContext())) - 1, spinner.getSelectedItem().toString(), getNormalTime(datePicker, timePicker), calendar.getTimeInMillis()));
                    Arr.writeNumFile(getContext());
                    JSONHelper.exportToJSON(getContext(), notifs);
                    adapter.notifyDataSetChanged();
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();
                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();
                    createNotification();



                    Toast.makeText(getContext(), "Добавлено уведомление", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }


    //Конвертируем время из миллисекунд в формат 'день/месяц часы:минуты'  (12/05 12:21)
    public String getNormalTime(DatePicker dP, TimePicker tP) {

        String month = Integer.toString(dP.getMonth());
        String day = Integer.toString(dP.getDayOfMonth());

        String hour = Integer.toString(tP.getHour());
        String min = Integer.toString(tP.getMinute());

        String time = String.format("%s/%s %s:%s", day, month, hour, min);

        return time;
    }


    //Метод создания уведомления на телефоне
    private void createNotification() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        long triggerAtMillis = calendar.getTimeInMillis();

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }




}