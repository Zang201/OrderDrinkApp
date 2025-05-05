package com.sinhvien.orderdrinkapp.CustomAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.sinhvien.orderdrinkapp.Activities.HomeActivity;
import com.sinhvien.orderdrinkapp.DAO.LuongDAO;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.DTO.LuongDTO;
import com.sinhvien.orderdrinkapp.DTO.NhanVienDTO;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class AdapterLuong extends BaseAdapter {
    Context context;
    LuongDAO luongDAO;
    int layout;
    List<LuongDTO> luongDTOList;
    AdapterLuong.ViewHolder viewHolder;
    NhanVienDAO nhanVienDAO;

    public AdapterLuong(Context context, int layout, List<LuongDTO> luongDTOList){
        this.context = context;
        this.layout = layout;
        this.luongDTOList = luongDTOList;
        nhanVienDAO = new NhanVienDAO(context);
    }

    @Override
    public int getCount() {
        return luongDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return luongDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return luongDTOList.get(position).getMaLuong();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){

            viewHolder = new AdapterLuong.ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.btn_xoa_Luong=(ImageView)view.findViewById(R.id.btn_xoa_Luong);
            viewHolder.tvLUONGMOTGIO=(TextView)view.findViewById(R.id.tvLUONGMOTGIO);
            viewHolder.tvNGAYTHANG = (TextView)view.findViewById(R.id.tvTHANGNAM);
            viewHolder.tvSOGIO = (TextView)view.findViewById(R.id.tvSOGIO);
            viewHolder.tvLUONGTHUONG = (TextView)view.findViewById(R.id.tvLUONGTHUONG);
            viewHolder.tvLUONGPHAT = (TextView)view.findViewById(R.id.tvLUONGPHAT);
            viewHolder.tvTONG = (TextView)view.findViewById(R.id.tvTONGLUONG);
            viewHolder.tvThongTin= view.findViewById(R.id.tongThogntin);

            view.setTag(viewHolder);
        } else {
            viewHolder = (AdapterLuong.ViewHolder) view.getTag();
        }
        LuongDTO luongDTO = luongDTOList.get(position);

//        viewHolder.btn_xoa_Luong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Xác nhận xóa");
//                builder.setMessage("Bạn có chắc chắn muốn xóa mục này?");
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Perform deletion here
//                        boolean isDeleted = luongDAO.xoaLuong(luongDTOList.get(position).getMaLuong());
//                        if (isDeleted) {
//                            luongDTOList.remove(position);
//                            notifyDataSetChanged();
//                            Toast.makeText(context, "Xóa lương thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Không thể xóa lương", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                builder.setNegativeButton("Hủy", null);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });

        viewHolder.btn_xoa_Luong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa mục này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform deletion here
                        luongDTOList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(HomeActivity.manv);
        if(nhanVienDTO.getMAQUYEN()==1){
            NhanVienDTO nhanvien = nhanVienDAO.LayNVTheoMa(luongDTO.getMaNhanvien());
            String quyen = nhanvien.getMAQUYEN() ==2?"Chạy bàn":"Phục vụ";
            viewHolder.tvThongTin.setText(nhanvien.getHOTENNV()+"---"+quyen);
        }else{
            viewHolder.tvThongTin.setVisibility(View.GONE);
        }
        int Tong = luongDTO.getSoGio()*luongDTO.getMucLuong()+luongDTO.getLuongThuong()-luongDTO.getLuongPhat();
        viewHolder.tvLUONGMOTGIO.setText("Mức lương : "+luongDTO.getMucLuong());
        viewHolder.tvNGAYTHANG.setText("Lương tháng : "+luongDTO.getNgayThang());
        viewHolder.tvSOGIO.setText("Số giờ : "+luongDTO.getSoGio()+" X "+luongDTO.getMucLuong()+" = "+luongDTO.getSoGio()*luongDTO.getMucLuong());
        viewHolder.tvLUONGTHUONG.setText("Lương thưởng :    "+luongDTO.getLuongThuong());
        viewHolder.tvLUONGPHAT.setText("Lương phạt :    "+luongDTO.getLuongPhat());
        viewHolder.tvTONG.setText("TỔNG :    "+Tong);
        return view;
    }

    public class ViewHolder{
        TextView tvLUONGMOTGIO, tvNGAYTHANG,tvSOGIO,tvLUONGTHUONG,tvLUONGPHAT,tvTONG,tvThongTin;
        ImageView btn_xoa_Luong;
    }
}



//public class AdapterLuong  extends BaseAdapter {
//    Context context;
//    LuongDAO luongDAO;
//    int layout;
//    List<LuongDTO> luongDTOList;
//    AdapterLuong.ViewHolder viewHolder;
//    NhanVienDAO nhanVienDAO;
//    Adapter adapter;
//
//    public AdapterLuong(Context context, int layout, List<LuongDTO> luongDTOList){
//        this.adapter = adapter;
//        this.context = context;
//        this.layout = layout;
//        this.luongDTOList = luongDTOList;
//        nhanVienDAO = new NhanVienDAO(context);
//    }
//
//    @Override
//    public int getCount() {
//        return luongDTOList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return luongDTOList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return luongDTOList.get(position).getMaLuong();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if(view == null){
//
//            viewHolder = new AdapterLuong.ViewHolder();
//            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(layout,parent,false);
//
//            viewHolder.btn_xoa_Luong=(ImageView)view.findViewById(R.id.btn_xoa_Luong);
//            viewHolder.tvLUONGMOTGIO=(TextView)view.findViewById(R.id.tvLUONGMOTGIO);
//            viewHolder.tvNGAYTHANG = (TextView)view.findViewById(R.id.tvTHANGNAM);
//            viewHolder.tvSOGIO = (TextView)view.findViewById(R.id.tvSOGIO);
//            viewHolder.tvLUONGTHUONG = (TextView)view.findViewById(R.id.tvLUONGTHUONG);
//            viewHolder.tvLUONGPHAT = (TextView)view.findViewById(R.id.tvLUONGPHAT);
//            viewHolder.tvTONG = (TextView)view.findViewById(R.id.tvTONGLUONG);
//            viewHolder.tvThongTin= view.findViewById(R.id.tongThogntin);
//
//            view.setTag(viewHolder);
//        }else {
//            viewHolder = (AdapterLuong.ViewHolder) view.getTag();
//        }
//        LuongDTO luongDTO = luongDTOList.get(position);
//
//// Xử lý sự kiện khi người dùng nhấn vào nút xóa
//        // Giả sử viewHolder.btn_xoa_Luong là Button được dùng để kích hoạt chức năng xóa
//
//
//        viewHolder.btn_xoa_Luong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Xác nhận xóa");
//                builder.setMessage("Bạn có chắc chắn muốn xóa mục này?");
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Xóa đối tượng luongDTO tương ứng với vị trí position
//                        luongDTOList.remove(position);
//                        // Cập nhật giao diện
//                        notifyDataSetChanged();
//                    }
//                });
//                builder.setNegativeButton("Hủy", null);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
//        // Chỉ xóa giao diện chưa xóa được csdl do còn lỗi tồn động trong quá trình xóa.
//        viewHolder.btn_xoa_Luong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Xác nhận xóa");
//                builder.setMessage("Bạn có chắc chắn muốn xóa mục này?");
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Xóa đối tượng luongDTO tương ứng với vị trí position
//                        luongDTOList.remove(position);
//                        // Cập nhật giao diện
//                        notifyDataSetChanged();
//                    }
//                });
//                builder.setNegativeButton("Hủy", null);
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
//
//
//        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(HomeActivity.manv);
//        if(nhanVienDTO.getMAQUYEN()==1){
//            NhanVienDTO nhanvien = nhanVienDAO.LayNVTheoMa(luongDTO.getMaNhanvien());
//            String quyen = nhanvien.getMAQUYEN() ==2?"Chạy bàn":"Phục vụ";
//            viewHolder.tvThongTin.setText(nhanvien.getHOTENNV()+"---"+quyen);
//        }else{
//            viewHolder.tvThongTin.setVisibility(View.GONE);
//        }
//        int Tong = luongDTO.getSoGio()*luongDTO.getMucLuong()+luongDTO.getLuongThuong()-luongDTO.getLuongPhat();
//        viewHolder.tvLUONGMOTGIO.setText("Mức lương : "+luongDTO.getMucLuong());
//        viewHolder.tvNGAYTHANG.setText("Lương tháng : "+luongDTO.getNgayThang());
//        viewHolder.tvSOGIO.setText("Số giờ : "+luongDTO.getSoGio()+" X "+luongDTO.getMucLuong()+" = "+luongDTO.getSoGio()*luongDTO.getMucLuong());
//        viewHolder.tvLUONGTHUONG.setText("Lương thưởng :    "+luongDTO.getLuongThuong());
//        viewHolder.tvLUONGPHAT.setText("Lương phạt :    "+luongDTO.getLuongPhat());
//        viewHolder.tvTONG.setText("TỔNG :    "+Tong);
//        return view;
//    }
//
//    public class ViewHolder{
//        TextView tvLUONGMOTGIO, tvNGAYTHANG,tvSOGIO,tvLUONGTHUONG,tvLUONGPHAT,tvTONG,tvThongTin;
//        ImageView btn_xoa_Luong;
//    }
//}
