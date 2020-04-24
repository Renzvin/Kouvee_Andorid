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

import com.app.p3l.DAO.CustomerDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TempCustomer;
import com.app.p3l.Temporary.TempListPickProduk;

import java.util.List;

public class DeskripsiTransaksiProdukAdapter extends RecyclerView.Adapter<DeskripsiTransaksiProdukAdapter.DeskripsiView> {
    private Context context;
    private List<ProdukTemp> produkTemps;

    public DeskripsiTransaksiProdukAdapter(Context context, List<ProdukTemp> produkTemps) {
        this.context = context;
        this.produkTemps = produkTemps;
    }

    @NonNull
    @Override
    public DeskripsiTransaksiProdukAdapter.DeskripsiView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_row_transaksi_produk,parent,false);
        final DeskripsiTransaksiProdukAdapter.DeskripsiView EditDeleteHolder = new DeskripsiTransaksiProdukAdapter.DeskripsiView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeskripsiView holder, int position) {
        final ProdukTemp row = produkTemps.get(position);
        holder.nama.setText(row.getNama());
        holder.jumlah.setText(Integer.toString(row.getJumlah()));
        holder.harga.setText(Integer.toString(row.getHarga()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Aw You Touch Me",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getItemCount() {
        return produkTemps.size();
    }

    public void filterList(List<ProdukTemp> filteredList){
        produkTemps = filteredList;
        notifyDataSetChanged();
    }

    public class DeskripsiView extends RecyclerView.ViewHolder {
        TextView nama, jumlah, harga;
        LinearLayout parent;
        public DeskripsiView(@NonNull View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.tvNamaProduk);
            jumlah = (TextView) itemView.findViewById(R.id.tvJumlahProduk);
            harga = (TextView) itemView.findViewById(R.id.tvHargaProduk);
            parent=(LinearLayout)itemView.findViewById(R.id.transaksi_produk_parent);
        }
    }
}
