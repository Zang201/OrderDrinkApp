package com.sinhvien.orderdrinkapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.DAO.LoaiMonDAO;
import com.sinhvien.orderdrinkapp.DTO.BanAnDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
import com.sinhvien.orderdrinkapp.R;

public class ChuyenBanActivity extends AppCompatActivity {

    BanAnDTO banAnDTO;
    private TextInputLayout TXTL_dialog_NhapMaBan2;
    private AppCompatButton BTN_dialog_XacNhan2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuyen_ban2);



        // Ánh xạ các thành phần giao diện
        TXTL_dialog_NhapMaBan2 = findViewById(R.id.txtl_dialog_NhapMaBan2);
        BTN_dialog_XacNhan2 = findViewById(R.id.btn_dialog_XacNhan2);

        // Thêm xử lý sự kiện cho BTN_dialog_XacNhan2
        BTN_dialog_XacNhan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy mã bàn mới từ TextInputLayout
                String maBanMoi = TXTL_dialog_NhapMaBan2.getEditText().getText().toString();

                // Kiểm tra xem mã bàn mới có hợp lệ không
                if (!maBanMoi.isEmpty()) {
                    int maBanMoiInt = Integer.parseInt(maBanMoi);

                    // Lấy thông tin về bàn mới từ CSDL
                    BanAnDTO banAnDTO = getBanFromDatabase(maBanMoiInt);

                    if (banAnDTO != null) {
                        // Xử lý chuyển bàn với thông tin về bàn mới (banAnDTO)
                        // ...

                        // Sau khi hoàn thành, có thể đóng activity nếu cần
                        finish();
                    } else {
                        // Xử lý khi không tìm thấy thông tin về bàn mới
                        showAlertDialog("Không tìm thấy thông tin", "Không tìm thấy thông tin về bàn mới. Vui lòng kiểm tra lại mã bàn.");
                    }
                } else {
                    // Xử lý khi mã bàn mới không hợp lệ
                    showAlertDialog("Mã bàn mới không hợp lệ", "Vui lòng nhập mã bàn mới.");
                }
            }
        });

        // Nhận mã bàn hiện tại từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            int maBanHienTai = intent.getIntExtra("maban_hientai", -1);
            // Kiểm tra nếu mã bàn hợp lệ
            if (maBanHienTai != -1) {
                // Hiển thị mã bàn trong TextInputLayout
                TXTL_dialog_NhapMaBan2.getEditText().setText(String.valueOf(maBanHienTai));
            } else {
                // Xử lý khi không tìm thấy mã bàn
                Toast.makeText(ChuyenBanActivity.this, "Không tìm thấy thông tin về bàn mới", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Thực hiện truy vấn CSDL để lấy thông tin về bàn mới
    private BanAnDTO getBanFromDatabase(int maBanMoi) {
        String query = "SELECT * FROM " + CreateDatabase.TBL_BAN + " WHERE " + CreateDatabase.TBL_BAN_MABAN + " = " + maBanMoi;
        Cursor cursor = banAnDTO.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_BAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TENBAN)));
            banAnDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_BAN_TINHTRANG)));
            cursor.close();
            return banAnDTO;
        } else {
            // Xử lý khi không tìm thấy thông tin về bàn mới
            Log.e("ChuyenBanActivity", "Không tìm thấy thông tin về bàn mới");
            // Hoặc sử dụng Toast để hiển thị thông báo ngắn
            // Toast.makeText(ChuyenBanActivity.this, "Không tìm thấy thông tin về bàn mới", Toast.LENGTH_SHORT).show();

            // Hoặc sử dụng AlertDialog để hiển thị thông báo chi tiết hơn
            showAlertDialog("Không tìm thấy thông tin", "Không tìm thấy thông tin về bàn mới. Vui lòng kiểm tra lại mã bàn.");

            return null;
        }
    }

    // Phương thức hiển thị AlertDialog
    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChuyenBanActivity.this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng nhấn OK (nếu cần)
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

//        // Thêm xử lý sự kiện cho nút Xác nhận
//        BTN_dialog_XacNhan2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Lấy giá trị từ TextInputLayout
//                String maBanMoi = TXTL_dialog_NhapMaBan2.getEditText().getText().toString();
//                // Kiểm tra nếu mã bàn mới không rỗng
//                if (!maBanMoi.isEmpty()) {
//                    // Chuyển đến DisplayCategoryFragment và truyền thông tin về mã bàn mới
//                    Intent intent = new Intent(ChuyenBanActivity.this, AddTableActivity.class);
//
//                    // Thực hiện truy vấn CSDL để lấy thông tin về bàn mới
//                    Ban banMoi = getBanFromDatabase(Integer.parseInt(maBanMoi));
//                    if (banMoi != null) {
//                        intent.putExtra("ban_moi", banMoi);
//
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    } else {
//                        // Xử lý khi không tìm thấy thông tin về bàn mới
//                        Toast.makeText(ChuyenBanActivity.this, "Không tìm thấy thông tin về bàn mới", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Xử lý khi người dùng không nhập mã bàn mới
//                    Toast.makeText(ChuyenBanActivity.this, "Vui lòng nhập mã bàn mới", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//            }
//        });
//        }



