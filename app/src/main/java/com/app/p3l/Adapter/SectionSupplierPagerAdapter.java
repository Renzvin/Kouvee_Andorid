package com.app.p3l.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.ui.CRUDdata.CRUDSupplierFragment;

public class SectionSupplierPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;


    public SectionSupplierPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CRUDSupplierFragment();
        return  fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
