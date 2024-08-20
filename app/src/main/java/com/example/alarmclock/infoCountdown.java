package com.example.alarmclock;

public class infoCountdown {
    private String Thoi_Gian;
    private int Luot;

    public infoCountdown(String thoi_Gian,int luot) {
        Thoi_Gian = thoi_Gian;
        Luot = luot;
    }

    public String getThoi_Gian() {
        return Thoi_Gian;
    }
    public int getLuot() {
        return Luot;
    }

    public void setThoi_Gian(String thoi_Gian) {
        Thoi_Gian = thoi_Gian;
    }
    public void setLuot(int luot) {
        Luot = luot;
    }
}
