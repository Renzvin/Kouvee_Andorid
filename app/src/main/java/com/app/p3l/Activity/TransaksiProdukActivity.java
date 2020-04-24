package com.app.p3l.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.p3l.Adapter.SectionSupplierPagerAdapter;
import com.app.p3l.Adapter.SectionTransaksiProdukPagerAdapter;
import com.app.p3l.R;

public class TransaksiProdukActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        SectionTransaksiProdukPagerAdapter sectionsPagerAdapter = new SectionTransaksiProdukPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
    }
}
