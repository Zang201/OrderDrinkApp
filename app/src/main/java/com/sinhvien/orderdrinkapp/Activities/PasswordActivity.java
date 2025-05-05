package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
import com.sinhvien.orderdrinkapp.R;

public class PasswordActivity extends AppCompatActivity {
    TextInputLayout usename;
    Button resertPw;
    CreateDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        usename = (TextInputLayout) findViewById(R.id.Resetpassword);
        resertPw =(Button) findViewById(R.id.resetPassword);
        DB = new CreateDatabase( this);
        resertPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usename.getEditText().getText().toString();
                Boolean checkuser= DB.checkUsernameExist(user);
                if (checkuser==true){
                    Intent intent = new Intent(getApplicationContext(), ResetActivity.class);
                    intent.putExtra("TENDN", user);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Người dùng không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}