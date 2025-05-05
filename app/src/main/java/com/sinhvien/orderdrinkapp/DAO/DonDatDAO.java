package com.sinhvien.orderdrinkapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import com.sinhvien.orderdrinkapp.DTO.ChiTietDonDatDTO;
import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DonDatDAO {
    private CreateDatabase dbHelper;
    SQLiteDatabase database;
    public DonDatDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public List<DonDatDTO> LayDanhSachMonAnTheoMaBan(int maBan) {
        List<DonDatDTO> danhSachMonAn = new ArrayList<>();

        // Xây dựng truy vấn SQL để lấy danh sách món ăn theo mã bàn
        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT +
                " WHERE " + CreateDatabase.TBL_DONDAT_MABAN + " = ?";
        Cursor cursor = database.rawQuery(query, new String[] { String.valueOf(maBan) });

        // Duyệt qua kết quả của truy vấn và thêm mỗi món ăn vào danh sách
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DonDatDTO monAn = new DonDatDTO();
                monAn.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
                monAn.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
                monAn.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
                monAn.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
                monAn.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
                monAn.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));

                danhSachMonAn.add(monAn);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return danhSachMonAn;
    }

    public boolean CapNhatMonAn(DonDatDTO monAn) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TBL_DONDAT_MABAN, monAn.getMaBan());
        values.put(CreateDatabase.TBL_DONDAT_MANV, monAn.getMaNV());
        values.put(CreateDatabase.TBL_DONDAT_NGAYDAT, monAn.getNgayDat());
        values.put(CreateDatabase.TBL_DONDAT_TINHTRANG, monAn.getTinhTrang());
        values.put(CreateDatabase.TBL_DONDAT_TONGTIEN, monAn.getTongTien());

        // Thực hiện cập nhật dữ liệu trong bảng với mã đơn đặt hàng tương ứng
        int rowsAffected = database.update(
                CreateDatabase.TBL_DONDAT,
                values,
                CreateDatabase.TBL_DONDAT_MADONDAT + " = ?",
                new String[] { String.valueOf(monAn.getMaDonDat()) }
        );

        // Trả về true nếu có ít nhất một hàng được cập nhật, ngược lại trả về false
        return rowsAffected > 0;
    }
    public void XoaMonAn(int maDonDat) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(CreateDatabase.TBL_DONDAT, CreateDatabase.TBL_DONDAT_MADONDAT + " = ?", new String[]{String.valueOf(maDonDat)});
        database.close();
    }



    public long ThemDonDat(DonDatDTO donDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_MABAN,donDatDTO.getMaBan());
        contentValues.put(CreateDatabase.TBL_DONDAT_MANV,donDatDTO.getMaNV());
        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT,donDatDTO.getNgayDat());
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,donDatDTO.getTinhTrang());
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,donDatDTO.getTongTien());

        long madondat = database.insert(CreateDatabase.TBL_DONDAT,null,contentValues);

        return madondat;
    }

    public List<DonDatDTO> LayDSDonDat(){
        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonDatDTO donDatDTO = new DonDatDTO();
            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDatDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDatDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
            donDatDTOS.add(donDatDTO);

            cursor.moveToNext();
        }
        return donDatDTOS;
    }

    public List<DonDatDTO> LayDSDonDatNgay(String ngaythang){
        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT+" WHERE "+CreateDatabase.TBL_DONDAT_NGAYDAT+" like '"+ngaythang+"'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonDatDTO donDatDTO = new DonDatDTO();
            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDatDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDatDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
            donDatDTOS.add(donDatDTO);

            cursor.moveToNext();
        }
        return donDatDTOS;
    }

    public long LayMaDonTheoMaBan(int maban, String tinhtrang){
        String query = "SELECT * FROM " +CreateDatabase.TBL_DONDAT+ " WHERE " +CreateDatabase.TBL_DONDAT_MABAN+ " = '" +maban+ "' AND "
                +CreateDatabase.TBL_DONDAT_TINHTRANG+ " = '" +tinhtrang+ "' ";
        long magoimon = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT));

            cursor.moveToNext();
        }
        return magoimon;
    }

    public boolean UpdateTongTienDonDat(int madondat,String tongtien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,tongtien);
        long ktra  = database.update(CreateDatabase.TBL_DONDAT,contentValues,
                CreateDatabase.TBL_DONDAT_MADONDAT+" = "+madondat,null);
        if(ktra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean UpdateTThaiDonTheoMaBan(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_DONDAT,contentValues,CreateDatabase.TBL_DONDAT_MABAN+
                " = '"+maban+"'",null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }
        public boolean xoaDonDat(DonDatDTO donDatDTO) {
        // Xác định điều kiện xóa dữ liệu, ở đây là mã đơn đặt hàng
        String dieuKien = CreateDatabase.TBL_DONDAT_MADONDAT + " = ?";
        String[] giaTriDieuKien = { String.valueOf(donDatDTO.getMaDonDat()) };

        // Thực hiện xóa đơn đặt hàng từ cơ sở dữ liệu
        int ketQuaXoa = database.delete(CreateDatabase.TBL_DONDAT, dieuKien, giaTriDieuKien);

        // Kiểm tra kết quả xóa và trả về true nếu xóa thành công, ngược lại trả về false
        return ketQuaXoa > 0;
    }

    public void open() {
    }

    public void close() {
    }
    //    //Hàm đúng nhất
    public double TinhTongDoanhThu(String ngayBatDau, String ngayKetThuc) {
        double tongDoanhThu = 0;

        // Thực hiện truy vấn SQL để tính tổng doanh thu trong khoảng thời gian chỉ định
        String query = "SELECT SUM(" + CreateDatabase.TBL_DONDAT_TONGTIEN + ") AS TongDoanhThu " +
                "FROM " + CreateDatabase.TBL_DONDAT +
                " WHERE " + CreateDatabase.TBL_DONDAT_NGAYDAT + " >= '" + ngayBatDau + "'" +
                " AND " + CreateDatabase.TBL_DONDAT_NGAYDAT + " <= '" + ngayKetThuc + "'";
        Cursor cursor = database.rawQuery(query, null);

        // Lấy tổng doanh thu từ kết quả của truy vấn
        if (cursor != null && cursor.moveToFirst()) {
            tongDoanhThu = cursor.getDouble(cursor.getColumnIndex("TongDoanhThu"));
            cursor.close();
        }

        return tongDoanhThu;
    }
}














//package com.sinhvien.orderdrinkapp.DAO;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteStatement;
//import android.util.Log;
//import android.util.Pair;
//
//import com.sinhvien.orderdrinkapp.DTO.ChiTietDonDatDTO;
//import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
//import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
//import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DonDatDAO {
//    SQLiteDatabase database;
//    public DonDatDAO(Context context){
//        CreateDatabase createDatabase = new CreateDatabase(context);
//        database = createDatabase.open();
//    }
//
//    public long ThemDonDat(DonDatDTO donDatDTO){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CreateDatabase.TBL_DONDAT_MABAN,donDatDTO.getMaBan());
//        contentValues.put(CreateDatabase.TBL_DONDAT_MANV,donDatDTO.getMaNV());
//        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT,donDatDTO.getNgayDat());
//        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,donDatDTO.getTinhTrang());
//        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,donDatDTO.getTongTien());
//
//        long madondat = database.insert(CreateDatabase.TBL_DONDAT,null,contentValues);
//
//        return madondat;
//    }
//
//    public List<DonDatDTO> LayDSDonDat(){
//        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
//        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT;
//        Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            DonDatDTO donDatDTO = new DonDatDTO();
//            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
//            donDatDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
//            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
//            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
//            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
//            donDatDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
//            donDatDTOS.add(donDatDTO);
//
//            cursor.moveToNext();
//        }
//        return donDatDTOS;
//    }
//
//    public List<DonDatDTO> LayDSDonDatNgay(String ngaythang){
//        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
//        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT+" WHERE "+CreateDatabase.TBL_DONDAT_NGAYDAT+" like '"+ngaythang+"'";
//        Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            DonDatDTO donDatDTO = new DonDatDTO();
//            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
//            donDatDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
//            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
//            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
//            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
//            donDatDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
//            donDatDTOS.add(donDatDTO);
//
//            cursor.moveToNext();
//        }
//        return donDatDTOS;
//    }
////    public double TinhTongDoanhThu(String ngayBatDau, String ngayKetThuc) {
////        double tongDoanhThu = 0;
////
////        // Tạo và thực thi truy vấn SQL
////        String query = "SELECT SUM(" + CreateDatabase.TBL_DONDAT_TONGTIEN + ") AS TongDoanhThu " +
////                "FROM " + CreateDatabase.TBL_DONDAT +
////                " WHERE " + CreateDatabase.TBL_DONDAT_NGAYDAT + " >= ? AND " + CreateDatabase.TBL_DONDAT_NGAYDAT + " <= ?";
////        SQLiteStatement stmt = database.compileStatement(query);
////        stmt.bindString(1, ngayBatDau);
////        stmt.bindString(2, ngayKetThuc);
////        Cursor cursor = null;
////        stmt.execute();
////
////        // Lấy tổng doanh thu từ kết quả
////        if (cursor != null && cursor.moveToFirst()) {
////            tongDoanhThu = cursor.getDouble(cursor.getColumnIndex("TongDoanhThu"));
////        }
////
////        // Đóng kết nối
////        if (cursor != null) {
////            cursor.close();
////        }
////
////        return tongDoanhThu;
////    }
//
//
//
//
//    //Hàm đúng nhất
//    public double TinhTongDoanhThu(String ngayBatDau, String ngayKetThuc) {
//        double tongDoanhThu = 0;
//
//        // Thực hiện truy vấn SQL để tính tổng doanh thu trong khoảng thời gian chỉ định
//        String query = "SELECT SUM(" + CreateDatabase.TBL_DONDAT_TONGTIEN + ") AS TongDoanhThu " +
//                "FROM " + CreateDatabase.TBL_DONDAT +
//                " WHERE " + CreateDatabase.TBL_DONDAT_NGAYDAT + " >= '" + ngayBatDau + "'" +
//                " AND " + CreateDatabase.TBL_DONDAT_NGAYDAT + " <= '" + ngayKetThuc + "'";
//        Cursor cursor = database.rawQuery(query, null);
//
//        // Lấy tổng doanh thu từ kết quả của truy vấn
//        if (cursor != null && cursor.moveToFirst()) {
//            tongDoanhThu = cursor.getDouble(cursor.getColumnIndex("TongDoanhThu"));
//            cursor.close();
//        }
//
//        return tongDoanhThu;
//    }
//    public long LayMaDonTheoMaBan(int maban, String tinhtrang){
//        String query = "SELECT * FROM " +CreateDatabase.TBL_DONDAT+ " WHERE " +CreateDatabase.TBL_DONDAT_MABAN+ " = '" +maban+ "' AND "
//                +CreateDatabase.TBL_DONDAT_TINHTRANG+ " = '" +tinhtrang+ "' ";
//        long magoimon = 0;
//        Cursor cursor = database.rawQuery(query,null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT));
//
//            cursor.moveToNext();
//        }
//        return magoimon;
//    }
//
//    public boolean UpdateTongTienDonDat(int madondat,String tongtien){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,tongtien);
//        long ktra  = database.update(CreateDatabase.TBL_DONDAT,contentValues,
//                CreateDatabase.TBL_DONDAT_MADONDAT+" = "+madondat,null);
//        if(ktra != 0){
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    public boolean UpdateTThaiDonTheoMaBan(int maban,String tinhtrang){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,tinhtrang);
//        long ktra = database.update(CreateDatabase.TBL_DONDAT,contentValues,CreateDatabase.TBL_DONDAT_MABAN+
//                " = '"+maban+"'",null);
//        if(ktra !=0){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    // Hàm chuyển bàn
//    public boolean ChuyenBan(int maDonDat, int maBanMoi) {
//        // Lấy thông tin đơn đặt hàng hiện tại
//        DonDatDTO donDatDTO = LayDonDatTheoMa(maDonDat);
//
//        // Cập nhật mã bàn mới cho đơn đặt hàng
//        donDatDTO.setMaBan(maBanMoi);
//
//        // Cập nhật thông tin đơn đặt hàng với bàn mới
//        boolean capNhatDonDat = CapNhatDonDat(donDatDTO);
//
//        // Trả về true nếu quá trình chuyển bàn thành công
//        return capNhatDonDat;
//    }
//
//    // Hàm lấy thông tin đơn đặt hàng theo mã
//    private DonDatDTO LayDonDatTheoMa(int maDonDat) {
//        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT + " WHERE " + CreateDatabase.TBL_DONDAT_MADONDAT + " = " + maDonDat;
//        Cursor cursor = database.rawQuery(query, null);
//
//        DonDatDTO donDatDTO = new DonDatDTO();
//        if (cursor.moveToFirst()) {
//            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
//            donDatDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
//            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
//            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
//            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
//            donDatDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
//        }
//
//        cursor.close();
//        return donDatDTO;
//    }
//
//    // Hàm cập nhật thông tin đơn đặt hàng
//    private boolean CapNhatDonDat(DonDatDTO donDatDTO) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CreateDatabase.TBL_DONDAT_MABAN, donDatDTO.getMaBan());
//        contentValues.put(CreateDatabase.TBL_DONDAT_MANV, donDatDTO.getMaNV());
//        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT, donDatDTO.getNgayDat());
//        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG, donDatDTO.getTinhTrang());
//        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN, donDatDTO.getTongTien());
//
//        long ktra = database.update(CreateDatabase.TBL_DONDAT, contentValues,
//                CreateDatabase.TBL_DONDAT_MADONDAT + " = " + donDatDTO.getMaDonDat(), null);
//
//        return ktra != 0;
//    }
//    public boolean xoaDonDat(DonDatDTO donDatDTO) {
//        // Xác định điều kiện xóa dữ liệu, ở đây là mã đơn đặt hàng
//        String dieuKien = CreateDatabase.TBL_DONDAT_MADONDAT + " = ?";
//        String[] giaTriDieuKien = { String.valueOf(donDatDTO.getMaDonDat()) };
//
//        // Thực hiện xóa đơn đặt hàng từ cơ sở dữ liệu
//        int ketQuaXoa = database.delete(CreateDatabase.TBL_DONDAT, dieuKien, giaTriDieuKien);
//
//        // Kiểm tra kết quả xóa và trả về true nếu xóa thành công, ngược lại trả về false
//        return ketQuaXoa > 0;
//    }
//
//    public void open(){
//    }
//
//    public void close() {
//
//    }
//}
