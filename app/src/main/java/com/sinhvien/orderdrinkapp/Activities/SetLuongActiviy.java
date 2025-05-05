package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.orderdrinkapp.CustomAdapter.AdapterLuong;
import com.sinhvien.orderdrinkapp.DAO.LuongDAO;
import com.sinhvien.orderdrinkapp.DTO.LuongDTO;
import com.sinhvien.orderdrinkapp.R;

public class SetLuongActiviy extends AppCompatActivity {
    private TextView tvManv;
    private EditText etLUONGTHUONG,etLUONGPHAT,etSoGio,etTHANGNAM,etLUONGMOTGIO;
    private Button btnThem;
    private LuongDAO luongDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_luong_activiy);
        tvManv = findViewById(R.id.tvMANV);
        etLUONGPHAT = findViewById(R.id.etLUONGPHAT);
        etLUONGTHUONG = findViewById(R.id.etLUONGTHUONG);
        etTHANGNAM = findViewById(R.id.etTHANGNAM);
        etSoGio = findViewById(R.id.etSOGIO);
        etLUONGMOTGIO = findViewById(R.id.etLUONGMOTGIO);
        luongDAO = new LuongDAO(SetLuongActiviy.this);
        btnThem = findViewById(R.id.btnSET);
        int maNhanvien = getIntent().getIntExtra("MaNhanVien",1001);
        tvManv.setText("MÃ NHÂN VIÊN : "+ maNhanvien);

        btnThem.setOnClickListener(view ->{
            String luongthuong = etLUONGTHUONG.getText().toString();
            String luongphat = etLUONGPHAT.getText().toString();
            String sogio = etSoGio.getText().toString();
            String thangnam = etTHANGNAM.getText().toString();
            String mucluong = etLUONGMOTGIO.getText().toString();

            if(sogio.isEmpty()){
                Toast.makeText(SetLuongActiviy.this,"Số giờ không được bỏ trống",Toast.LENGTH_SHORT).show();
            } else if (thangnam.isEmpty()) {
                Toast.makeText(SetLuongActiviy.this,"Tháng năm không được bỏ trống",Toast.LENGTH_SHORT).show();
            } else if (mucluong.isEmpty()) {
                Toast.makeText(SetLuongActiviy.this,"Mức lương không được bỏ trống",Toast.LENGTH_SHORT).show();
            } else if(luongDAO.check(maNhanvien, thangnam)){
                if(luongthuong.isEmpty()) luongthuong="0";
                if(luongphat.isEmpty()) luongphat="0";
                LuongDTO luongDTO = new LuongDTO(Integer.valueOf(luongthuong),Integer.valueOf(luongphat),Integer.valueOf(sogio),thangnam,Integer.valueOf(mucluong),maNhanvien);
                if(luongDAO.SetLuong(luongDTO)){
                    Toast.makeText(SetLuongActiviy.this,"Set Lương thành công",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SetLuongActiviy.this,"Lương tháng này đã tồn tại",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SetLuongActiviy.this,"Lương tháng này đã tồn tại",Toast.LENGTH_SHORT).show();
            }
        });

    }
}









//package com.sinhvien.orderdrinkapp.Activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.sinhvien.orderdrinkapp.DAO.LuongDAO;
//import com.sinhvien.orderdrinkapp.DTO.LuongDTO;
//import com.sinhvien.orderdrinkapp.R;
//
//import java.util.SplittableRandom;
//
//public class SetLuongActiviy extends AppCompatActivity {
//    private TextView tvManv;
//    private EditText etLUONGTHUONG,etLUONGPHAT,etSoGio,etTHANGNAM, etLuongMotGio;
//    private Button btnThem;
//    private LuongDAO luongDAO;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set_luong_activiy);
//        tvManv = findViewById(R.id.tvMANV);
//        etLUONGPHAT = findViewById(R.id.etLUONGPHAT);
//        etLUONGTHUONG = findViewById(R.id.etLUONGTHUONG);
//        etTHANGNAM = findViewById(R.id.etTHANGNAM);
//        etSoGio = findViewById(R.id.etSOGIO);
//        etLuongMotGio = findViewById(R.id.etLUONGMOTGIO);
//        luongDAO = new LuongDAO(SetLuongActiviy.this);
//        btnThem = findViewById(R.id.btnSET);
//        int maNhanvien = getIntent().getIntExtra("MaNhanVien",1001);
//        tvManv.setText("MÃ NHÂN VIÊN : "+ maNhanvien);
//
////        btnThem.setOnClickListener(view -> {
////            String luongthuong = etLUONGTHUONG.getText().toString();
////            String luongphat = etLUONGPHAT.getText().toString();
////            String sogio = etSoGio.getText().toString();
////            String thangnam = etTHANGNAM.getText().toString();
////            String mucluong = etLuongMotGio.getText().toString();
////
////            // Kiểm tra xem các trường nhập liệu có trống không
////            if (sogio.isEmpty() || thangnam.isEmpty() || mucluong.isEmpty()) {
////                Toast.makeText(SetLuongActiviy.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            int luongThuong = luongthuong.isEmpty() ? 0 : Integer.parseInt(luongthuong);
////            int luongPhat = luongphat.isEmpty() ? 0 : Integer.parseInt(luongphat);
////            int soGio = Integer.parseInt(sogio);
////            int mucLuong = Integer.parseInt(mucluong);
////
////            LuongDTO luongDTO = new LuongDTO(String.valueOf(mucLuong), String.valueOf(luongThuong), String.valueOf(luongPhat), String.valueOf(soGio), thangnam, maNhanvien);
////
////            // Kiểm tra lương tháng này đã tồn tại hay chưa
////            if (luongDAO.check(maNhanvien, thangnam)) {
////                // Thêm lương vào cơ sở dữ liệu
////                if (luongDAO.SetLuong(luongDTO)) {
////                    Toast.makeText(SetLuongActiviy.this, "Set Lương thành công", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(SetLuongActiviy.this, "Lỗi khi thêm lương", Toast.LENGTH_SHORT).show();
////                }
////            } else {
////                Toast.makeText(SetLuongActiviy.this, "Lương tháng này đã tồn tại", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//
//
//        btnThem.setOnClickListener(view ->{
//            String luongthuong = etLUONGTHUONG.getText().toString();
//            String luongphat = etLUONGPHAT.getText().toString();
//            String sogio = etSoGio.getText().toString();
//            String thangnam = etTHANGNAM.getText().toString();
//            String luonggio = etLuongMotGio.getText().toString();
//
//
//            if(sogio.isEmpty()){
//                Toast.makeText(SetLuongActiviy.this,"Số giờ không được bỏ trống",Toast.LENGTH_SHORT).show();
//            }else if(luongDAO.check(maNhanvien,thangnam)==true){
//                if(luongthuong.isEmpty()) luongthuong="0";
//                if(luongphat.isEmpty()) luongphat="0";
//                LuongDTO luongDTO = new LuongDTO(Integer.valueOf(luongthuong),Integer.valueOf(luongphat),Integer.valueOf(sogio),thangnam,Integer.valueOf(luonggio),maNhanvien);
//                if(luongDAO.SetLuong(luongDTO)){
//                    Toast.makeText(SetLuongActiviy.this,"Set Lương thành công",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(SetLuongActiviy.this,"Lương tháng này đã tồn tại",Toast.LENGTH_SHORT).show();
//                }
//            }else{
//                Toast.makeText(SetLuongActiviy.this,"Lương tháng này đã tồn tại",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}