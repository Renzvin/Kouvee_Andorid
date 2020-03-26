package com.app.p3l.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.p3l.Adapter.SectionHewanPagerAdapter;
import com.app.p3l.R;
import com.google.android.material.tabs.TabLayout;

public class HewanActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hewan);
        SectionHewanPagerAdapter sectionsPagerAdapter = new SectionHewanPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.hew_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.hew_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
