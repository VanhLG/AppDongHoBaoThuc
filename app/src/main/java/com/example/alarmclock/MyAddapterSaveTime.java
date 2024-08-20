package com.example.alarmclock;
import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyAddapterSaveTime<sharedPreferences> extends BaseAdapter {
    AlarmDAO alarmDAO;
    long time;
    private SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    AlarmManager alarmManager;
    Calendar calendar;
    PendingIntent pendingIntent;
    private ArrayList<IfSaveTime> list;
    private Context context;
    private IClickItemListener listener;
    public MyAddapterSaveTime(AlarmDAO alarmDAO, ArrayList<IfSaveTime> list, Context context, IClickItemListener listener) {
        this.alarmDAO = alarmDAO;
        this.list = list;
        this.context = context;
        this.listener = listener;
    }
    public MyAddapterSaveTime(ArrayList<IfSaveTime>  list, Context context, IClickItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        sharedPreferences =context.getSharedPreferences("com.example.alarmclock", Context.MODE_PRIVATE);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        view = LayoutInflater.from(context).inflate(R.layout.layout_save_alarm,viewGroup,false);
        TextView textView = view.findViewById(R.id.time);
        TextView tb = view.findViewById(R.id.TB);
        Switch aSwitch= view.findViewById(R.id.Switch);
        boolean a=list.get(i).isaBoolean();
        aSwitch.setChecked(a);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    IfSaveTime ifSaveTime= new IfSaveTime();
                    ifSaveTime.setId(list.get(i).getId());
                    ifSaveTime.setaBoolean(true);
                    ifSaveTime.setGio(list.get(i).getGio());
                    ifSaveTime.setPhut(list.get(i).getPhut());
                    ifSaveTime.setText(list.get(i).getText());
                    String gio1= String.valueOf(list.get(i).getGio())+":"+String.valueOf(list.get(i).getPhut());
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("gio",gio1);
                    editor.putString("ten",list.get(i).getText());
                    editor.apply();
                    calendar.set(Calendar.HOUR_OF_DAY, ifSaveTime.getGio());
                    calendar.set(Calendar.MINUTE, ifSaveTime.getPhut());
                    Intent i2 = new Intent(context, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(context, getNextId(), i2, 0);
                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        if (Calendar.AM_PM == 0)
                            time = time + (1000 * 60 * 60 * 12);
                        else
                            time = time + (1000 * 60 * 60 * 24);
                    }
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 0, pendingIntent);
                    alarmDAO.update(ifSaveTime);
                }
                else {
                    IfSaveTime ifSaveTime= new IfSaveTime();
                    ifSaveTime.setId(list.get(i).getId());
                    ifSaveTime.setaBoolean(false);
                    ifSaveTime.setGio(list.get(i).getGio());
                    ifSaveTime.setPhut(list.get(i).getPhut());
                    ifSaveTime.setText(list.get(i).getText());
                    alarmDAO.update(ifSaveTime);
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        String g,p;
        if(list.get(i).getGio()<10){
            g= "0"+ String.valueOf( list.get(i).getGio());}
        else {
            g= String.valueOf( list.get(i).getGio());
        }
        if(list.get(i).getPhut()<10){
            p= "0"+ String.valueOf( list.get(i).getPhut());}
        else {
            p= String.valueOf( list.get(i).getPhut());
        }
        String time = (String)  g+":"+p;
        textView.setText(time);
        tb.setText(list.get(i).getText().toString());

        RelativeLayout layout_item = view.findViewById(R.id.layout_item);
        layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(list.get(i));
            }
        });
        return view;
    }
    private int getNextId()
    {
        return (int) new Date().getTime();
    }
}

