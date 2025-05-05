package com.sinhvien.orderdrinkapp.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.sinhvien.orderdrinkapp.DTO.LuongDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LuongDAO {
    SQLiteDatabase database;
    public LuongDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean SetLuong(LuongDTO luongDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put("LUONGTHUONG",luongDTO.getLuongThuong());
        contentValues.put("LUONGPHAT",luongDTO.getLuongPhat());
        contentValues.put("SOGIO",luongDTO.getSoGio());
        contentValues.put("NGAYTHANG",luongDTO.getNgayThang());
        contentValues.put("MUCLUONG",luongDTO.getMucLuong());
        contentValues.put("MANV",luongDTO.getMaNhanvien());

        long ktra = database.insert("LUONG",null,contentValues);
        return ktra != 0;
    }

    public List<LuongDTO> getAll(){
        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
        String query = "SELECT * FROM LUONG";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LuongDTO luongDTO = new LuongDTO();

            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
            luongDTO.setLuongThuong(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")));
            luongDTO.setLuongPhat(cursor.getInt(cursor.getColumnIndex("LUONGPHAT")));
            luongDTO.setSoGio(cursor.getInt(cursor.getColumnIndex("SOGIO")));
            luongDTO.setNgayThang(cursor.getString(cursor.getColumnIndex("NGAYTHANG")));
            luongDTO.setMucLuong(cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
            luongDTO.setMaNhanvien(cursor.getInt(cursor.getColumnIndex("MANV")));
            arrayList.add(luongDTO);
            cursor.moveToNext();
        }
        return arrayList;
    }

    public List<LuongDTO> getListLuong(int manhanvien){
        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
        String query = "SELECT * FROM LUONG WHERE MANV ="+manhanvien;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LuongDTO luongDTO = new LuongDTO();

            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
            luongDTO.setLuongThuong(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")));
            luongDTO.setLuongPhat(cursor.getInt(cursor.getColumnIndex("LUONGPHAT")));
            luongDTO.setSoGio(cursor.getInt(cursor.getColumnIndex("SOGIO")));
            luongDTO.setNgayThang(cursor.getString(cursor.getColumnIndex("NGAYTHANG")));
            luongDTO.setMucLuong(cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
            luongDTO.setMaNhanvien(manhanvien);
            arrayList.add(luongDTO);
            cursor.moveToNext();
        }
        return arrayList;
    }


    public boolean check(int manhanvien,String thangNAM){
        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
        String query = "SELECT * FROM LUONG WHERE MANV ="+manhanvien+" AND NGAYTHANG='"+thangNAM+"'";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LuongDTO luongDTO = new LuongDTO();

            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
            luongDTO.setLuongThuong(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")));
            luongDTO.setLuongPhat(cursor.getInt(cursor.getColumnIndex("LUONGPHAT")));
            luongDTO.setSoGio(cursor.getInt(cursor.getColumnIndex("SOGIO")));
            luongDTO.setNgayThang(cursor.getString(cursor.getColumnIndex("NGAYTHANG")));
            luongDTO.setMucLuong(cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
            luongDTO.setMaNhanvien(manhanvien);
            arrayList.add(luongDTO);
            cursor.moveToNext();
        }
        return arrayList.size() == 0;
    }

    public boolean xoaLuong(int maLuong) {
        int result = database.delete("LUONG", "MALUONG=?", new String[]{String.valueOf(maLuong)});
        return result > 0;
    }
}











//package com.sinhvien.orderdrinkapp.DAO;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//
//import com.sinhvien.orderdrinkapp.DTO.LuongDTO;
//import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LuongDAO {
//    int mucLuong, luongThuong, luongPhat, soGio,maNhanvien;
//    String thangnam;
//    SQLiteDatabase database;
//
//    public LuongDAO(Context context){
//        CreateDatabase createDatabase = new CreateDatabase(context);
//        database = createDatabase.open();
//    }
//
//    public boolean SetLuong(LuongDTO luongDTO){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("LUONGTHUONG",luongDTO.getLuongThuong());
//        contentValues.put("LUONGPHAT",luongDTO.getLuongPhat());
//        contentValues.put("SOGIO",luongDTO.getSoGio());
//        contentValues.put("NGAYTHANG",luongDTO.getNgayThang());
//        contentValues.put("MUCLUONG",luongDTO.getMucLuong());
//        contentValues.put("MANV",luongDTO.getMaNhanvien());
//
//        long ktra = database.insert("LUONGNHANVIEN",null,contentValues);
//        return ktra != 0;
//    }
//
//    // Modify getAll method
//    public List<LuongDTO> getAll() {
//        List<LuongDTO> arrayList = new ArrayList<>();
//        String query = "SELECT * FROM LUONG";
//        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                LuongDTO luongDTO = new LuongDTO(Integer.valueOf(luongThuong), Integer.valueOf(luongPhat), Integer.valueOf(soGio), thangnam, Integer.valueOf(mucLuong), maNhanvien);
//                // Set values from cursor to the LuongDTO object
//                luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
//                luongDTO.setLuongThuong(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")));
//                luongDTO.setLuongPhat(cursor.getInt(cursor.getColumnIndex("LUONGPHAT")));
//                luongDTO.setSoGio(cursor.getInt(cursor.getColumnIndex("SOGIO")));
//                luongDTO.setNgayThang(cursor.getString(cursor.getColumnIndex("NGAYTHANG")));
//                luongDTO.setMucLuong(cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
//                luongDTO.setMaNhanvien(cursor.getInt(cursor.getColumnIndex("MANV")));
//                arrayList.add(luongDTO);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return arrayList;
//    }
//
//
//
//    public List<LuongDTO> getListLuong(int manhanvien){
//        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
//        String query = "SELECT * FROM LUONG WHERE MANV ="+manhanvien;
//        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            LuongDTO luongDTO = new LuongDTO( String.valueOf(luongThuong), String.valueOf(luongPhat), String.valueOf(soGio), thangnam,String.valueOf(mucLuong), maNhanvien);
//            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
//            luongDTO.setLuongThuong(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")));
//            luongDTO.setLuongPhat(cursor.getInt(cursor.getColumnIndex("LUONGPHAT")));
//            luongDTO.setSoGio(cursor.getInt(cursor.getColumnIndex("SOGIO")));
//            luongDTO.setNgayThang(cursor.getString(cursor.getColumnIndex("NGAYTHANG")));
//            luongDTO.setMucLuong(cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
//            luongDTO.setMaNhanvien(manhanvien);
//            arrayList.add(luongDTO);
//            cursor.moveToNext();
//        }
//        return arrayList;
//    }
//
//
//
//    public boolean check(int manhanvien,String thangNAM){
//        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
//        String query = "SELECT * FROM LUONG WHERE MANV ="+manhanvien+" AND NGAYTHANG='"+thangNAM+"'";
//        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            LuongDTO luongDTO = new LuongDTO( String.valueOf(luongThuong), String.valueOf(luongPhat), String.valueOf(soGio), thangnam,String.valueOf(mucLuong), maNhanvien);
//            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
//            luongDTO.setLuongThuong(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")));
//            luongDTO.setLuongPhat(cursor.getInt(cursor.getColumnIndex("LUONGPHAT")));
//            luongDTO.setSoGio(cursor.getInt(cursor.getColumnIndex("SOGIO")));
//            luongDTO.setNgayThang(cursor.getString(cursor.getColumnIndex("NGAYTHANG")));
//            luongDTO.setMucLuong(cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
//            luongDTO.setMaNhanvien(manhanvien);
//            arrayList.add(luongDTO);
//            cursor.moveToNext();
//        }
//        return arrayList.size() == 0;
//    }
//
//    public boolean xoaLuong(LuongDTO luongDTO) {
//        String whereClause = "MALUONG = ?";
//        String[] whereArgs = {String.valueOf(luongDTO.getMaLuong())};
//        int deletedRows = database.delete("LUONG", whereClause, whereArgs);
//        return deletedRows > 0;
//    }
//}


//    public boolean xoaLuong(String thangNam, int maNV) {
//        String whereClause = "MANV = ?";
//        String[] whereArgs = {thangNam, String.valueOf(maNV)};
//        int deletedRows = database.delete("LUONG", whereClause, whereArgs);
//        return deletedRows > 0;
//    }


//    public List<LuongDTO> getAll() {
//        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
//        String query = "SELECT * FROM LUONG";
//        Cursor cursor = database.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                int maLuong = cursor.getInt(cursor.getColumnIndex("MALUONG"));
//                int luongThuong = cursor.getInt(cursor.getColumnIndex("LUONGTHUONG"));
//                int luongPhat = cursor.getInt(cursor.getColumnIndex("LUONGPHAT"));
//                int soGio = cursor.getInt(cursor.getColumnIndex("SOGIO"));
//                int mucLuong = cursor.getInt(cursor.getColumnIndex("MUCLUONG"));
//                String ngayThang = cursor.getString(cursor.getColumnIndex("NGAYTHANG"));
//                int maNhanvien = cursor.getInt(cursor.getColumnIndex("MANV"));
//
//                LuongDTO luongDTO = new LuongDTO(luongThuong, luongPhat, soGio, ngayThang, maNhanvien, mucLuong);
//                luongDTO.setMaLuong(maLuong);
//
//                arrayList.add(luongDTO);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return arrayList;
//    }

//    public List<LuongDTO> getListLuong(int manhanvien){
//        List<LuongDTO> arrayList = new ArrayList<LuongDTO>();
//        String query = "SELECT * FROM LUONG WHERE MANV ="+manhanvien;
//        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            LuongDTO luongDTO = new LuongDTO(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")),
//                    cursor.getInt(cursor.getColumnIndex("LUONGPHAT")),
//                    cursor.getInt(cursor.getColumnIndex("SOGIO")),
//                    cursor.getString(cursor.getColumnIndex("NGAYTHANG")),
//                    manhanvien,
//                    cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
//            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
//            arrayList.add(luongDTO);
//            cursor.moveToNext();
//        }
//        return arrayList;
//    }
//    public boolean check(int manhanvien, String thangNAM) {
//        List<LuongDTO> arrayList = new ArrayList<>();
//        String query = "SELECT * FROM LUONG WHERE MANV =" + manhanvien + " AND NGAYTHANG='" + thangNAM + "'";
//        Cursor cursor = database.rawQuery(query, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            LuongDTO luongDTO = new LuongDTO(cursor.getInt(cursor.getColumnIndex("LUONGTHUONG")),
//                    cursor.getInt(cursor.getColumnIndex("LUONGPHAT")),
//                    cursor.getInt(cursor.getColumnIndex("SOGIO")),
//                    cursor.getString(cursor.getColumnIndex("NGAYTHANG")),
//                    manhanvien,
//                    cursor.getInt(cursor.getColumnIndex("MUCLUONG")));
//            luongDTO.setMaLuong(cursor.getInt(cursor.getColumnIndex("MALUONG")));
//            luongDTO.setMaNhanvien(manhanvien);
//            arrayList.add(luongDTO);
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return arrayList.size() == 0;
//    }
