package com.example.alarmclock;


import java.io.Serializable;

public class IfSaveTime implements Serializable {

    private int id;
    private int gio;
    private int phut;
    private String text;
    private boolean aBoolean;

    public IfSaveTime() {
    }

    public IfSaveTime(int id, int gio, int phut, String text, boolean aBoolean) {
        this.id = id;
        this.gio = gio;
        this.phut = phut;
        this.text = text;
        this.aBoolean = aBoolean;
    }

    public IfSaveTime(int id, int gio, int phut, String text) {
        this.id = id;
        this.gio = gio;
        this.phut = phut;
        this.text = text;
    }

    public IfSaveTime(int gio, int phut, String text) {
        this.gio = gio;
        this.phut = phut;
        this.text = text;
    }

    @Override
    public String toString() {
        return "IfSaveTime{" +
                "id=" + id +
                ", gio=" + gio +
                ", phut=" + phut +
                ", text='" + text + '\'' +
                ", aBoolean=" + aBoolean +
                '}';
    }

    public int getGio() {
        return gio;
    }

    public void setGio(int gio) {
        this.gio = gio;
    }

    public int getPhut() {
        return phut;
    }

    public void setPhut(int phut) {
        this.phut = phut;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public void setId(int id) {
        this.id = id;
    }
}
