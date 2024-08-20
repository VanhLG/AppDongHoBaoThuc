package com.example.alarmclock;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
public class AlarmNotification extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,luu,b0;
    ImageButton xoa;int a,b;
    EditText text;
    TextView baitoan;int x;
    TextView gio,phut,ngay,thang,thu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = new Intent(this, ForegroundService.class);
        stopService(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notification);
        gio=findViewById(R.id.textView3);
        phut=findViewById(R.id.textView4);
        ngay=findViewById(R.id.textView7);
        thang=findViewById(R.id.textView9);
        b1=findViewById(R.id.btnum1);
        baitoan=findViewById(R.id.bt1);
        b2=findViewById(R.id.btnum2);
        b3=findViewById(R.id.btnum3);
        b4=findViewById(R.id.btnum4);
        b5=findViewById(R.id.btnum5);
        b6=findViewById(R.id.btnum6);
        b7=findViewById(R.id.btnum7);
        b8=findViewById(R.id.btnum8);
        b9=findViewById(R.id.btnum9);
        b0=findViewById(R.id.btnum0);
        luu=findViewById(R.id.btnumchek);
        xoa = findViewById(R.id.btexe);
        text=findViewById(R.id.text);
        thu=findViewById(R.id.textView7);
        ngay=findViewById(R.id.textView8);
        thang=findViewById(R.id.textView10);
        //hiện thị giờ ngày tháng
        DateFormat df = new SimpleDateFormat("EEE");
        DateFormat df2 = new SimpleDateFormat("MM");
        DateFormat df4 = new SimpleDateFormat("HH");
        DateFormat df5 = new SimpleDateFormat("mm");
        DateFormat df3 = new SimpleDateFormat("dd");
        String date = df.format(Calendar.getInstance().getTime());
        thu.setText(date);
        phut.setText(df5.format(Calendar.getInstance().getTime()));
        gio.setText(df4.format(Calendar.getInstance().getTime()));
        ngay.setText(df3.format(Calendar.getInstance().getTime()));
        thang.setText(df2.format(Calendar.getInstance().getTime()));
        Random s=new Random();
        a=s.nextInt(50);
        b= s.nextInt(50);
        x=0;
        baitoan.setText(String.valueOf(a)+"+"+String.valueOf(b));
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+0);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+1);
            }
        });  b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+2);
            }
        });  b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+3);
            }
        });  b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+4);
            }
        });  b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+5);
            }
        });  b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+6);
            }
        });  b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+7);
            }
        });  b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+8);
            }
        });  b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx+9);
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tx= text.getText().toString();
                text.setText(tx.substring(0, tx.length() - 1));
            }
        });
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(this, alarmUri);
        ringtone.play();
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i=Integer.parseInt(text.getText().toString());
                if(i==a+b){
                    Toast.makeText(AlarmNotification.this, "Tắt báo thức", Toast.LENGTH_SHORT).show();
                    ringtone.stop();finish();
                }
                else {
                    x++;
                    a=s.nextInt(50);
                    b= s.nextInt(50);
                    baitoan.setText(String.valueOf(a)+"+"+String.valueOf(b));
                    if(x>=2){}
                }
            }
        });
    }
}

