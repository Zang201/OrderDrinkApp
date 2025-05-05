package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.DTO.TimeDTO;
import com.sinhvien.orderdrinkapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeAdapter extends BaseAdapter {

    private Context mContext;
    private List<TimeDTO> mTimeList;

    public TimeAdapter(Context context, List<TimeDTO> timeList) {
        this.mContext = context;
        this.mTimeList = timeList;
    }

    @Override
    public int getCount() {
        return mTimeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTimeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_layout_time, parent, false);
            viewHolder = new ViewHolder();
//            viewHolder.txtTenNV = convertView.findViewById(R.id.txt_customTT_TenNV);
            viewHolder.txtNgay = convertView.findViewById(R.id.txt_customTT_Ngay);
            viewHolder.txtLogin = convertView.findViewById(R.id.txt_customTT_Login);
            viewHolder.txtLogout = convertView.findViewById(R.id.txt_customTT_Logout);
            viewHolder.txtSum = convertView.findViewById(R.id.txt_customTT_Sum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TimeDTO timeDTO = mTimeList.get(position);

        // Hiển thị thông tin
//        viewHolder.txtTenNV.setText(timeDTO.getTenNhanVien());
        viewHolder.txtNgay.setText("Ngày: " + timeDTO.getNgay());
        viewHolder.txtLogin.setText("Đăng nhập lúc: " + timeDTO.getThoiGianDangNhap());
        viewHolder.txtLogout.setText("Đăng xuất lúc: " + timeDTO.getThoiGianLogout());
        viewHolder.txtSum.setText("Tổng thời gian: " + calculateTimeDifference(timeDTO.getThoiGianDangNhap(), timeDTO.getThoiGianLogout()));

        return convertView;
    }

    private String calculateTimeDifference(String loginTime, String logoutTime) {
        // Tính tổng thời gian
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        try {
            Date login = format.parse(loginTime);
            Date logout = format.parse(logoutTime);
            long diff = logout.getTime() - login.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", diffHours, diffMinutes, diffSeconds);
        } catch (ParseException e) {
            e.printStackTrace();
            return "00:00:00";
        }
    }

    static class ViewHolder {
        TextView txtTenNV, txtNgay, txtLogin, txtLogout, txtSum;
    }
}








//public class AdapterTime extends BaseAdapter {
//    private Context context;
//    private List<TimeDTO> timeList;
//
//    public AdapterTime(Context context, List<TimeDTO> timeList) {
//        this.context = context;
//        this.timeList = timeList;
//    }
//
//    @Override
//    public int getCount() {
//        return timeList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return timeList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.custom_layout_time, parent, false);
//            viewHolder = new ViewHolder();
////            viewHolder.txtTenNV = convertView.findViewById(R.id.txt_customTT_TenNV);
//            viewHolder.txtNgay = convertView.findViewById(R.id.txt_customTT_Ngay);
//            viewHolder.txtLogin = convertView.findViewById(R.id.txt_customTT_Login);
//            viewHolder.txtLogout = convertView.findViewById(R.id.txt_customTT_Logout);
//            viewHolder.txtSum = convertView.findViewById(R.id.txt_customTT_Sum);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        TimeDTO timeDTO = timeList.get(position);
//
////        viewHolder.txtTenNV.setText(timeDTO.getTenNhanVien());
//        viewHolder.txtNgay.setText("Ngày: " + timeDTO.getNgay());
//        viewHolder.txtLogin.setText("Đăng nhập lúc: " + timeDTO.getThoiGianDangNhap());
//        viewHolder.txtLogout.setText("Đăng xuất lúc: " + timeDTO.getThoiGianLogout());
//        viewHolder.txtSum.setText("Giờ làm trong ngày: " + timeDTO.getGioLamTrongNgay());
//
//        return convertView;
//    }
//
//    static class ViewHolder {
//        TextView txtTenNV, txtNgay, txtLogin, txtLogout, txtSum;
//    }
//}
