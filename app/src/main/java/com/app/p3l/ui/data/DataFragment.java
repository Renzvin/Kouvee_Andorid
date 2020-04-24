package com.app.p3l.ui.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.app.p3l.Activity.HewanActivity;
import com.app.p3l.Activity.LayananActivity;
import com.app.p3l.Activity.ProdukActivity;
import com.app.p3l.Activity.SupplierActivity;
import com.app.p3l.Activity.TransaksiProdukActivity;
import com.app.p3l.CRUDActivity.CreateTransaksiProdukActivity;
import com.app.p3l.R;


public class DataFragment extends Fragment {
    ImageButton produk,layanan,hewan,supplier,transaksi_produk,transaksi_layanan;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_data, container, false);
        produk = (ImageButton) v.findViewById(R.id.button_produk);
        produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProdukActivity.class);
                startActivity(i);
            }
        });
        supplier = (ImageButton) v.findViewById(R.id.button_supplier);
        supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SupplierActivity.class);
                startActivity(i);
            }
        });
        hewan = (ImageButton) v.findViewById(R.id.button_hewan);
        hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HewanActivity.class);
                startActivity(i);
            }
        });
        layanan = (ImageButton) v.findViewById(R.id.button_layanan);
        layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LayananActivity.class);
                startActivity(i);
            }
        });
        transaksi_produk = (ImageButton)v.findViewById(R.id.button_trans_produk);
        transaksi_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TransaksiProdukActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}
