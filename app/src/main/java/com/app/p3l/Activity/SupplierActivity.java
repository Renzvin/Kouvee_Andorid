package com.app.p3l.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.p3l.Adapter.SectionProdukPagerAdapter;
import com.app.p3l.Adapter.SectionSupplierPagerAdapter;
import com.app.p3l.R;
import com.google.android.material.tabs.TabLayout;

public class SupplierActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        SectionSupplierPagerAdapter sectionsPagerAdapter = new SectionSupplierPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.sup_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.sup_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
