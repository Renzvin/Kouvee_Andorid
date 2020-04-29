package com.app.p3l.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.R;
import com.app.p3l.ui.CRUDdata.CRUDTransaksiLayananFragment;
import com.app.p3l.ui.CRUDdata.CRUDTransaksiProdukFragment;
import com.app.p3l.ui.hewan.HewanFragment;
import com.app.p3l.ui.jenis_hewan.JenisHewanFragment;
import com.app.p3l.ui.ukuran_hewan.UkuranHewanFragment;

public class SectionTransaksiCSPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.sub5, R.string.sub6};
    private final Context mContext;


    public SectionTransaksiCSPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CRUDTransaksiProdukFragment();
                break;
            case 1:
                fragment = new CRUDTransaksiLayananFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
