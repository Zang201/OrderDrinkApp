package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.DTO.MonDTO;
import com.sinhvien.orderdrinkapp.R;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {

    Context context;
    int layout;
    List<MonDTO> monDTOList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<MonDTO> monDTOList){
        this.context = context;
        this.layout = layout;
        this.monDTOList = monDTOList;
    }

    @Override
    public int getCount() {
        return monDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monDTOList.get(position).getMaMon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhMon = (ImageView)view.findViewById(R.id.img_custommenu_HinhMon);
            viewholder.txt_custommenu_TenMon = (TextView) view.findViewById(R.id.txt_custommenu_TenMon);
            viewholder.img_custommenu_TinhTrang = (ImageView)view.findViewById(R.id.txt_custommenu_TinhTrang);


            viewholder.txt_custommenu_GiaTien = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        MonDTO monDTO = monDTOList.get(position);
        viewholder.txt_custommenu_TenMon.setText(monDTO.getTenMon());
        viewholder.txt_custommenu_GiaTien.setText(monDTO.getGiaTien()+" VNĐ");

        //hiển thị tình trạng của món
        if(monDTO.getTinhTrang().equals("true")){
            viewholder.img_custommenu_TinhTrang.setImageResource(R.drawable.baseline_check_circle_24);
        } else {
            viewholder.img_custommenu_TinhTrang.setImageResource(R.drawable.baseline_cancel_24);
        }

        //lấy hình ảnh
        if(monDTO.getHinhAnh() != null){
            byte[] menuimage = monDTO.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_HinhMon.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhMon.setImageResource(R.drawable.cafe_americano);
        }

        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder{
        ImageView img_custommenu_HinhMon,img_custommenu_TinhTrang;
        TextView txt_custommenu_TenMon, txt_custommenu_GiaTien;


    }
}
