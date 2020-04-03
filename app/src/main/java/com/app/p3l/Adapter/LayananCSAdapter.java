package com.app.p3l.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LayananCSAdapter extends RecyclerView.Adapter<LayananCSAdapter.LayananView> {
    List<LayananDAO> layanan ;
    private Context context;
    public LayananCSAdapter(Context context, List<LayananDAO> layanan) {
        this.context = context;
        this.layanan = layanan;
    }

    @NonNull
    @Override
    public LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_cs_layanan,parent,false);
        final LayananCSAdapter.LayananView LayananHolder = new LayananCSAdapter.LayananView(view);
        return LayananHolder;
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

    public void filterList(List<LayananDAO> filteredList){
        layanan = filteredList;
        notifyDataSetChanged();
    }

    public class LayananView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        LinearLayout parent;
        public LayananView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.Layanan_CS_Image);
            Title = (TextView)itemView.findViewById(R.id.Layanan_CS_Title);
            Price = (TextView)itemView.findViewById(R.id.Layanan_CS_Harga);
            parent = (LinearLayout)itemView.findViewById(R.id.Layanan_CS_Parent);
        }
    }
}
