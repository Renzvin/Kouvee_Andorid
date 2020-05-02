package com.app.p3l.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.ui.CRUDdata.CRUDPemesananFragment;

public class SectionPemesananPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public SectionPemesananPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CRUDPemesananFragment();
        return  fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
