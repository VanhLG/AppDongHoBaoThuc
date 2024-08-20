package com.example.alarmclock;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlarmSave extends AppCompatActivity {
    Button btnHenGio, btnBamGio;
    ImageButton img;
    ArrayList<IfSaveTime> list;
    AlarmDAO alarmDAO;
    ArrayList<AlarmManager> alarmManagerArrayList;
    MyAddapterSaveTime addapter;
    private ListView lisview;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ẩn thanh action bar
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_alarm_save);
        lisview = findViewById(R.id.Lisview2);
        //gọi dữ liệu từ database
        alarmDAO = new AlarmDAO(this);
        list = new ArrayList<>();
        //truyền databas vào list
        //nếu muốn sắp xếp thì đổi All thành sx
        for (IfSaveTime ifSaveTime : list = alarmDAO.getAll()) {
        }
        //set click cho adapter
        addapter = new MyAddapterSaveTime(alarmDAO, list, this, new IClickItemListener() {
            @Override
            public void onClickItem(IfSaveTime saveTime) {
                Intent i = new Intent(AlarmSave.this, MainScreen.class);
                int id = saveTime.getId();
                i.putExtra("id", id);
                i.putExtra("ten", saveTime.getText());
                startActivity(i);
            }
        });
        //truyền dữ liệu adapter vào listview
        lisview.setAdapter(addapter);
        img = findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(AlarmSave.this, MainScreen.class);
                startActivity(a);
            }
        });
        alarmManagerArrayList = new ArrayList<>();
        btnBamGio = findViewById(R.id.button3);
        btnBamGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AlarmSave.this, CountdownClock.class);
                startActivity(i);
            }
        });
        btnHenGio = findViewById(R.id.button1);
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AlarmSave.this, Timer.class);
                startActivity(i);
            }
        });

        /*timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list=new ArrayList<>();
                list=alarmDAO.getBygio(Integer.parseInt(ed.getText().toString()));
                addapter = new MyAddapterSaveTime(alarmDAO, list, getApplicationContext(), new IClickItemListener() {
                    @Override
                    public void onClickItem(IfSaveTime saveTime) {
                        Toast.makeText(getApplicationContext(),
                                saveTime.toString(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AlarmSave.this, MainScreen.class);
                        int id = saveTime.getId();
                        i.putExtra("id", id);
                        i.putExtra("ten", saveTime.getText());
                        startActivity(i);

                    }
                });
                lisview.setAdapter(addapter);
            }
        });*/
        /*xoa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(AlarmSave.this);
               builder.setTitle("Bạn muốn xóa tất cả báo thức không");
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                      alarmDAO.xoa();


                       lisview.setAdapter(null);
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });
               AlertDialog alert = builder.create();
               alert.show();
           }
       });*/
    }

}