package com.app.p3l.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.p3l.R;
import com.app.p3l.ui.CRUDdata.CreateProdukFragment;
import com.app.p3l.ui.CRUDdata.DeleteProdukFragment;
import com.app.p3l.ui.CRUDdata.EditProdukFragment;
import com.app.p3l.ui.hewan.HewanFragment;
import com.app.p3l.ui.jenis_hewan.JenisHewanFragment;
import com.app.p3l.ui.ukuran_hewan.UkuranHewanFragment;

public class SectionHewanPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_4, R.string.tab_text_5,R.string.tab_text_6};
    private final Context mContext;


    public SectionHewanPagerAdapter(Context mContext, FragmentManager fm) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new HewanFragment();
                break;
            case 1:
                fragment = new JenisHewanFragment();
                break;
            case 2:
                fragment = new UkuranHewanFragment();
                break;
        }
        return  fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
