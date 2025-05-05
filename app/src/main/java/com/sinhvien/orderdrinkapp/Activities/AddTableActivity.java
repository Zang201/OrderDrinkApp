package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.DAO.BanAnDAO;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.DAO.QuyenDAO;
import com.sinhvien.orderdrinkapp.DTO.NhanVienDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
import com.sinhvien.orderdrinkapp.R;

public class AddTableActivity extends AppCompatActivity {

    TextInputLayout TXTL_addtable_tenban;
    Button BTN_addtable_TaoBan;
    BanAnDAO banAnDAO;
    QuyenDAO quyenDAO;
    NhanVienDAO nhanVienDAO;
    NhanVienDTO nhanVienDTO;
    SharedPreferences sharedPreferences;
    public static int maquyen = 0;
    public static int manv = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtable_layout);
        //region Lấy đối tượng trong view
        TXTL_addtable_tenban = (TextInputLayout) findViewById(R.id.txtl_addtable_tenban);
        BTN_addtable_TaoBan = (Button) findViewById(R.id.btn_addtable_TaoBan);

        banAnDAO = new BanAnDAO(this);
        quyenDAO = new QuyenDAO(this);
        nhanVienDAO = new NhanVienDAO(this);

//        //lấy file share prefer
//        sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
//        maquyen = sharedPreferences.getInt("maquyen",0);
//        manv=  sharedPreferences.getInt("manv",0);





        BTN_addtable_TaoBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTenBanAn = TXTL_addtable_tenban.getEditText().getText().toString();
                if(validateName()){
                    // Kiểm tra quyền của nhân viên hiện tại
                    int maQuyen = getCurrentUserPermission();
                    // Kiểm tra nếu nhân viên có quyền "Chạy bàn" hoặc "Phục vụ" (MAQUYEN == 2 hoặc MAQUYEN == 3)
                    if(maQuyen == 2 || maQuyen == 3){
                        Toast.makeText(AddTableActivity.this, "Bạn không có quyền thêm bàn.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Tiếp tục thêm bàn nếu nhân viên không có quyền "Chạy bàn" hoặc "Phục vụ"
                        boolean ktra = banAnDAO.ThemBanAn(sTenBanAn);
                        // Trả về kết quả cho displaytable
                        Intent intent = new Intent();
                        intent.putExtra("ketquathem", ktra);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }

            private int getCurrentUserPermission(){
                // Lấy mã quyền từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                return sharedPreferences.getInt("maquyen", 0);
            }
        });

    }
    //validate dữ liệu
    private boolean validateName(){
        String val = TXTL_addtable_tenban.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addtable_tenban.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addtable_tenban.setError(null);
            TXTL_addtable_tenban.setErrorEnabled(false);
            return true;
        }
    }
}