package com.example.alarmclock;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Timer extends AppCompatActivity {
    private EditText editText;
    private long mStartTime;
    private long mEndTime;
    private TextView textView,txt;
    private Button btnStartPause;
    private Button btnReset;
    private Button btnSet;
    private Button btnBamGio, btnBaoThuc;
    private CountDownTimer mcountDownTimer;
    private boolean mTimeRunning;
    private long mTimeLeftInMillis;
    private static final String CHANNEL_ID = "channel_ID";
    private static final int NOTIFICATION_ID = 1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        editText = findViewById(R.id.edt);
        btnStartPause = findViewById(R.id.btnStartPause)  ;
        btnReset = findViewById(R.id.btnReset);
        textView = findViewById(R.id.time);
        txt = findViewById(R.id.txt);
        btnSet = findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                if(input.length()==0)
                {
                    Toast.makeText(Timer.this,"Không có thời gian nào để hẹn giờ!",Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(input)*60000;
                if(millisInput==0)
                {
                    Toast.makeText(Timer.this,"Hãy nhập dữ liệu chính xác!",Toast.LENGTH_SHORT).show();
                    return;
                }
                setTime(millisInput);
                editText.setText("");
            }
        });
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimeRunning) {
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        btnBaoThuc = findViewById(R.id.button2);
        btnBaoThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Timer.this, AlarmSave.class);
                startActivity(i);
            }
        });
        btnBamGio = findViewById(R.id.btnBamGio);
        btnBamGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Timer.this, CountdownClock.class);
                startActivity(intent);
            }
        });
    }
    private void setTime(long milliseconds)
    {
        mStartTime = milliseconds;
        resetTimer();
        closekeyboard();
    }
    private void startTimer() {
        //Lấy thời gian hệ thống + thời gian của mình
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        //Countdown: là mTimeLeft - 1s(mỗi một vòng chạy)
        mcountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimeRunning = false;
                updateButtons();
            }
        }.start();
        mTimeRunning = true;
        updateButtons();
    }
    private void pauseTimer(){
        //Dừng đếm giờ, chạy lại đếm h với cột mốc cũ khi đã dừng
        mcountDownTimer.cancel();
        mTimeRunning = false;
        updateButtons();
    }
    private void resetTimer(){
        //mTimeLeftInMillis: sử dụng chính -  tính bằng s
        //mStartTime: thời gian bắt đầu
        mTimeLeftInMillis = mStartTime;
        updateCountDownText();
        updateButtons();
    }

    private void updateButtons() {
        if(mTimeRunning)
        {
            txt.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
            btnReset.setVisibility(View.INVISIBLE);
            btnSet.setVisibility(View.INVISIBLE);
            btnStartPause.setText("Pause");
        }
        else
        {
            txt.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            btnStartPause.setText("Start");
            btnReset.setVisibility(View.VISIBLE);
            btnSet.setVisibility(View.VISIBLE);
            if(mTimeLeftInMillis<1000)
            {
                btnStartPause.setVisibility(View.INVISIBLE);
            }
            else{
                btnStartPause.setVisibility(View.VISIBLE);
            }
            if(mTimeLeftInMillis< mStartTime)
            {
                btnReset.setVisibility(View.VISIBLE);
            }
            else
            {
                btnReset.setVisibility(View.INVISIBLE);
            }
        }
    }
    private void updateCountDownText()
    {
        int minutes = (int) ((mTimeLeftInMillis/1000)/60);
        int seconds = (int) (mTimeLeftInMillis/1000) %60;
        int hour = minutes/60;
        minutes = minutes%60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hour,minutes,seconds);
        textView.setText(timeLeftFormatted);
        if(minutes==0&&seconds==1&&hour==0)
            sendNotification();
    }
    private void closekeyboard()
    {
        View view = this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    //xoay màn hình
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisLeft", mTimeLeftInMillis);
        outState.putBoolean("timeRunning", mTimeRunning);
        outState.putLong("endTime", mEndTime);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTimeLeftInMillis = savedInstanceState.getLong("millisLeft");
        mTimeRunning = savedInstanceState.getBoolean("timeRunning");
        updateCountDownText();
        updateButtons();
        if(mTimeRunning)
        {
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }
    }
    //Hàm stop khi nó dừng giao diện, nó vẫn chạy
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("chay",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("startTimeInMillis",mStartTime);
        editor.putLong("millisLeft",mTimeLeftInMillis);
        editor.putBoolean("timeRunning",mTimeRunning);
        editor.putLong("endTime",mEndTime);
        editor.apply();
        if(mcountDownTimer!=null)
        {
            mcountDownTimer.cancel();
        }
    }
    //Hiển thị giao diện lại.
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("chay",MODE_PRIVATE);
        mStartTime=sharedPreferences.getLong("startTimeInMillis",600000);
        mTimeLeftInMillis = sharedPreferences.getLong("millisLeft",mStartTime);
        mTimeRunning = sharedPreferences.getBoolean("timeRunning",false);
        updateCountDownText();
        updateButtons();
        if(mTimeRunning)
        {
            mEndTime = sharedPreferences.getLong("endTime",0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if(mTimeLeftInMillis<0)
            {
                mTimeLeftInMillis = 0;
                mTimeRunning = false;
                updateCountDownText();
                updateButtons();
            }else
            {
                startTimer();
            }
        }
    }
    private void sendNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        Intent intent = new Intent(Timer.this,Timer.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(Timer.this);
        stackBuilder.addNextIntentWithParentStack(intent);
        //tao 1 pendingIntent chua tien trinh khoi tao activity
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(getNotificationID(), PendingIntent.FLAG_UPDATE_CURRENT);
        //Tao doi tuong NotificationCompat
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Thông báo hẹn giờ")
                .setContentText("Đã hết thời gian hẹn giờ")
                .setSound(null)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Dang ky doi tuong NotificationCompat len Status Bar
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationID(), notification.build());
    }
    private int getNotificationID() {
        return (int) new Date().getTime();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            //Dang ky doi tuong Notification
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}