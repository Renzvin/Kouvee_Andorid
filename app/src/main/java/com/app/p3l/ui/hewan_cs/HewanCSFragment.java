package com.app.p3l.ui.hewan_cs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.p3l.Adapter.SectionHewanPagerAdapter;
import com.app.p3l.R;
import com.google.android.material.tabs.TabLayout;

public class HewanCSFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_cs_hewan, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SectionHewanPagerAdapter sectionsPagerAdapter = new SectionHewanPagerAdapter(getActivity().getApplicationContext(),getFragmentManager());
        ViewPager viewPager = getView().findViewById(R.id.hew_view_cs_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = getView().findViewById(R.id.hew_cs_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
