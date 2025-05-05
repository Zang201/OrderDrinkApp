//package com.sinhvien.orderdrinkapp.Activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ActivityOptions;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Pair;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.material.textfield.TextInputLayout;
//import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
//import com.sinhvien.orderdrinkapp.R;
//
//public class LoginActivity extends AppCompatActivity {
//    Button BTN_login_DangNhap, BTN_login_DangKy;
//    TextInputLayout TXTL_login_TenDN, TXTL_login_MatKhau;
//    NhanVienDAO nhanVienDAO;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_layout);
//
//        //thuộc tính view
//        TXTL_login_TenDN = (TextInputLayout)findViewById(R.id.txtl_login_TenDN);
//        TXTL_login_MatKhau = (TextInputLayout)findViewById(R.id.txtl_login_MatKhau);
//        BTN_login_DangNhap = (Button)findViewById(R.id.btn_login_DangNhap);
//        BTN_login_DangKy = (Button)findViewById(R.id.btn_login_DangKy);
//
//        nhanVienDAO = new NhanVienDAO(this);    //khởi tạo kết nối csdl
//
//        BTN_login_DangNhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!validateUserName() | !validatePassWord()){
//                    return;
//                }
//
//                String tenDN = TXTL_login_TenDN.getEditText().getText().toString();
//                String matKhau = TXTL_login_MatKhau.getEditText().getText().toString();
//                int ktra = nhanVienDAO.KiemTraDN(tenDN,matKhau);
//                int maquyen = nhanVienDAO.LayQuyenNV(ktra);
//                if(ktra != 0 ){
//                    // lưu mã quyền vào shareprefer
//                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor =sharedPreferences.edit();
//                    editor.putInt("maquyen",maquyen);
//                    editor.putInt("manv",ktra);
//                    editor.commit();
//
//                    //gửi dữ liệu user qua trang chủ
//                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
//                    intent.putExtra("tendn",TXTL_login_TenDN.getEditText().getText().toString());
//                    intent.putExtra("manv",ktra);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    //Hàm quay lại màn hình chính
//    public void backFromLogin(View view)
//    {
//        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
//        //tạo animation cho thành phần
//        Pair[] pairs = new Pair[1];
//        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
//            startActivity(intent,options.toBundle());
//        }
//        else {
//            startActivity(intent);
//        }
//    }
//
//    //Hàm chuyển qua trang đăng ký
//    public void callRegisterFromLogin(View view)
//    {
//        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//    }
//
//    //region Validate field
//    private boolean validateUserName(){
//        String val = TXTL_login_TenDN.getEditText().getText().toString().trim();
//
//        if(val.isEmpty()){
//            TXTL_login_TenDN.setError(getResources().getString(R.string.not_empty));
//            return false;
//        }else {
//            TXTL_login_TenDN.setError(null);
//            TXTL_login_TenDN.setErrorEnabled(false);
//            return true;
//        }
//    }
//
//    private boolean validatePassWord(){
//        String val = TXTL_login_MatKhau.getEditText().getText().toString().trim();
//
//        if(val.isEmpty()){
//            TXTL_login_MatKhau.setError(getResources().getString(R.string.not_empty));
//            return false;
//        }else{
//            TXTL_login_MatKhau.setError(null);
//            TXTL_login_MatKhau.setErrorEnabled(false);
//            return true;
//        }
//    }
//    //endregion
//}

package com.sinhvien.orderdrinkapp.Activities;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
//import com.sinhvien.orderdrinkapp.DAO.TimeDAO;
import com.sinhvien.orderdrinkapp.DTO.TimeDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
import com.sinhvien.orderdrinkapp.R;

public class LoginActivity extends AppCompatActivity {

    Button BTN_login_DangNhap, BTN_login_DangKy, btn;
    TextInputLayout TXTL_login_TenDN, TXTL_login_MatKhau;
    NhanVienDAO nhanVienDAO;
//    TimeDAO timeDAO;
    CreateDatabase createDatabase;
    public static int manv = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //thuộc tính view
        btn =(Button)findViewById(R.id.btn_login_quenmk);
        TXTL_login_TenDN = (TextInputLayout)findViewById(R.id.txtl_login_TenDN);
        TXTL_login_MatKhau = (TextInputLayout)findViewById(R.id.txtl_login_MatKhau);
        BTN_login_DangNhap = (Button)findViewById(R.id.btn_login_DangNhap);
        BTN_login_DangKy = (Button)findViewById(R.id.btn_login_DangKy);

        BTN_login_DangKy.setVisibility(View.GONE);
//        timeDAO = new TimeDAO(this);
        nhanVienDAO = new NhanVienDAO(this);    //khởi tạo kết nối csdl


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });
        BTN_login_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName() || !validatePassWord()){
                    return;
                }

                String tenDN = TXTL_login_TenDN.getEditText().getText().toString();
                String matKhau = TXTL_login_MatKhau.getEditText().getText().toString();
                int ktra = nhanVienDAO.KiemTraDN(tenDN, matKhau);
                int maquyen = nhanVienDAO.LayQuyenNV(ktra);
                if(ktra != 0 ){
                    // Lưu mã quyền vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("maquyen",maquyen);
                    editor.putInt("manv",ktra);
                    editor.apply();

                    // Lấy thời gian đăng nhập
                    String loginTime = getCurrentTime();

                    // Tạo đối tượng TimeDTO để chứa thông tin thời gian đăng nhập
                    TimeDTO timeDTO = new TimeDTO();
                    timeDTO.setThoiGianDangNhap(loginTime);

                    // Chèn thông tin thời gian đăng nhập vào cơ sở dữ liệu
//                    TimeDAO timeDAO = new TimeDAO(LoginActivity.this);
//                    timeDAO.insertTime(timeDTO);

                    // Gửi dữ liệu user qua HomeActivity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("tendn", tenDN);
                    intent.putExtra("manv", ktra);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                }
            }

            private String getCurrentTime() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                Date currentTime = new Date();
                return dateFormat.format(currentTime);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutLogin),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
//    public void callRegisterFromLogin(View view)
//    {
////        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
////        startActivity(intent);
////        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        Toast.makeText(this, "Đã tồn tại tài khoản quản lý!", Toast.LENGTH_SHORT).show();
//    }
    public void callRegisterFromLogin(View view) {
        // Lấy mã quyền của người dùng từ SharedPreferences
        int maQuyen = getCurrentUserPermission();

        // Kiểm tra xem mã quyền có bằng 1 hay không
        if (maQuyen == 1) {
            // Nếu có mã quyền = 1, hiển thị thông báo và không cho phép chuyển qua trang đăng ký
            Toast.makeText(this, "Đã tồn tại tài khoản quản lý!", Toast.LENGTH_SHORT).show();
        } else {
            // Nếu không, chuyển qua trang đăng ký
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    private int getCurrentUserPermission() {
        // Lấy mã quyền từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("maquyen", 0);
    }

    //region Validate field
    private boolean validateUserName(){
        String val = TXTL_login_TenDN.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_login_TenDN.setError(null);
            TXTL_login_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_login_MatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_login_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else{
            TXTL_login_MatKhau.setError(null);
            TXTL_login_MatKhau.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}