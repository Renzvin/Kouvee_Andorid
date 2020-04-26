package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TempListPickProduk;
import com.app.p3l.Temporary.TempMultipleLayanan;

import java.util.List;

public class ListLayananPickAdapter extends RecyclerView.Adapter<ListLayananPickAdapter.DeskripsiView> {
    private Context context;
    private List<TempMultipleLayanan> produkTemps;

    public ListLayananPickAdapter(Context context, List<TempMultipleLayanan> produkTemps) {
        this.context = context;
        this.produkTemps = produkTemps;
    }

    @NonNull
    @Override
    public ListLayananPickAdapter.DeskripsiView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_row_transaksi_layanan, parent, false);
        final ListLayananPickAdapter.DeskripsiView EditDeleteHolder = new ListLayananPickAdapter.DeskripsiView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListLayananPickAdapter.DeskripsiView holder, int position) {
        final TempMultipleLayanan row = produkTemps.get(position);
        holder.nama.setText(row.getNama());
        holder.harga.setText(Integer.toString(row.getHarga()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = row.getId();
                for (int count = 0; count < TempListPickProduk.produkTemp.size(); count++) {
                    if (TempListPickProduk.produkTemp.get(count).getId() == temp) {
                        showDialog(temp, position);
                    }
                }
            }
        });
    }

    private void showDialog(final int temp, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Kelola Produk yang Dipilih");

        // set pesan dari dialog
        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertDialogBuilder.setView(input);
        alertDialogBuilder
                .setMessage("Klik Back untuk kembali!")
                .setIcon(R.drawable.paw)
                .setCancelable(false)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for (int count = 0; count < TempListPickProduk.produkTemp.size(); count++) {
                            if (temp == TempListPickProduk.produkTemp.get(count).getId()) {
                                int tempHarga = TempListPickProduk.produkTemp.get(count).getHarga() / TempListPickProduk.produkTemp.get(count).getJumlah();
                                TempListPickProduk.produkTemp.get(count).setJumlah(Integer.parseInt(input.getText().toString()));
                                TempListPickProduk.produkTemp.get(count).setHarga(Integer.parseInt(input.getText().toString()) * tempHarga);
                            }
                        }
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                notifyItemRemoved(position);
                TempListPickProduk.produkTemp.remove(temp);
            }
        })
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    public int getItemCount() {
        return produkTemps.size();
    }

    public void filterList(List<TempMultipleLayanan> filteredList) {
        produkTemps = filteredList;
        notifyDataSetChanged();
    }

    public class DeskripsiView extends RecyclerView.ViewHolder {
        TextView nama, jumlah, harga;
        LinearLayout parent;

        public DeskripsiView(@NonNull View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.tvNamaProduk);
            harga = (TextView) itemView.findViewById(R.id.tvHargaProduk);
            parent = (LinearLayout) itemView.findViewById(R.id.transaksi_produk_parent);
        }
    }
}
