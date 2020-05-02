package com.app.p3l.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.DAO.Detail_PemesananDAO;
import com.app.p3l.R;

import java.util.List;

public class EditDeleteDetailPemesananAdapter extends RecyclerView.Adapter<EditDeleteDetailPemesananAdapter.DetailPemesananView> {
    private Context context;
    List<Detail_PemesananDAO> pemesananDAO;

    public EditDeleteDetailPemesananAdapter(List<Detail_PemesananDAO> pemesananDAO, Context context) {
        this.pemesananDAO = pemesananDAO;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteDetailPemesananAdapter.DetailPemesananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_pemesanan_produk, parent, false);
        final EditDeleteDetailPemesananAdapter.DetailPemesananView EditDeleteHolder = new EditDeleteDetailPemesananAdapter.DetailPemesananView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteDetailPemesananAdapter.DetailPemesananView DetailPemesananView, int position) {
        final Detail_PemesananDAO row = pemesananDAO.get(position);
        DetailPemesananView.namaProduk.setText(row.getProduk_Name());
        DetailPemesananView.jumlah.setText(String.valueOf(row.getJumlah()));
        DetailPemesananView.satuan.setText(row.getSatuan());
    }

    @Override
    public int getItemCount() {
        return pemesananDAO.size();
    }

    public void filterList(List<Detail_PemesananDAO> filteredList) {
        pemesananDAO = filteredList;
        notifyDataSetChanged();
    }

    public class DetailPemesananView extends RecyclerView.ViewHolder {
        TextView namaProduk,jumlah,satuan;
        LinearLayout parent;
        public DetailPemesananView(@NonNull View itemView) {
            super(itemView);
            namaProduk = (TextView) itemView.findViewById(R.id.row_pp_produk);
            jumlah = (TextView) itemView.findViewById(R.id.row_pp_jumlah);
            satuan = (TextView) itemView.findViewById(R.id.row_pp_satuan);
            parent = (LinearLayout)itemView.findViewById(R.id.row_linear_layout);
        }
    }
}
