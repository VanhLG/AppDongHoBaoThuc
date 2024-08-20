package com.example.alarmclock;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import java.util.ArrayList;
public class AlarmDAO {
    private SQLiteDatabase sqLiteDatabase;
    //lấy về CSDL ở dạng “đọc và ghi
    public  AlarmDAO(Context context){
        AlarmHelper helper= new AlarmHelper(context);
        sqLiteDatabase= helper.getWritableDatabase();
    }
    @SuppressLint("Range")
    //mảng giá trị các tham số trong câu lệnh SQL (selectionArgs)
    public ArrayList<IfSaveTime>  get(String sql, String ...selectionArgs){
        ArrayList<IfSaveTime> arrayList= new ArrayList<>();
        Cursor cursor =sqLiteDatabase.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            IfSaveTime ifSaveTime= new IfSaveTime();
            ifSaveTime.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            ifSaveTime.setGio(cursor.getInt(cursor.getColumnIndex("gio")));
            ifSaveTime.setPhut(cursor.getInt(cursor.getColumnIndex("phut")));
            ifSaveTime.setText(cursor.getString(cursor.getColumnIndex("ten")));
            ifSaveTime.setaBoolean(tra(cursor.getInt(cursor.getColumnIndex("trangthai")))
            );
            arrayList.add(ifSaveTime);
        }
        return arrayList;
    }
    public ArrayList<IfSaveTime> getAll(){
        String sql = "select * from alarms";
        return  get(sql);
    }
    public IfSaveTime getById(int id){
        String sql = "SELECT * FROM alarms WHERE ID = ?";
        ArrayList<IfSaveTime> arrayList= get(sql, String.valueOf(id));
        return arrayList.get(0);
    }
    public long insert(IfSaveTime note){
        ContentValues values = new ContentValues();
        values.put("gio", note.getGio());
        values.put("phut", note.getPhut());
        values.put("ten", note.getText());
        values.put("trangthai",tra2(note.isaBoolean()));
        return sqLiteDatabase.insert("alarms", null, values);
    }
    public long update(IfSaveTime note){
        ContentValues values = new ContentValues();
        values.put("gio", note.getGio());
        values.put("phut", note.getPhut());
        values.put("ten", note.getText());
        values.put("trangthai",tra2(note.isaBoolean()));
        return sqLiteDatabase.update("alarms", values, "ID=?", new String[]{String.valueOf(note.getId())});
    }
    public int delete(int id){
        return sqLiteDatabase.delete("alarms", "ID=?", new String[]{String.valueOf(id)});
    }
    //xoá tất cả báo thức
    public  int xoa(){
        return  sqLiteDatabase.delete("alarms",null, null);
    }
    public  boolean tra(int i){
        if (i !=0) return false;
        else return true;
    }
    public int tra2(boolean i){
        if (i == false)return 1;
        else return 0;
    }
    //sắp xếp
    public ArrayList<IfSaveTime> getsx(){
        String sql = "select * from alarms order by gio desc, phut desc";
        return  get(sql);
    }
    //Tìm kiếm theo giờ
    public ArrayList<IfSaveTime> getBygio(int id){
        String sql = "SELECT * FROM alarms WHERE  ID = ?";
        ArrayList<IfSaveTime> arrayList= get(sql);
        return  arrayList;
    }
}

