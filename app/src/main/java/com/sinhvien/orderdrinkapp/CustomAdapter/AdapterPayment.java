package com.sinhvien.orderdrinkapp.CustomAdapter;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.orderdrinkapp.DTO.ThanhToanDTO;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPayment extends RecyclerView.Adapter<AdapterPayment.ViewHolder>{
    private  List<ThanhToanDTO> thanhToanDTOList;
    private final OnclickItem clickListener;
    private final Context mContext;
    SharedPreferences sharedPreferences;

    public AdapterPayment(OnclickItem clickListener, Context mContext) {
        this.clickListener = clickListener;
        this.mContext = mContext;
    }

    public void setDta(List<ThanhToanDTO> thanhToanDTOList){
        this.thanhToanDTOList = thanhToanDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View studentView =
                inflater.inflate(R.layout.custom_layout_paymentmenu, parent, false);
        return new ViewHolder(studentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(position);

        viewHolder.txt_custompayment_TenMon.setText(thanhToanDTO.getTenMon());
        viewHolder.txt_custompayment_SoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolder.txt_custompayment_GiaTien.setText(thanhToanDTO.getGiaTien() * thanhToanDTO.getSoLuong() + " đ");

        // Lấy mã quyền của nhân viên
        int maQuyen = getCurrentUserPermission();

        // Kiểm tra nếu mã quyền là 2 hoặc 3, ẩn hoặc vô hiệu hóa các nút back và next
        if (maQuyen == 2 || maQuyen == 3) {
            viewHolder.back.setVisibility(View.GONE); // Ẩn nút back
            viewHolder.next.setVisibility(View.GONE); // Ẩn nút next
        } else {
            viewHolder.back.setVisibility(View.VISIBLE);
            viewHolder.next.setVisibility(View.VISIBLE);

            // Thiết lập sự kiện click cho các nút back và next
            viewHolder.back.setOnClickListener(view -> {
                clickListener.OnClickBack(thanhToanDTO);
            });
            viewHolder.next.setOnClickListener(view -> {
                clickListener.OnClickNext(thanhToanDTO);
            });
        }

        // Thiết lập sự kiện long click cho mỗi mục
        viewHolder.itemView.setOnLongClickListener(view -> {
            clickListener.OnLongClick(thanhToanDTO);
            return true;
        });
    }

    private int getCurrentUserPermission() {
        // Lấy mã quyền từ SharedPreferences
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("maquyen", 0);
    }


    @Override
    public int getItemCount() {
        return thanhToanDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_custompayment_HinhMon;
        ImageButton back,next;
        TextView txt_custompayment_TenMon, txt_custompayment_SoLuong, txt_custompayment_GiaTien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_custompayment_HinhMon = (CircleImageView)itemView.findViewById(R.id.img_custompayment_HinhMon);
            txt_custompayment_TenMon = (TextView)itemView.findViewById(R.id.txt_custompayment_TenMon);
            txt_custompayment_SoLuong = (TextView)itemView.findViewById(R.id.txt_custompayment_SoLuong);
            txt_custompayment_GiaTien = (TextView)itemView.findViewById(R.id.txt_custompayment_GiaTien);
            back = (ImageButton)itemView.findViewById(R.id.btnback);
            next = (ImageButton)itemView.findViewById(R.id.btnnext);

        }
    }
}
