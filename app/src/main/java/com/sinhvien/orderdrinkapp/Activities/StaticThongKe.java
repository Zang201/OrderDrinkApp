package com.sinhvien.orderdrinkapp.Activities;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Button;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sinhvien.orderdrinkapp.DAO.DonDatDAO;
import com.sinhvien.orderdrinkapp.DAO.ThanhToanDAO;
import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
import com.sinhvien.orderdrinkapp.DTO.ThanhToanDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;
import com.sinhvien.orderdrinkapp.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint("SimpleDateFormat")
public class StaticThongKe extends AppCompatActivity {
    Button btnTuNgay, btnDenNgay, btnDoanhThu;
    EditText edtTuNgay, edtDenNgay;
    TextView tvDoanhThu;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int mYear, mMonth, mDay;

    private Spinner spinnerYear,sprinerMonth;
    private HorizontalBarChart chartYear,chartWeekMonth;
    private TextView tvdaonhthunam;

    DonDatDAO donDatDAO;
    List<DonDatDTO> listdondat;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_thong_ke);

        // Doanh thu ngay
        btnTuNgay = findViewById(R.id.btnTuNgay);
        btnDenNgay = findViewById(R.id.btnDenNgay);
        btnDoanhThu = findViewById(R.id.btnDoanhThu);
        edtTuNgay = findViewById(R.id.edtTuNgay);
        edtDenNgay = findViewById(R.id.edtDenNgay);
        tvDoanhThu = findViewById(R.id.tvDoanhThuNgay);

        // Phan do thi
        spinnerYear = findViewById(R.id.spinner_year);
        sprinerMonth = findViewById(R.id.spinner_month);
        chartYear = findViewById(R.id.chartYear);

        //Doanh thu
        tvdaonhthunam = findViewById(R.id.tvdoanhthu);
        chartWeekMonth = findViewById(R.id.charMonth);

        donDatDAO = new DonDatDAO(this);

        Long daonhthu =0L;
        for(DonDatDTO object :donDatDAO.LayDSDonDat()){
            daonhthu+=Long.valueOf(object.getTongTien());
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###,###");
        tvdaonhthunam.setText(formatter.format(daonhthu)+" VNĐ");

        // Xử lý khi nút btnTuNgay2 được nhấn
        btnTuNgay.setOnClickListener(v -> showDatePickerDialog(edtTuNgay));

        // Xử lý khi nút btnTuNgay2 được nhấn
        btnDenNgay.setOnClickListener(v -> showDatePickerDialog(edtDenNgay));


        // Xử lý sự kiện khi nút "Doanh thu" được nhấn
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày bắt đầu và kết thúc từ EditText
                String ngayBatDau = edtTuNgay.getText().toString();
                String ngayKetThuc = edtDenNgay.getText().toString();

                // Kiểm tra xem ngày bắt đầu và kết thúc có rỗng hay không
                if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
                    Toast.makeText(StaticThongKe.this, "Vui lòng nhập đầy đủ ngày bắt đầu và kết thúc", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một đối tượng DonDatDAO
                DonDatDAO donDatDAO = new DonDatDAO(StaticThongKe.this);

                // Mở kết nối với cơ sở dữ liệu
                donDatDAO.open();

                // Gọi phương thức TinhTongDoanhThu từ DonDatDAO và lưu kết quả
                double tongDoanhThu = donDatDAO.TinhTongDoanhThu(ngayBatDau, ngayKetThuc);

                // Đóng kết nối với cơ sở dữ liệu
                donDatDAO.close();

                // Định dạng số tiền với dấu chấm cách 3 số
                android.icu.text.DecimalFormat df = new android.icu.text.DecimalFormat("#,###.## VND");
                String formattedTongDoanhThu = df.format(tongDoanhThu);

                // Hiển thị tổng doanh thu với dấu chấm cách 3 số và VND phía sau lên TextView
                tvDoanhThu.setText(formattedTongDoanhThu);
            }
        });

        List<Integer> years =new ArrayList<>();
        List<Integer> month =new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int currnetYear = calendar.get(Calendar.YEAR);
        // hiển thị năm
        for(int i=0;i<5;i++){
            years.add(currnetYear);
            currnetYear=currnetYear-1;
        }
        // hiển thị tháng
        for(int i=1;i<13;i++){
            month.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_spinner_item, years);

        ArrayAdapter<Integer> adaptermonth = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_spinner_item, month);

        // Layout for All ROWs of Spinner.  (Optional for ArrayAdapter).
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptermonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerYear.setAdapter(adapter);
        sprinerMonth.setAdapter(adaptermonth);

        this.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    onItemSelectedHandler(parent, view, position, id);
                    setDataweek(1);
                    Toast.makeText(getApplicationContext(),"Chạm vào biểu đồ để thay đổi dữ liệu",Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                sprinerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        onItemSelectedHandlerMonth(parent, view, position, id);
                        Toast.makeText(getApplicationContext(),"Chạm vào biểu đồ để thay đổi dữ liệu",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    // Phần mới
    private void showDatePickerDialog(EditText editText) {
        // Lấy ngày, tháng, năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(StaticThongKe.this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    // Đặt ngày đã chọn vào EditText
                    String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth1, monthOfYear + 1, year1);
                    editText.setText(selectedDate);
                }, year, month, dayOfMonth);

        // Giới hạn DatePickerDialog không vượt quá ngày hiện tại
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
    private void onItemSelectedHandlerMonth(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        Integer month = (Integer) adapter.getItem(position);
        try {
            setDataweek(month);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
// Hiển thị thanh ngang tuần trong tháng
    private void setDataweek(Integer month) throws ParseException {
        ArrayList<BarEntry> monthChart =new ArrayList<>();
        List<DonDatDTO> filterDtaaW = filterMonth(month,listdondat);
        Log.d("AAA",filterDtaaW.toString());
        List<ThongkeThang> listthongke = new ArrayList<>();
        for(int i=1;i<=5;i++){
            ThongkeThang thongkeThang = new ThongkeThang();
            thongkeThang.setThang(i);
            Long price = 0L;
            for (DonDatDTO object: filterDtaaW) {
                if(getWEEK(object.getNgayDat())==i) {
                    price += Long.valueOf(object.getTongTien());
                }
            }
            thongkeThang.setPrice(price);
            listthongke.add(thongkeThang);
        }
        for(ThongkeThang object : listthongke){
            Log.d("ARR",object.toString());
            monthChart.add(new BarEntry((float)object.getThang(), (float) (object.getPrice() / 1000)));
        }
        BarDataSet set1;
        set1 = new BarDataSet(monthChart,"ĐƠN VỊ 1000 VND");
        set1.setValueTextSize(14);
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(set1);
        barData.setValueTextSize(14);
        XAxis xl = chartWeekMonth.getXAxis();
        chartWeekMonth.setBackgroundColor(Color.parseColor("#ffffcc"));
        xl.setTextSize(14);

        chartWeekMonth.setData(barData);
    }
    // Hiển thị thanh ngang năm
    private int getWEEK(String thoiGianKT) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(convertStringToDate(thoiGianKT));
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    private List<DonDatDTO> filterMonth(Integer month, List<DonDatDTO> filtedata) {
        List<DonDatDTO> dataNew = new ArrayList<>();
        for (DonDatDTO object: filtedata) {
            if(getMonth(object.getNgayDat()) == month) {
                Log.d("SSS",object.toString());
                dataNew.add(object);
            }
        }
        return dataNew;
    }
    private void setData(int year) throws ParseException {
        ArrayList<BarEntry> monthChart =new ArrayList<>();
        listdondat = filterYear(year,donDatDAO.LayDSDonDat());
        Log.d("AAA",donDatDAO.LayDSDonDat().toString());
        List<ThongkeThang> listthongke = new ArrayList<>();
        for(int i=1;i<13;i++){
            ThongkeThang thongkeThang = new ThongkeThang();
            thongkeThang.setThang(i);
            Long price = 0L;
            for (DonDatDTO object: listdondat) {
                if(getMonth(object.getNgayDat())==i) {
                    price += Long.valueOf( object.getTongTien());
                }
            }
            thongkeThang.setPrice(price);
            listthongke.add(thongkeThang);
        }

        for(ThongkeThang object : listthongke){
            Log.d("ARR",object.toString());
            monthChart.add(new BarEntry((float)object.getThang(), (float) (object.getPrice() / 1000)));
        }

        BarDataSet set1;
        set1 = new BarDataSet(monthChart,"ĐƠN VỊ 1000 VND");
        set1.setValueTextSize(14);
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(set1);
        barData.setValueTextSize(14);
        XAxis xl = chartYear.getXAxis();
        xl.setTextSize(14);

        chartYear.setData(barData);

    }

    private void onItemSelectedHandler(AdapterView<?> adapterView, View view, int position, long id) throws ParseException {
        Adapter adapter = adapterView.getAdapter();
        Integer year = (Integer) adapter.getItem(position);
        Toast.makeText(this,year+"",Toast.LENGTH_SHORT).show();
        setData(year);
    }

    private int getMonth(String thoiGianKT) {
        Date date = null;
        try {
            date = convertStringToDate(thoiGianKT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getMonth()+1;
    }

    private List<DonDatDTO> filterYear(Integer year, List<DonDatDTO> all) throws ParseException {
        List<DonDatDTO> dataNew = new ArrayList<>();
        for (DonDatDTO object : all) {
            if (getYear(object.getNgayDat()) == year) {
                dataNew.add(object);
            }
        }
        return dataNew;
    }

    private int getYear(String thoiGianKT) throws ParseException {
        Date date = null;
        date = convertStringToDate(thoiGianKT);
        return date.getYear()+1900;
    }

    private Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return dateFormat.parse(dateString);
    }

    private class ThongkeThang {
        int thang;
        Long price;

        public ThongkeThang() {
        }
        public ThongkeThang(int thang, Long price) {
            this.thang = thang;
            this.price = price;
        }

        @Override
        public String toString() {
            return "ThongkeThang{" +
                    "thang=" + thang +
                    ", price=" + price +
                    '}';
        }

        public int getThang() {
            return thang;
        }

        public void setThang(int thang) {
            this.thang = thang;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }
    }

}