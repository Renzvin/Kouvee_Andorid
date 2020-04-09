package com.app.p3l.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.R;
import com.app.p3l.ui.CRUDdata.CRUDCustomerFragment;
import com.app.p3l.ui.CRUDdata.CRUDProdukFragment;
import com.app.p3l.ui.hewan.HewanFragment;
import com.app.p3l.ui.jenis_hewan.JenisHewanFragment;
import com.app.p3l.ui.ukuran_hewan.UkuranHewanFragment;

public class SectionCustomerPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;


    public SectionCustomerPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CRUDCustomerFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public int getCount() {
        return 1;
    }
}
