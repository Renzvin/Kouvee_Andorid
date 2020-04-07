package com.app.p3l.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.ui.CRUDdata.CRUDProdukFragment;

public class SectionProdukPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;


    public SectionProdukPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CRUDProdukFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public int getCount() {
        return 1;
    }
}
