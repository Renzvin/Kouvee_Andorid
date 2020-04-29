package com.app.p3l.ui.transaksi_cs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.p3l.Adapter.SectionHewanPagerAdapter;
import com.app.p3l.Adapter.SectionTransaksiCSPagerAdapter;
import com.app.p3l.R;
import com.google.android.material.tabs.TabLayout;

public class cs_transaksi extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_cs_transaksi, container, false);
        SectionTransaksiCSPagerAdapter sectionsPagerAdapter = new SectionTransaksiCSPagerAdapter(getActivity().getApplicationContext(), getActivity().getSupportFragmentManager());
        ViewPager viewPager = v.findViewById(R.id.transaksi_view_cs_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = v.findViewById(R.id.transaksi_cs_tabs);
        tabs.setupWithViewPager(viewPager);
        return v;
    }
}
