package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.CRUDActivity.ShowDetailTransaksiLayananActivity;
import com.app.p3l.CRUDActivity.ShowDetailTransaksiProduk;
import com.app.p3l.DAO.TransaksiDAO;
import com.app.p3l.R;

import java.util.List;

public class EditDeleteTransaksiLayananAdapter extends RecyclerView.Adapter<EditDeleteTransaksiLayananAdapter.TransaksiView> {
    List<TransaksiDAO> transaksi_DAOS;
    private Context context;

    public EditDeleteTransaksiLayananAdapter(List<TransaksiDAO> transaksi_DAOS, Context context) {
        this.transaksi_DAOS = transaksi_DAOS;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteTransaksiLayananAdapter.TransaksiView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_transaksi,parent,false);
        final EditDeleteTransaksiLayananAdapter.TransaksiView EditDeleteHolder = new EditDeleteTransaksiLayananAdapter.TransaksiView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteTransaksiLayananAdapter.TransaksiView holder, int position) {
        final TransaksiDAO row = transaksi_DAOS.get(position);
//        int temp = row.getId();
        holder.no_transaksi.setText(row.getNo_transaksi());
        holder.status.setText(row.getStatus());
        holder.tanggal.setText(row.getTanggal());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ShowDetailTransaksiLayananActivity.class);
                i.putExtra("no_transaksi",row.getNo_transaksi());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transaksi_DAOS.size();
    }

    public void filterList(List<TransaksiDAO> filteredList){
        transaksi_DAOS = filteredList;
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
