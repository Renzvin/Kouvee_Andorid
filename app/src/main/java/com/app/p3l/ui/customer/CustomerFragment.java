package com.app.p3l.ui.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.app.p3l.Adapter.SectionCustomerPagerAdapter;
import com.app.p3l.Adapter.SectionHewanPagerAdapter;
import com.app.p3l.R;
import com.google.android.material.tabs.TabLayout;

public class CustomerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_customer, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SectionCustomerPagerAdapter sectionsPagerAdapter = new SectionCustomerPagerAdapter(getActivity().getApplicationContext(),getFragmentManager());
        ViewPager viewPager = getView().findViewById(R.id.customer_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = getView().findViewById(R.id.customer_tabs);
        tabs.setupWithViewPager(viewPager);

    }
}
