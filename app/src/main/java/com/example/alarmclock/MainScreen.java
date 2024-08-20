package com.example.alarmclock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainScreen extends AppCompatActivity {
    Button luu;Intent i;
    ImageButton xoa;
    TimePicker timePicker;
    EditText ed;
    //   AlarmDAO alarmDAO;
    Calendar calendar;
    Bundle b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        luu = (Button) findViewById(R.id.buttonluu);
        xoa= (ImageButton)  findViewById(R.id.btxoa);
        timePicker= (TimePicker) findViewById(R.id.timePK);
        ed= (EditText) findViewById(R.id.AlarmName);
        calendar = Calendar.getInstance();
        AlarmDAO alarmDAO=new AlarmDAO(this);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=getIntent();
                if(i.getIntExtra("id",0)!=0){
                    alarmDAO.delete(i.getIntExtra("id",0));
                    Toast.makeText(MainScreen.this, "Đã xóa báo thức", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainScreen.this, "Báo thức không tồn tại", Toast.LENGTH_SHORT).show();
                }
                Intent  i = new Intent(MainScreen.this,AlarmSave.class);
//
                startActivity(i);
            }
        });
        
        
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=getIntent();
                if(i.getIntExtra("id",0)!=0){
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                    int g = timePicker.getCurrentHour();
                    int p = timePicker.getCurrentMinute();
                    //  ed.setText("yuegfefg");
                    IfSaveTime ifSaveTime= new IfSaveTime();
                    ifSaveTime.setId(i.getIntExtra("id",0));
                    ifSaveTime.setaBoolean(false);
                    ifSaveTime.setGio(g);
                    ifSaveTime.setPhut(p);
                    ifSaveTime.setText(ed.getText().toString());
                    //  Toast.makeText(context,   ifSaveTime.toString(), Toast.LENGTH_SHORT).show();
                    alarmDAO.update(ifSaveTime);
                    Toast.makeText(MainScreen.this, "Đã cài báo thức lức "+g+':'+p, Toast.LENGTH_SHORT).show();

                }

                else {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                    int g = timePicker.getCurrentHour();
                    int p = timePicker.getCurrentMinute();


                    IfSaveTime ifSaveTime = new IfSaveTime();
                    ifSaveTime.setaBoolean(false);
                    ifSaveTime.setGio(g);
                    ifSaveTime.setPhut(p);
                    ifSaveTime.setText(ed.getText().toString());
                    alarmDAO.insert(ifSaveTime);

                    Toast.makeText(MainScreen.this, "Đã cài báo thức lức "+g+':'+p, Toast.LENGTH_SHORT).show();}
                Intent  i = new Intent(MainScreen.this,AlarmSave.class);
                startActivity(i);

            }
        });

    }

}