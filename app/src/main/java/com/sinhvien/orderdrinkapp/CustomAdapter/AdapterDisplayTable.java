package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.Activities.ChuyenBanActivity;
import com.sinhvien.orderdrinkapp.Activities.HomeActivity;
import com.sinhvien.orderdrinkapp.Activities.PaymentActivity;
import com.sinhvien.orderdrinkapp.DAO.BanAnDAO;
import com.sinhvien.orderdrinkapp.DAO.DonDatDAO;
import com.sinhvien.orderdrinkapp.DTO.BanAnDTO;
import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
import com.sinhvien.orderdrinkapp.Fragments.DisplayCategoryFragment;
import com.sinhvien.orderdrinkapp.Fragments.DisplayMenuFragment;
import com.sinhvien.orderdrinkapp.Fragments.DisplayTableFragment;
import com.sinhvien.orderdrinkapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<BanAnDTO> banAnDTOList;
    ViewHolder viewHolder;
    BanAnDAO banAnDAO;
    DonDatDAO donDatDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList = banAnDTOList;
        banAnDAO = new BanAnDAO(context);
        donDatDAO = new DonDatDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();

    }
    private AlertDialog dialog;
    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);
            viewHolder.imgDoiBan = (ImageView) view.findViewById(R.id.img_customtable_DoiBan);
            viewHolder.imgBanAn = (ImageView) view.findViewById(R.id.img_customtable_BanAn);
            viewHolder.imgGoiMon = (ImageView) view.findViewById(R.id.img_customtable_GoiMon);
            viewHolder.imgThanhToan = (ImageView) view.findViewById(R.id.img_customtable_ThanhToan);
            viewHolder.imgAnNut = (ImageView) view.findViewById(R.id.img_customtable_AnNut);
            viewHolder.txtTenBanAn = (TextView)view.findViewById(R.id.txt_customtable_TenBanAn);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(banAnDTOList.get(position).isDuocChon()){
            HienThiButton();
        }else {
            AnButton();
        }

        BanAnDTO banAnDTO = banAnDTOList.get(position);

        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        //đổi hình theo tình trạng
        if(kttinhtrang.equals("true")){
                viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_table_bar_25);
        }else {
            viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_table_bar_24);
        }


        viewHolder.txtTenBanAn.setText(banAnDTO.getTenBan());
        viewHolder.imgBanAn.setTag(position);

        //sự kiện click
        viewHolder.imgDoiBan.setOnClickListener(this);
        viewHolder.imgBanAn.setOnClickListener(this);
        viewHolder.imgGoiMon.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);
        viewHolder.imgAnNut.setOnClickListener(this);

        return view;
    }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

            int vitri1 = (int) viewHolder.imgBanAn.getTag();

            int maban = banAnDTOList.get(vitri1).getMaBan();
            String tenban = banAnDTOList.get(vitri1).getTenBan();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String ngaydat= dateFormat.format(calendar.getTime());

            switch (id) {
                case R.id.img_customtable_BanAn:
                    int vitri = (int) v.getTag();
                    banAnDTOList.get(vitri).setDuocChon(true);
                    HienThiButton();
                    break;

                case R.id.img_customtable_AnNut:
                    AnButton();
                    break;

                case R.id.img_customtable_GoiMon:
                    Intent getIHome = ((HomeActivity) context).getIntent();
                    int manv = getIHome.getIntExtra("manv", 0);
                    String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                    if (tinhtrang.equals("false")) {
                        //Thêm bảng gọi món và update tình trạng bàn
                        DonDatDTO donDatDTO = new DonDatDTO();
                        donDatDTO.setMaBan(maban);
                        donDatDTO.setMaNV(manv);
                        donDatDTO.setNgayDat(ngaydat);
                        donDatDTO.setTinhTrang("false");
                        donDatDTO.setTongTien("0");

                        long ktra = donDatDAO.ThemDonDat(donDatDTO);
                        banAnDAO.CapNhatTinhTrangBan(maban, "true");
                        if (ktra == 0) {
                            Toast.makeText(context, context.getResources().getString(R.string.add_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                    //chuyển qua trang category
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                    Bundle bDataCategory = new Bundle();
                    bDataCategory.putInt("maban", maban);
                    displayCategoryFragment.setArguments(bDataCategory);

                    transaction.replace(R.id.contentView, displayCategoryFragment).addToBackStack("hienthibanan");
                    transaction.commit();
                    break;
                case R.id.img_customtable_ThanhToan:
                    //chuyển dữ liệu qua trang thanh toán
                    Intent iThanhToan = new Intent(context, PaymentActivity.class);
                    iThanhToan.putExtra("maban", maban);
                    iThanhToan.putExtra("tenban", tenban);
                    iThanhToan.putExtra("ngaydat", ngaydat);
                    context.startActivity(iThanhToan);
                    break;

    // chưa đổi được trạng thái bàn từ xanh sang đỏ
                case R.id.img_customtable_DoiBan:
                    final int maBanHienTai = banAnDTOList.get(vitri1).getMaBan();
                    // Xây dựng dialog để chọn bàn mới
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Nhập tên bàn mới");
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);
                    builder.setCancelable(false);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tenBanMoi = input.getText().toString().trim();

                            // Kiểm tra xem tên bàn mới có hợp lệ không
                            if (tenBanMoi.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập tên bàn mới", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Lấy mã bàn mới từ tên bàn mới
                            int maBanMoi = banAnDAO.LayMaBanTheoTen(tenBanMoi);

                            if (maBanMoi != -1) {
                                // Lấy danh sách các món ăn của bàn hiện tại
                                List<DonDatDTO> danhSachMonAn = donDatDAO.LayDanhSachMonAnTheoMaBan(maBanHienTai);

                                // Cập nhật thông tin bàn cho các món ăn
                                for (DonDatDTO monAn : danhSachMonAn) {
                                    monAn.setMaBan(maBanMoi);
                                    // Cập nhật món ăn vào cơ sở dữ liệu
                                    donDatDAO.CapNhatMonAn(monAn);
                                }

                                // Cập nhật thông tin bàn trong cơ sở dữ liệu
                                banAnDAO.DoiBan(maBanHienTai, maBanMoi);

                                // Thông báo cho người dùng biết rằng việc đổi bàn đã thành công
                                Toast.makeText(context, "Đổi bàn thành công", Toast.LENGTH_SHORT).show();

                                // Cập nhật trạng thái và hình ảnh của bàn hiện tại và bàn mới
                                for (int i = 0; i < banAnDTOList.size(); i++) {
                                    if (banAnDTOList.get(i).getMaBan() == maBanHienTai) {
                                        banAnDTOList.get(i).setDuocChon(false); // Bàn hiện tại không được chọn nữa
                                        viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_table_bar_24); // Đổi hình của bàn hiện tại
                                    } else if (banAnDTOList.get(i).getMaBan() == maBanMoi) {
                                        banAnDTOList.get(i).setDuocChon(true); // Bàn mới được chọn
                                        viewHolder.imgBanAn.setImageResource(R.drawable.ic_baseline_table_bar_25); // Đổi hình của bàn mới
                                    }
                                }
                                // Cập nhật giao diện của các item trong ListView
                                notifyDataSetChanged(); // Yêu cầu ListView cập nhật lại giao diện

                                // Gọi sự kiện lắng nghe sự thay đổi bàn
                                if (onBanChangeListener != null) {
                                    onBanChangeListener.onBanChange(maBanHienTai, tenBanMoi);
                                }
                            } else {
                                Toast.makeText(context, "Bàn mới không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                    break;
            }
            }
    private void HienThiButton(){
        viewHolder.imgDoiBan.setVisibility(View.VISIBLE);
        viewHolder.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolder.imgAnNut.setVisibility(View.VISIBLE);
        if(HomeActivity.maquyen ==3){
            viewHolder.imgGoiMon.setVisibility(View.GONE);
//            viewHolder.imgThanhToan.setVisibility(View.GONE);

        }
    }
    private void AnButton(){
        viewHolder.imgDoiBan.setVisibility(View.INVISIBLE);
        viewHolder.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolder.imgAnNut.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnNut, imgDoiBan;
        TextView txtTenBanAn;
    }
    public interface OnBanChangeListener {
        void onBanChange(int maBanHienTai, String tenBanMoi);
    }
    private OnBanChangeListener onBanChangeListener;

    public void setOnBanChangeListener(OnBanChangeListener listener) {
        this.onBanChangeListener = listener;
    }
}