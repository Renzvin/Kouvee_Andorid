package com.app.p3l.ui.customer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.p3l.Adapter.HewanAdapter;
import com.app.p3l.Adapter.SectionCustomerPagerAdapter;
import com.app.p3l.Adapter.SectionHewanPagerAdapter;
import com.app.p3l.R;
import com.google.android.material.tabs.TabLayout;

public class CustomerFragment extends Fragment  {

    private RecyclerView customerRecycler;
    private SectionCustomerPagerAdapter hewanAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.view_pager, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SectionCustomerPagerAdapter sectionsPagerAdapter = new SectionCustomerPagerAdapter(getActivity().getApplicationContext(),getChildFragmentManager());
        ViewPager viewPager = getView().findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
    }
}

