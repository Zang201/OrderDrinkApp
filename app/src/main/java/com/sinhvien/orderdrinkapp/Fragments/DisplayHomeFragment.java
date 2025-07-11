package com.sinhvien.orderdrinkapp.Fragments;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.sinhvien.orderdrinkapp.Activities.AddCategoryActivity;
import com.sinhvien.orderdrinkapp.Activities.HomeActivity;
import com.sinhvien.orderdrinkapp.CustomAdapter.AdapterDisplayCategory;
import com.sinhvien.orderdrinkapp.CustomAdapter.AdapterRecycleViewCategory;
import com.sinhvien.orderdrinkapp.CustomAdapter.AdapterRecycleViewStatistic;
import com.sinhvien.orderdrinkapp.DAO.DonDatDAO;
import com.sinhvien.orderdrinkapp.DAO.LoaiMonDAO;
import com.sinhvien.orderdrinkapp.DTO.DonDatDTO;
import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DisplayHomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView rcv_displayhome_LoaiMon, rcv_displayhome_DonTrongNgay,  rcv_displayhome_LoaiMon1;
    RelativeLayout layout_displayhome_ThongKe,layout_displayhome_XemBan, layout_displayhome_XemMenu, layout_displayhome_XemNV;
    TextView txt_displayhome_ViewAllCategory, txt_displayhome_ViewAllStatistic;
    LoaiMonDAO loaiMonDAO;
    DonDatDAO donDatDAO;
    List<LoaiMonDTO> loaiMonDTOList;
    List<DonDatDTO> donDatDTOS;
    AdapterRecycleViewCategory adapterRecycleViewCategory;
    AdapterRecycleViewStatistic adapterRecycleViewStatistic;
    LinearLayout thongke,quanly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displayhome_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Trang chủ");
        setHasOptionsMenu(true);
        thongke = view.findViewById(R.id.linearThongke);
        quanly = view.findViewById(R.id.linearQuanlY);
        //region Lấy dối tượng view
        rcv_displayhome_LoaiMon = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon);
        rcv_displayhome_LoaiMon1 = (RecyclerView)view.findViewById(R.id.rcv_displayhome_LoaiMon1);
//        rcv_displayhome_DonTrongNgay = (RecyclerView)view.findViewById(R.id.rcv_displayhome_DonTrongNgay);
//        layout_displayhome_ThongKe = (RelativeLayout)view.findViewById(R.id.layout_displayhome_ThongKe);
        layout_displayhome_XemBan = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemBan);
        layout_displayhome_XemMenu = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemMenu);
        layout_displayhome_XemNV = (RelativeLayout)view.findViewById(R.id.layout_displayhome_XemNV);
        txt_displayhome_ViewAllCategory = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllCategory);
//        txt_displayhome_ViewAllStatistic = (TextView) view.findViewById(R.id.txt_displayhome_ViewAllStatistic);
        //endregion
        if(HomeActivity.maquyen !=1){
            thongke.setVisibility(View.GONE);
            quanly.setVisibility(View.GONE);
            layout_displayhome_XemMenu.setVisibility(View.GONE);
        }
        //khởi tạo kết nối
        loaiMonDAO = new LoaiMonDAO(getActivity());
        donDatDAO = new DonDatDAO(getActivity());

        HienThiDSLoai();
        HienThiDSLoai1();
//        HienThiDonTrongNgay();

//        layout_displayhome_ThongKe.setOnClickListener(this);
        layout_displayhome_XemBan.setOnClickListener(this);
        layout_displayhome_XemMenu.setOnClickListener(this);
        layout_displayhome_XemNV.setOnClickListener(this);
        txt_displayhome_ViewAllCategory.setOnClickListener(this);
//        txt_displayhome_ViewAllStatistic.setOnClickListener(this);

        return view;
    }

    private void HienThiDSLoai(){
        rcv_displayhome_LoaiMon.setHasFixedSize(true);
        rcv_displayhome_LoaiMon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        loaiMonDTOList = loaiMonDAO.LayDSLoaiMon();
        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,loaiMonDTOList);
        rcv_displayhome_LoaiMon.setAdapter(adapterRecycleViewCategory);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }
    private void HienThiDSLoai1(){
        rcv_displayhome_LoaiMon1.setHasFixedSize(true);
        rcv_displayhome_LoaiMon1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        loaiMonDTOList = loaiMonDAO.LayDSLoaiMon();
        adapterRecycleViewCategory = new AdapterRecycleViewCategory(getActivity(),R.layout.custom_layout_displaycategory,loaiMonDTOList);
        rcv_displayhome_LoaiMon1.setAdapter(adapterRecycleViewCategory);
        adapterRecycleViewCategory.notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        NavigationView navigationView = (NavigationView)getActivity().findViewById(R.id.navigation_view_trangchu);
        switch (id){
//            case R.id.layout_displayhome_ThongKe:

//            case R.id.txt_displayhome_ViewAllStatistic:
//                FragmentTransaction tranDisplayStatistic = getActivity().getSupportFragmentManager().beginTransaction();
//                tranDisplayStatistic.replace(R.id.contentView,new DisplayStatisticFragment());
//                tranDisplayStatistic.addToBackStack(null);
//                tranDisplayStatistic.commit();
//                navigationView.setCheckedItem(R.id.nav_statistic);
//
//                break;

            case R.id.layout_displayhome_XemBan:
                FragmentTransaction tranDisplayTable = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayTable.replace(R.id.contentView,new DisplayTableFragment());
                tranDisplayTable.addToBackStack(null);
                tranDisplayTable.commit();
                navigationView.setCheckedItem(R.id.nav_table);
                break;
            case R.id.layout_displayhome_XemMenu:
                FragmentTransaction tranDisplayCategory = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory.addToBackStack(null);
                tranDisplayCategory.commit();
                navigationView.setCheckedItem(R.id.nav_category);
                break;
            case R.id.layout_displayhome_XemNV:
                FragmentTransaction tranDisplayStaff= getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayStaff.replace(R.id.contentView,new DisplayStaffFragment());
                tranDisplayStaff.addToBackStack(null);
                tranDisplayStaff.commit();
                navigationView.setCheckedItem(R.id.nav_staff);
                break;

            case R.id.txt_displayhome_ViewAllCategory:
                FragmentTransaction tranDisplayCategory1 = getActivity().getSupportFragmentManager().beginTransaction();
                tranDisplayCategory1.replace(R.id.contentView,new DisplayCategoryFragment());
                tranDisplayCategory1.addToBackStack(null);
                tranDisplayCategory1.commit();
                navigationView.setCheckedItem(R.id.nav_category);
                break;

        }
    }
}

