package com.app.p3l.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TempListPickProduk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pop_up_produk_dialog extends DialogFragment {
    private EditText jumlah;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_up_produk_dialog,null);
        jumlah = (EditText)view.findViewById(R.id.pick_jumlah);

        builder.setView(view).setTitle("Tambah Pembelian Produk").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Success Create Product",Toast.LENGTH_SHORT).show();
                ProdukTemp produkTemp = new ProdukTemp(getArguments().getString("nama"),getArguments().getInt("id"),Integer.parseInt(jumlah.getText().toString()),getArguments().getInt("harga")*Integer.parseInt(jumlah.getText().toString()));
                TempListPickProduk.produkTemp.add(produkTemp);
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
