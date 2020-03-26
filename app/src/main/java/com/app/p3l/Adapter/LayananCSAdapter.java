package com.app.p3l.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LayananCSAdapter extends RecyclerView.Adapter<LayananCSAdapter.LayananView> {
    List<LayananDAO> layanan ;

    public LayananCSAdapter(Context applicationContext, List<LayananDAO> produk) {
        this.layanan = produk;
    }

    @NonNull
    @Override
    public LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cs_layanan,parent,false);
        return new LayananView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LayananView LayananView, int position) {
        final LayananDAO row = layanan.get(position);

        LayananView.Title.setText(row.getNama());
        LayananView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(LayananView.Image);
    }

    @Override
    public int getItemCount() {
        return layanan.size();
    }

    public class LayananView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        public LayananView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.Layanan_CS_Image);
            Title = (TextView)itemView.findViewById(R.id.Layanan_CS_Title);
            Price = (TextView)itemView.findViewById(R.id.Layanan_CS_Harga);
        }
    }
}
