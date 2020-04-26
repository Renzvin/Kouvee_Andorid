package com.app.p3l.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TempMultipleLayanan;

import java.util.List;

public class DeskripsiTransaksiLayananAdapter extends RecyclerView.Adapter<DeskripsiTransaksiLayananAdapter.DeskripsiView> {
    private Context context;
    private List<TempMultipleLayanan> produkTemps;

    public DeskripsiTransaksiLayananAdapter(Context context, List<TempMultipleLayanan> produkTemps) {
        this.context = context;
        this.produkTemps = produkTemps;
    }

    @NonNull
    @Override
    public DeskripsiTransaksiLayananAdapter.DeskripsiView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_row_transaksi_layanan, parent, false);
        final DeskripsiTransaksiLayananAdapter.DeskripsiView EditDeleteHolder = new DeskripsiTransaksiLayananAdapter.DeskripsiView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeskripsiTransaksiLayananAdapter.DeskripsiView holder, int position) {
        final TempMultipleLayanan row = produkTemps.get(position);
        holder.nama.setText(row.getNama());
        holder.harga.setText(Integer.toString(row.getHarga()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Aw You Touch Me", Toast.LENGTH_SHORT).show();
            }
        });
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
