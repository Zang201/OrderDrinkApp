package com.sinhvien.orderdrinkapp.DTO;

import android.database.Cursor;

public class BanAnDTO {

    int MaBan;
    String TenBan;
    boolean DuocChon;


    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }

    public boolean isDuocChon() {
        return DuocChon;
    }

    public void setDuocChon(boolean duocChon) {
        DuocChon = duocChon;
    }

    public Cursor rawQuery(String query, Object o) {
        return null;
    }

    public void setTinhTrang(String string) {
    }

    public void setTrangThai(boolean b) {
    }
}
