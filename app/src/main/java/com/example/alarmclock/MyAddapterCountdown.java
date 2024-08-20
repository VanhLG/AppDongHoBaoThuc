package com.example.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAddapterCountdown extends BaseAdapter {
    ArrayList<infoCountdown> list;
    Context context;

    public MyAddapterCountdown(ArrayList<infoCountdown> list, Context context) {
        this.list = list;
        this.context = context;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_countdown,viewGroup,false);
        TextView Thoi_Gian;
        TextView Luot;

       Thoi_Gian = view.findViewById(R.id.Thoi_Gian);
        Luot = view.findViewById(R.id.luot);

        Thoi_Gian.setText(list.get(i).getThoi_Gian().toString());
        Luot.setText(String.valueOf(list.get(i).getLuot()));

        return view;
    }
}
