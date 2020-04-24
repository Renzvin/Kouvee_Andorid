package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.CRUDActivity.ShowDetailTransaksiProduk;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.DAO.Transaksi_ProdukDAO;
import com.app.p3l.R;

import java.util.List;

public class EditDeleteTransaksiProdukAdapter extends RecyclerView.Adapter<EditDeleteTransaksiProdukAdapter.TransaksiView>{
    List<Transaksi_ProdukDAO> transaksi_produkDAOS;
    private Context context;

    public EditDeleteTransaksiProdukAdapter(List<Transaksi_ProdukDAO> transaksi_produkDAOS, Context context) {
        this.transaksi_produkDAOS = transaksi_produkDAOS;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteTransaksiProdukAdapter.TransaksiView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_transaksi_produk,parent,false);
        final EditDeleteTransaksiProdukAdapter.TransaksiView EditDeleteHolder = new EditDeleteTransaksiProdukAdapter.TransaksiView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteTransaksiProdukAdapter.TransaksiView holder, int position) {
        final Transaksi_ProdukDAO row = transaksi_produkDAOS.get(position);
//        int temp = row.getId();
        holder.no_transaksi.setText(row.getNo_transaksi());
        holder.status.setText(row.getStatus());
        holder.tanggal.setText(row.getTanggal());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ShowDetailTransaksiProduk.class);
                i.putExtra("no_transaksi",row.getNo_transaksi());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transaksi_produkDAOS.size();
    }

    public void filterList(List<Transaksi_ProdukDAO> filteredList){
        transaksi_produkDAOS = filteredList;
        notifyDataSetChanged();
    }

    public class TransaksiView extends RecyclerView.ViewHolder {
        TextView no_transaksi, tanggal, status;
        LinearLayout parent;
        public TransaksiView(@NonNull View itemView) {
            super(itemView);
            no_transaksi = (TextView) itemView.findViewById(R.id.nomor_transaksi);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal_transaksi);
            status = (TextView) itemView.findViewById(R.id.status_transaksi);
            parent=(LinearLayout)itemView.findViewById(R.id.transaksi_produk_parent);
        }
    }
}
