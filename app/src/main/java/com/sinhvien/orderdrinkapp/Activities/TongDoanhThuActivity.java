package com.sinhvien.orderdrinkapp.Activities;
import android.app.DatePickerDialog;
import android.icu.text.DecimalFormat;
import android.view.View;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sinhvien.orderdrinkapp.DAO.DonDatDAO;
import com.sinhvien.orderdrinkapp.R;

import java.util.Calendar;
import java.util.Locale;

public class TongDoanhThuActivity extends AppCompatActivity {
    DonDatDAO donDatDAO;
    private Button btnTuNgay1, btnDenNgay1, btnDoanhThu1;
    private EditText edtTuNgay1, edtDenNgay1;
    private TextView tvDoanhThuNgay1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_doanh_thu);

        // Mở kết nối với cơ sở dữ liệu
        DonDatDAO donDatDAO = new DonDatDAO(this);
        donDatDAO.open();

        // Ánh xạ views từ XML
        btnTuNgay1 = findViewById(R.id.btnTuNgay1);
        btnDenNgay1 = findViewById(R.id.btnDenNgay1);
        btnDoanhThu1 = findViewById(R.id.btnDoanhThu1);
        edtTuNgay1 = findViewById(R.id.edtTuNgay1);
        edtDenNgay1 = findViewById(R.id.edtDenNgay1);
        tvDoanhThuNgay1 = findViewById(R.id.tvDoanhThuNgay1);

        // Xử lý khi nút btnTuNgay1 được nhấn
        btnTuNgay1.setOnClickListener(v -> showDatePickerDialog(edtTuNgay1));

// Xử lý khi nút btnTuNgay2 được nhấn
        btnDenNgay1.setOnClickListener(v -> showDatePickerDialog(edtDenNgay1));

        // Xử lý sự kiện khi nút "Doanh thu" được nhấn
        btnDoanhThu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày bắt đầu và kết thúc từ EditText
                String ngayBatDau = edtTuNgay1.getText().toString();
                String ngayKetThuc = edtDenNgay1.getText().toString();

                // Kiểm tra xem ngày bắt đầu và kết thúc có rỗng hay không
                if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
                    Toast.makeText(TongDoanhThuActivity.this, "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một đối tượng DonDatDAO
                DonDatDAO donDatDAO = new DonDatDAO(TongDoanhThuActivity.this);

                // Mở kết nối với cơ sở dữ liệu
                donDatDAO.open();

                // Gọi phương thức TinhTongDoanhThu từ DonDatDAO và lưu kết quả
                double tongDoanhThu = donDatDAO.TinhTongDoanhThu(ngayBatDau, ngayKetThuc);

                // Đóng kết nối với cơ sở dữ liệu
                donDatDAO.close();

                // Định dạng số tiền với dấu chấm cách 3 số
                DecimalFormat df = new DecimalFormat("#,###.## VND");
                String formattedTongDoanhThu = df.format(tongDoanhThu);

                // Hiển thị tổng doanh thu với dấu chấm cách 3 số và VND phía sau lên TextView
                tvDoanhThuNgay1.setText(formattedTongDoanhThu);
            }
        });

//        btnDoanhThu1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Lấy ngày bắt đầu và kết thúc từ EditText
//                String ngayBatDau = edtTuNgay1.getText().toString();
//                String ngayKetThuc = edtDenNgay1.getText().toString();
//
//                // Kiểm tra xem ngày bắt đầu và kết thúc có rỗng hay không
//                if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
//                    Toast.makeText(TongDoanhThuActivity.this, "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Tạo một đối tượng DonDatDAO
//                DonDatDAO donDatDAO = new DonDatDAO(TongDoanhThuActivity.this);
//
//                // Mở kết nối với cơ sở dữ liệu
//                donDatDAO.open();
//
//                // Gọi phương thức TinhTongDoanhThu từ DonDatDAO và lưu kết quả
//                double tongDoanhThu = donDatDAO.TinhTongDoanhThu(ngayBatDau, ngayKetThuc);
//
//                // Đóng kết nối với cơ sở dữ liệu
//                donDatDAO.close();
//
//                // Hiển thị tổng doanh thu với VND phía sau lên TextView
//                tvDoanhThuNgay1.setText(String.format(Locale.getDefault(), "%.2f VND", tongDoanhThu));
//            }
//        });


    }

    // Hàm để hiển thị DatePickerDialog và cập nhật EditText khi người dùng chọn ngày
// Hàm để hiển thị DatePickerDialog và cập nhật EditText khi người dùng chọn ngày
    private void showDatePickerDialog(final EditText editText) {
        // Lấy ngày, tháng, năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(TongDoanhThuActivity.this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Đặt ngày đã chọn vào EditText
                    String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth1, monthOfYear + 1, year1);
                    editText.setText(selectedDate);
                }, year, month, dayOfMonth);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }




}