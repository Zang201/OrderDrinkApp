package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.Activities.DetailStatisticActivity;
import com.sinhvien.orderdrinkapp.DAO.BanAnDAO;
import com.sinhvien.orderdrinkapp.DAO.DonDatDAO;
import com.sinhvien.orderdrinkapp.DAO.NhanVienDAO;
import com.sinhvien.orderdrinkapp.DTO.BanAnDTO;
import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
import com.sinhvien.orderdrinkapp.DTO.NhanVienDTO;
import com.sinhvien.orderdrinkapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<DonDatDTO> donDatDTOS;
    ViewHolder viewHolder;
    NhanVienDAO nhanVienDAO;
    BanAnDAO banAnDAO;
    DonDatDAO donDatDAO;
    IonclickItem ionclickItem;

    public AdapterDisplayStatistic(Context context, int layout, List<DonDatDTO> donDatDTOS,IonclickItem ionclickItem){
        this.context = context;
        this.layout = layout;
        this.donDatDTOS = donDatDTOS;
        nhanVienDAO = new NhanVienDAO(context);
        banAnDAO = new BanAnDAO(context);
        this.ionclickItem = ionclickItem;
    }

    @Override
    public int getCount() {
        return donDatDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatDTOS.get(position).getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);
            viewHolder.btn_xoa_ThanhToan=(ImageView)view.findViewById(R.id.btn_xoa_ThanhToan);
            viewHolder.txt_customstatistic_MaDon = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenNV = (TextView)view.findViewById(R.id.txt_customstatistic_TenNV);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_BanDat = (TextView)view.findViewById(R.id.txt_customstatistic_BanDat);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDatDTO donDatDTO = donDatDTOS.get(position);
        viewHolder.txt_customstatistic_TrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ionclickItem.onClick(donDatDTO);
            }
        });
        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDatDTO.getTongTien()+" VNĐ");
        if (donDatDTO.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
            viewHolder.txt_customstatistic_TrangThai.setTextColor(Color.parseColor("#FF0000"));
        }
        NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donDatDTO.getMaNV());
        viewHolder.txt_customstatistic_TenNV.setText(nhanVienDTO.getHOTENNV());
        viewHolder.txt_customstatistic_BanDat.setText(banAnDAO.LayTenBanTheoMa(donDatDTO.getMaBan()));
        viewHolder.btn_xoa_ThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xác định đơn hàng cần xóa
                        DonDatDTO donDatDTOToDelete = donDatDTOS.get(position);

                        // Thực hiện hành động xóa ở đây
                        // Ví dụ: xóa đơn hàng khỏi cơ sở dữ liệu
                         DonDatDAO donDatDAO = new DonDatDAO(context);
                         donDatDAO.xoaDonDat(donDatDTOToDelete);

                        // Sau khi xóa thành công, cập nhật lại danh sách và cập nhật giao diện
                        donDatDTOS.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });



        return view;

        //Thiết lập nút xóa Thanh Toán


    }
    public class ViewHolder{
        TextView  txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;
        ImageView btn_xoa_ThanhToan;

    }
}
