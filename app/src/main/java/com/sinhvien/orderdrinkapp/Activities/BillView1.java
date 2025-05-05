
package com.sinhvien.orderdrinkapp.Activities;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TableLayout;
        import android.widget.TextView;

        import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
        import com.sinhvien.orderdrinkapp.DAO.ThanhToanDAO;
        import com.sinhvien.orderdrinkapp.R;

        import java.util.ArrayList;

public class BillView1 extends AppCompatActivity {

    // Lấy dữ liệu thanh toán
    NhanVienDAO nhanVienDAO; // DAO cho nhân viên
    ThanhToanDAO thanhToanDAO; // DAO cho thanh toán
    int sodiem; // Biến lưu trữ ID của hóa đơn
    SharedPreferences sharedPreferences; // SharedPreferences để lưu trữ ID
    SharedPreferences.Editor editor; // Editor cho SharedPreferences
    private static int lastGeneratedID = 0; // Biến static để theo dõi giá trị cuối cùng của ID
    ImageView img_detailstatistic_backbtn; // ImageView cho nút quay lại
    TextView tvIDHOADON, tvTongTien, tvHovatenNV, tvNgayDat; // TextViews để hiển thị thông tin hóa đơn
    TableLayout tableLayout; // TableLayout cho bảng hiển thị thông tin hóa đơn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view1);
        tableLayout = findViewById(R.id.tbLyaout); // Ánh xạ TableLayout từ layout XML

        // Khởi tạo các TextView
        img_detailstatistic_backbtn = findViewById(R.id.img_detailstatistic_backbtn); // Ánh xạ ImageView từ layout XML
        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nút quay lại được nhấn
                Intent intent = new Intent(BillView1.this, HomeActivity.class); // Tạo Intent để chuyển đến HomeActivity
                startActivity(intent); // Khởi chạy Intent
            }
        });

        // Khởi tạo các đối tượng DAO
        nhanVienDAO = new NhanVienDAO(BillView1.this); // Khởi tạo đối tượng DAO cho nhân viên
        thanhToanDAO = new ThanhToanDAO(this); // Khởi tạo đối tượng DAO cho thanh toán

        // Ánh xạ các TextView từ layout XML
        tvHovatenNV = findViewById(R.id.tvHovatenNV);
        tvIDHOADON = findViewById(R.id.tvIDHOADON);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvNgayDat = findViewById(R.id.tvNgayDat);

        // Khởi tạo SharedPreferences để lưu trữ ID
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit(); // Lấy Editor của SharedPreferences

// Lấy trạng thái của biến resetID từ SharedPreferences, mặc định là true
        boolean resetID = sharedPreferences.getBoolean("resetID", true);

        if (resetID) {
            // Xóa ID trong SharedPreferences và thiết lập lại từ đầu
            editor.remove("lastGeneratedID"); // Xóa ID hiện tại
            // Thiết lập lại ID từ đầu là 98
            sodiem = 98;
        } else {
            // Lấy ID hiện tại từ SharedPreferences, mặc định là 96
            sodiem = sharedPreferences.getInt("lastGeneratedID", 96);
            // Tăng ID lên 1 sau mỗi lần tạo hóa đơn
            sodiem++;
        }

// Lưu trạng thái mới của resetID
        editor.putBoolean("resetID", false);
// Lưu ID mới vào SharedPreferences
        editor.putInt("lastGeneratedID", sodiem);
        editor.apply();




//        // Khởi tạo SharedPreferences để lưu trữ ID
//        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit(); // Lấy Editor của SharedPreferences
//
//        // Kiểm tra nếu bạn muốn thiết lập lại ID từ đầu
//        boolean resetID = true; // Đặt biến này thành true nếu bạn muốn thiết lập lại ID từ đầu
//
//        if (resetID) {
//            // Xóa ID trong SharedPreferences và thiết lập lại từ đầu
//            editor.remove("lastGeneratedID"); // Xóa ID hiện tại
//            editor.apply(); // Áp dụng thay đổi
//            // Thiết lập lại ID từ đầu là 58
//            sodiem = 60;
//        } else {
//            sodiem = sharedPreferences.getInt("lastGeneratedID", 60); // Lấy ID hiện tại từ SharedPreferences, mặc định là 58
//            sodiem++; // Tăng ID lên 1 sau mỗi lần tạo hóa đơn
//        }

        // Trích xuất dữ liệu từ Intent
        Intent intent = getIntent();
        String ngayDat = intent.getStringExtra("ngay_dat"); // Lấy ngày đặt từ Intent
        String tenBan = intent.getStringExtra("ten_ban"); // Lấy tên bàn từ Intent
        String tongTien = intent.getStringExtra("tong_tien"); // Lấy tổng tiền từ Intent

        // Hiển thị dữ liệu trên các TextView
        tvIDHOADON.setText("ID HÓA ĐƠN: " + sodiem); // Hiển thị ID hóa đơn
        tvHovatenNV.setText("Mã bàn: " + tenBan); // Hiển thị mã bàn
        tvTongTien.setText("Tổng Tiền " + tongTien + " VND"); // Hiển thị tổng tiền
        tvNgayDat.setText("Ngày đặt: " + ngayDat); // Hiển thị ngày đặt

        // Lưu giá trị mới của sodiem vào SharedPreferences
        editor.putInt("lastGeneratedID", sodiem); // Lưu ID mới vào SharedPreferences
        editor.apply(); // Áp dụng thay đổi
    }
}
