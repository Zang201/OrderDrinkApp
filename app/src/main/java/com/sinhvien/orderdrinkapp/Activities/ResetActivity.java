package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
import com.sinhvien.orderdrinkapp.R;

public class ResetActivity extends AppCompatActivity {
    TextView username;
    TextInputLayout pass, repass;
    Button conform;
    CreateDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        username =(TextView) findViewById(R.id.user_name_resettex);
        pass= (TextInputLayout) findViewById(R.id.passwork_reset);
        repass= (TextInputLayout) findViewById(R.id.repasswork_reset);
        conform=(Button) findViewById(R.id.btnconform);
        DB = new CreateDatabase(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("TENDN"));
        conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String password = pass.getEditText().getText().toString(); // Sử dụng getText() để lấy dữ liệu từ EditText
                String repassword = repass.getEditText().getText().toString(); // Sử dụng getText() để lấy dữ liệu từ EditText

                if (password.equals(repassword)) {
                    Boolean check = DB.updatePassword(user, password);

                    if (check) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ResetActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResetActivity.this, "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}