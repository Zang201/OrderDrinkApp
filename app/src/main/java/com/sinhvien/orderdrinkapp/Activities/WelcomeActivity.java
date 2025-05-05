package com.sinhvien.orderdrinkapp.Activities;

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

import com.sinhvien.orderdrinkapp.R;

public class WelcomeActivity extends AppCompatActivity {
    Button btn_login, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);



//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WelcomeActivity.this, AddActivity.class);
//                startActivity(intent);
//            }
//        });

    }
    //chuyển sang trang đăng nhập

    public void callLoginFromWel(View view)
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_login),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
    // chuyển sang trang đăng ký
    public void callSignUpFromWel(View view) {
        // Lấy mã quyền của người dùng từ SharedPreferences
        int maQuyen = getCurrentUserPermission();

        // Kiểm tra xem mã quyền có bằng 1 hay không
        if (maQuyen == 1) {
            // Nếu có mã quyền = 1, hiển thị thông báo và không cho phép chuyển qua trang đăng ký
            Toast.makeText(this, "Đã tồn tại tài khoản quản lý!", Toast.LENGTH_SHORT).show();
        } else {
            // Nếu không, chuyển qua trang đăng ký
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(findViewById(R.id.btn_signup), "transition_signup");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    private int getCurrentUserPermission() {
        // Lấy mã quyền từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("maquyen", 0);
    }
}


//package com.sinhvien.orderdrinkapp.Activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Pair;
//import android.view.View;
//import android.widget.Toast;
//
//import com.sinhvien.orderdrinkapp.R;
//
//public class WelcomeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.welcome_layout);
//
//    }
//    //chuyển sang trang đăng nhập
//    public void callLoginFromWel(View view)
//    {
//        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//
//        Pair[] pairs = new Pair[1];
//        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_login),"transition_login");
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
//            startActivity(intent,options.toBundle());
//        }
//        else {
//            startActivity(intent);
//        }
//    }
//    // chuyển sang trang đăng ký
//    public void callSignUpFromWel(View view)
//    {
////        Toast.makeText(this, "Đã tồn tại tài khoản quản lí!", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
//        Pair[] pairs = new Pair[1];
//        pairs[0] = new Pair<View, String>(findViewById(R.id.btn_signup),"transition_signup");
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
//            startActivity(intent,options.toBundle());
//        }
//        else {
//            startActivity(intent);
//        }
//    }
//}