package com.app.p3l.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.R;
import com.app.p3l.ui.CRUDdata.CRUDLayananFragment;

public class SectionLayananPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public SectionLayananPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CRUDLayananFragment();
        return  fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
