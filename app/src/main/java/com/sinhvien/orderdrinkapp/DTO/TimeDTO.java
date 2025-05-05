package com.sinhvien.orderdrinkapp.DTO;

import android.database.Cursor;

import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeDTO {
    private int id;
    private int maThoiGian;
    private String thoiGianDangNhap;
    private String thoiGianLogout;
    private  String ngay;
    CreateDatabase database;

    public TimeDTO(int id, int maThoiGian, String thoiGianDangNhap, String thoiGianLogout, String ngay) {
        this.id = id;
        this.maThoiGian = maThoiGian;
        this.thoiGianDangNhap = thoiGianDangNhap;
        this.thoiGianLogout = thoiGianLogout;
        this.ngay = ngay;
    }

    public TimeDTO() {

        // Constructor mặc định
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho maThoiGian
    public int getMaThoiGian() {
        return maThoiGian;
    }

    public void setMaThoiGian(int maThoiGian) {
        this.maThoiGian = maThoiGian;
    }

    // Getter và Setter cho thoiGianDangNhap
    public String getThoiGianDangNhap() {
        return thoiGianDangNhap;
    }

    public void setThoiGianDangNhap(String thoiGianDangNhap) {
        this.thoiGianDangNhap = thoiGianDangNhap;
    }

    // Getter và Setter cho thoiGianLogout
    public String getThoiGianLogout() {
        return thoiGianLogout;
    }

    public void setThoiGianLogout(String thoiGianLogout) {
        this.thoiGianLogout = thoiGianLogout;
    }

}
