package com.sinhvien.orderdrinkapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TBL_NHANVIEN = "NHANVIEN";
    public static String TBL_MON = "MON";
    public static String TBL_LOAIMON = "LOAIMON";
    public static String TBL_BAN = "BAN";
    public static String TBL_DONDAT = "DONDAT";
    public static String TBL_CHITIETDONDAT = "CHITIETDONDAT";
    public static String TBL_QUYEN = "QUYEN";

    //Bảng nhân viên
    public static String TBL_NHANVIEN_MANV = "MANV";
    public static String TBL_NHANVIEN_HOTENNV = "HOTENNV";
    public static String TBL_NHANVIEN_TENDN = "TENDN";
    public static String TBL_NHANVIEN_MATKHAU = "MATKHAU";
    public static String TBL_NHANVIEN_EMAIL = "EMAIL";
    public static String TBL_NHANVIEN_SDT = "SDT";
    public static String TBL_NHANVIEN_GIOITINH = "GIOITINH";
    public static String TBL_NHANVIEN_NGAYSINH = "NGAYSINH";
    public static String TBL_NHANVIEN_MAQUYEN = "MAQUYEN";
    public static String TBL_NHANVIEN_LOGIN = "TIMELOGIN";
    public static String TBL_NHANVIEN_LOGOUT = "TIMELOGOUT";


    //Bảng quyền
    public static String TBL_QUYEN_MAQUYEN = "MAQUYEN";
    public static String TBL_QUYEN_TENQUYEN = "TENQUYEN";

    //Bảng món
    public static String TBL_MON_MAMON = "MAMON";
    public static String TBL_MON_TENMON = "TENMON";
    public static String TBL_MON_GIATIEN = "GIATIEN";
    public static String TBL_MON_TINHTRANG = "TINHTRANG";
    public static String TBL_MON_HINHANH = "HINHANH";
    public static String TBL_MON_MALOAI = "MALOAI";

    //Bảng loại món
    public static String TBL_LOAIMON_MALOAI = "MALOAI";
    public static String TBL_LOAIMON_TENLOAI = "TENLOAI";
    public static String TBL_LOAIMON_HINHANH = "HINHANH";

    //Bảng bàn
    public static String TBL_BAN_MABAN = "MABAN";
    public static String TBL_BAN_TENBAN = "TENBAN";
    public static String TBL_BAN_TINHTRANG = "TINHTRANG";

    //Bảng đơn đặt
    public static String TBL_DONDAT_MADONDAT = "MADONDAT";
    public static String TBL_DONDAT_MANV = "MANV";
    public static String TBL_DONDAT_NGAYDAT = "NGAYDAT";
    public static String TBL_DONDAT_TINHTRANG = "TINHTRANG";
    public static String TBL_DONDAT_TONGTIEN = "TONGTIEN";
    public static String TBL_DONDAT_MABAN = "MABAN";

    //Bảng chi tiết đơn đặt
    public static String TBL_CHITIETDONDAT_MADONDAT = "MADONDAT";
    public static String TBL_CHITIETDONDAT_MAMON = "MAMON";
    public static String TBL_CHITIETDONDAT_SOLUONG = "SOLUONG";

    //LUONG NHAN VIEN
    public static String TBL_LUONGNHANVIEN = "LUONGNHANVIEN";
    public static String TBL_MUCLUONG = "MUCLUONG";
    public static String TBL_MALUONG = "MALUONG";
    public static String TBL_LUONGTHUONG = "LUONGTHUONG";
    public static String TBL_LUONGPHAT = "LUONGPHAT";
    public static String TBL_SOGIO = "SOGIO";
    public static String TBL_NGAYTHANG = "NGAYTHANG";


    public CreateDatabase(Context context) {
        super(context, "OrderDrink", null, 3);
    }

    //thực hiện tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblNHANVIEN = "CREATE TABLE " + TBL_NHANVIEN + " ( " +
                TBL_NHANVIEN_MANV + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_NHANVIEN_HOTENNV + " TEXT, " +
                TBL_NHANVIEN_TENDN + " TEXT, " +
                TBL_NHANVIEN_MATKHAU + " TEXT, " +
                TBL_NHANVIEN_EMAIL + " TEXT, " +
                TBL_NHANVIEN_SDT + " TEXT, " +
                TBL_NHANVIEN_GIOITINH + " TEXT, " +
                TBL_NHANVIEN_NGAYSINH + " TEXT, " +
                TBL_NHANVIEN_MAQUYEN + " INTEGER, " +
                TBL_NHANVIEN_LOGIN + " TEXT, " + // Thêm cột thời gian đăng nhập
                TBL_NHANVIEN_LOGOUT + " TEXT)";  // Thêm cột thời gian đăng xuất


        String tblQUYEN = "CREATE TABLE " + TBL_QUYEN + " ( " + TBL_QUYEN_MAQUYEN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_QUYEN_TENQUYEN + " TEXT)";
        // Tạo bàn tăng tự động
        String tblBAN = "CREATE TABLE " + TBL_BAN + " ( " + TBL_BAN_MABAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_BAN_TENBAN + " TEXT, " + TBL_BAN_TINHTRANG + " TEXT )";

        String tblMON = "CREATE TABLE " + TBL_MON + " ( " + TBL_MON_MAMON + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_MON_TENMON + " TEXT, " + TBL_MON_GIATIEN + " TEXT, " + TBL_MON_TINHTRANG + " TEXT, "
                + TBL_MON_HINHANH + " BLOB, " + TBL_MON_MALOAI + " INTEGER )";

        String tblLOAIMON = "CREATE TABLE " + TBL_LOAIMON + " ( " + TBL_LOAIMON_MALOAI + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_LOAIMON_HINHANH + " BLOB, " + TBL_LOAIMON_TENLOAI + " TEXT)";

        String tblDONDAT = "CREATE TABLE " + TBL_DONDAT + " ( " + TBL_DONDAT_MADONDAT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TBL_DONDAT_MABAN + " INTEGER, " + TBL_DONDAT_MANV + " INTEGER, " + TBL_DONDAT_NGAYDAT + " TEXT, " + TBL_DONDAT_TONGTIEN + " TEXT,"
                + TBL_DONDAT_TINHTRANG + " TEXT )";

        String tblCHITIETDONDAT = "CREATE TABLE " + TBL_CHITIETDONDAT + " ( " + TBL_CHITIETDONDAT_MADONDAT + " INTEGER, "
                + TBL_CHITIETDONDAT_MAMON + " INTEGER, " + TBL_CHITIETDONDAT_SOLUONG + " INTEGER, "
                + " PRIMARY KEY ( " + TBL_CHITIETDONDAT_MADONDAT + "," + TBL_CHITIETDONDAT_MAMON + "))";

        String tblLUONGNHANVIEN = "CREATE TABLE " + TBL_LUONGNHANVIEN + " ( " +
                TBL_MALUONG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_LUONGTHUONG + " INTEGER, " +
                TBL_LUONGPHAT + " INTEGER, " +
                TBL_SOGIO + " INTEGER, " +
                TBL_MUCLUONG + " INTEGER, " +
                TBL_NGAYTHANG + " TEXT )";


        db.execSQL(tblNHANVIEN);
        db.execSQL(tblQUYEN);
        db.execSQL(tblBAN);
        db.execSQL(tblMON);
        db.execSQL(tblLOAIMON);
        db.execSQL(tblDONDAT);
        db.execSQL(tblCHITIETDONDAT);
        db.execSQL(tblLUONGNHANVIEN);
    }

    public boolean checkUsernameExist(String username) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TBL_NHANVIEN + " WHERE " + TBL_NHANVIEN_TENDN + "=?";
            cursor = database.rawQuery(query, new String[]{username});

            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
    }
    public boolean updatePassword(String TBL_NHANVIEN_TENDN, String TBL_NHANVIEN_MATKHAU) {
        SQLiteDatabase MyBD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MATKHAU", TBL_NHANVIEN_MATKHAU);
        // Update the password for the specified username
        long result = MyBD.update("NHANVIEN", contentValues, "TENDN=?", new String[]{TBL_NHANVIEN_TENDN});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean resetpassword(String TBL_NHANVIEN_TENDN, String password) {
        try {
            SQLiteDatabase MyBD = this.getWritableDatabase();
            if (MyBD != null && MyBD.isOpen()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("MATKHAU", password);

                int rowsAffected = MyBD.update("NHANVIEN", contentValues, "TENDN=?", new String[]{TBL_NHANVIEN_TENDN});

                if (rowsAffected > 0) {
                    // Có ít nhất một hàng được cập nhật thành công
                    return true;
                } else {
                    // Không có hàng nào bị ảnh hưởng (không tìm thấy người dùng)
                    return false;
                }
            } else {
                // Xử lý trường hợp MyBD là null hoặc đã đóng
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi
            return false;
        } finally {
            // Đóng CSDL nếu cần thiết
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        String dropTableQuery = "DROP TABLE IF EXISTS " + TBL_LUONGNHANVIEN;
        db.execSQL(dropTableQuery);

        // Tạo lại bảng mới
        String tblLUONGNHANVIEN = "CREATE TABLE LUONG (MALUONG INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "LUONGTHUONG INTEGER, " +
                "LUONGPHAT INTEGER, " +
                "SOGIO INTEGER, " +
                "NGAYTHANG TEXT, " +
                "MUCLUONG INTEGER, " +
                "MANV INTEGER REFERENCES NHANVIEN(MANV))";
        db.execSQL(tblLUONGNHANVIEN);




    }

    //mở kết nối csdl
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }

}
