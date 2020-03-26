package com.app.p3l.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukView> {
    List<ProdukDAO> produk ;
    public ProdukAdapter(Context applicationContext, List<ProdukDAO> produk) {
        this.produk = produk;
    }

    @NonNull
    @Override
    public ProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_produk,parent,false);
        return new ProdukView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukView ProdukView, int position) {
        final ProdukDAO row = produk.get(position);
        ProdukView.Title.setText(row.getNama());
        ProdukView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(ProdukView.Image);
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    public class ProdukView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        public ProdukView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.Produk_Image);
            Title = (TextView)itemView.findViewById(R.id.Produk_Title);
            Price = (TextView)itemView.findViewById(R.id.Produk_Harga);
        }
    }
}
