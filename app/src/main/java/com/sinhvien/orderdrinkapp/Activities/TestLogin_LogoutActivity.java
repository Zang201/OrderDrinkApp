package com.sinhvien.orderdrinkapp.Activities;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextClock;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.R;

public class TestLogin_LogoutActivity extends AppCompatActivity {
    private TextView timeLogoutTextView;
    private TextView returnLogoutTextView;
    private TextView timeLoginTextView;
    private TextView returnLoginTextView;
    private TextView timeSumTextView;
    private TextView returnTimeSumTextView;
    private TextClock textClock;
    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login_logout);
        // Khởi tạo đối tượng NhanVienDAO
        NhanVienDAO nhanVienDAO = new NhanVienDAO(this);

        // Ánh xạ các thành phần từ layout XML
        timeLogoutTextView = findViewById(R.id.time_logout);
        returnLogoutTextView = findViewById(R.id.return_logout);
        timeLoginTextView = findViewById(R.id.time_login);
        returnLoginTextView = findViewById(R.id.return_login);
        timeSumTextView = findViewById(R.id.time_sum);
        returnTimeSumTextView = findViewById(R.id.return_time_sum);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        String currentTime = dateFormat.format(calendar.getTime());


        textClock = findViewById(R.id.text_clock);
        textClock.setText("Thời gian hiện tại: " + currentTime);



    }
}