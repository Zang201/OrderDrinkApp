//package com.sinhvien.orderdrinkapp.Activities;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.sinhvien.orderdrinkapp.CustomAdapter.TimeAdapter;
//import com.sinhvien.orderdrinkapp.DAO.TimeDAO;
//import com.sinhvien.orderdrinkapp.DTO.TimeDTO;
//import com.sinhvien.orderdrinkapp.Fragments.BlankFragmentLuong;
//import com.sinhvien.orderdrinkapp.R;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class TimeActivity extends AppCompatActivity {
//
//    private ImageView imgBackBtn;
//    private TextView txtTenNhanVien;
//    private TextView txtThang;
//    private ListView lvTime;
//    private TextView txtSumTime;
//    private Button btnXacNhan;
//    private String tenNhanVien;
//    private String thang;
//    private TimeDAO timeDAO;
//    private List<TimeDTO> timeList;
//    private long totalTimeMillis;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_time);
//
//        // Ánh xạ các thành phần từ layout XML
//        imgBackBtn = findViewById(R.id.img_time_backbtn);
//        txtTenNhanVien = findViewById(R.id.txt_time_TenNhanVien);
//        txtThang = findViewById(R.id.txt_time_Tháng);
//        lvTime = findViewById(R.id.lvListTime);
//        txtSumTime = findViewById(R.id.txt_time_SumTime);
//        btnXacNhan = findViewById(R.id.btn_time_XacNhan);
//
//        // Thiết lập sự kiện click cho nút Back
//        imgBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Chuyển đến HomeActivity khi người dùng nhấn nút Back
//                Intent intent = new Intent(TimeActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Lấy thông tin về tên nhân viên và tháng từ Intent
//        tenNhanVien = getIntent().getStringExtra("tendn");
//        thang = getIntent().getStringExtra("thang");
//
//        // Hiển thị thông tin về tên nhân viên và tháng
//        txtTenNhanVien.setText("Nhân viên: " + tenNhanVien);
//        txtThang.setText("Tháng " + thang);
//
//        // Tạo một đối tượng TimeDAO
//        timeDAO = new TimeDAO(this);
//
//        // Hiển thị danh sách thời gian đăng nhập và đăng xuất của nhân viên trong tháng
//        displayTimeList();
//
//        // Tính và hiển thị tổng thời gian làm việc của nhân viên trong tháng
//        displayTotalTime();
//    }
//
//    private void displayTimeList() {
//        // Lấy danh sách thời gian đăng nhập và đăng xuất của nhân viên trong tháng
//        timeList = timeDAO.getAllTime();
//
//        // Tạo một Adapter để hiển thị danh sách thời gian đăng nhập và đăng xuất trên ListView
//        TimeAdapter adapter = new TimeAdapter(this, timeList);
//
//        // Đặt Adapter cho ListView
//        lvTime.setAdapter(adapter);
//    }
//
//    private void displayTotalTime() {
//        // Lấy tổng thời gian làm việc của nhân viên trong tháng
//        totalTimeMillis = timeDAO.getTotalTime();
//
//        // Hiển thị tổng thời gian làm việc trên giao diện người dùng
//        txtSumTime.setText("Tổng thời gian làm việc: " + formatTime(totalTimeMillis));
//    }
//
//    private String formatTime(long milliseconds) {
//        // Chuyển đổi tổng thời gian từ milliseconds sang giờ, phút và giây
//        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
//
//        // Format chuỗi thời gian và trả về
//        return String.format("%02d giờ %02d phút %02d giây", hours, minutes, seconds);
//    }
//}
//
