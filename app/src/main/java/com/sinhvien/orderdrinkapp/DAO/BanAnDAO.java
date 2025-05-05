package com.sinhvien.orderdrinkapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.sinhvien.orderdrinkapp.DTO.BanAnDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class BanAnDAO  {
    private static int soLuongBan = 0;
    SQLiteDatabase database;
    Context context;
    public BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }
    // Lấy mã bàn theo tên bàn
    public int LayMaBanTheoTen(String tenBan) {
        int maBan = -1;
        Cursor cursor = null;
        try {
            cursor = database.query(CreateDatabase.TBL_BAN, new String[]{CreateDatabase.TBL_BAN_MABAN},
                    CreateDatabase.TBL_BAN_TENBAN + " = ?", new String[]{tenBan},
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                maBan = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return maBan;
    }

    // Đổi bàn từ bàn hiện tại sang bàn mới
    public boolean DoiBan(int maBanHienTai, int maBanMoi) {
        ContentValues values = new ContentValues();
        values.put(CreateDatabase.TBL_BAN_MABAN, maBanMoi);

        int rowsAffected = database.update(CreateDatabase.TBL_BAN, values,
                CreateDatabase.TBL_BAN_MABAN + " = ?",
                new String[]{String.valueOf(maBanHienTai)});
        return rowsAffected > 0;
    }
public boolean ThemBanAn(String tenban) {
    // Check for duplicate name
    String sql = "SELECT COUNT(*) FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_TENBAN + " LIKE ?";
    String[] args = {tenban};
    Cursor cursor = database.rawQuery(sql, args);
    cursor.moveToFirst();
    int count = cursor.getInt(0);
    cursor.close();
    if (count > 0) {
        // Name already exists, return false
        return false;
    }
    // Check if the number of tables has reached the limit
    long soBanHienTai = database.compileStatement("SELECT COUNT(*) FROM " + CreateDatabase.TBL_BAN).simpleQueryForLong();
    if (soBanHienTai >= 15) {
        // Reached the limit, return false
        return false;
    }

    ContentValues contentValues = new ContentValues();
    contentValues.put(CreateDatabase.TBL_BAN_TENBAN, tenban);
    contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG, "false");
    long ktra = database.insert(CreateDatabase.TBL_BAN, null, contentValues);
    if(ktra != 0){
        soLuongBan++; // Update counter if insert is successful
        return true;
    }else {
        return false;
    }
}
    public boolean XoaBanTheoMa(int maban){
        long ktra =database.delete(CreateDatabase.TBL_BAN,CreateDatabase.TBL_BAN_MABAN+" = "+maban,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }
    //Sửa tên bàn
    public boolean CapNhatTenBan(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TENBAN,tenban);

        long ktra = database.update(CreateDatabase.TBL_BAN,contentValues,CreateDatabase.TBL_BAN_MABAN+ " = '"+maban+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }
    //Hàm lấy ds các bàn ăn đổ vào gridview
    public List<BanAnDTO> LayTatCaBanAn(){
        List<BanAnDTO> banAnDTOList = new ArrayList<BanAnDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_BAN;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)));
            banAnDTOList.add(banAnDTO);
            cursor.moveToNext();
        }
        return banAnDTOList;
    }
    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_MON_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG,tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_BAN,contentValues,CreateDatabase.TBL_BAN_MABAN+ " = '"+maban+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayTenBanTheoMa(int maban){
        String tenban="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_BAN + " WHERE " +CreateDatabase.TBL_BAN_MABAN+ " = '" +maban+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tenban = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN));
            cursor.moveToNext();
        }
        return tenban;
    }

}


// PHAN CU
//    public boolean ThemBanAn(String tenban){
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CreateDatabase.TBL_BAN_TENBAN,tenban);
//        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG,"false");
//        long ktra = database.insert(CreateDatabase.TBL_BAN,null,contentValues);
//        if(ktra != 0){
//            return true;
//        }else {
//            return false;
//        }
//    }


// CHUẨN
//    public boolean ThemBanAn(String tenban){
//        // Check if the number of tables has reached the limit
//        long soBanHienTai = database.compileStatement("SELECT COUNT(*) FROM " + CreateDatabase.TBL_BAN).simpleQueryForLong();
//        if (soBanHienTai >= 15) {
//            // Reached the limit, return false
//            return false;
//        }
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(CreateDatabase.TBL_BAN_TENBAN, tenban);
//        contentValues.put(CreateDatabase.TBL_BAN_TINHTRANG, "false");
//        long ktra = database.insert(CreateDatabase.TBL_BAN, null, contentValues);
//        if(ktra != 0){
//            soLuongBan++; // Update counter if insert is successful
//            return true;
//        }else {
//            return false;
//        }
//    }
