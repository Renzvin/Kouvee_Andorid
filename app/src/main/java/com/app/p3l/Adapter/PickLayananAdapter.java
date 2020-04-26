package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.Activity.DescLayananActivity;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.TempListPickLayanan;
import com.app.p3l.Temporary.TempMultipleLayanan;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PickLayananAdapter extends RecyclerView.Adapter<PickLayananAdapter.LayananView> {
    List<LayananDAO> layanan;
    private Context context;

    public PickLayananAdapter(List<LayananDAO> layanan, Context context) {
        this.layanan = layanan;
        this.context = context;
    }

    @NonNull
    @Override
    public PickLayananAdapter.LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_layanan, parent, false);
        final PickLayananAdapter.LayananView LayananHolder = new PickLayananAdapter.LayananView(view);
        return LayananHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull PickLayananAdapter.LayananView holder, int position) {
        final LayananDAO row = layanan.get(position);
        holder.Title.setText(row.getNama());
        holder.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(holder.Image);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempMultipleLayanan layanan = new TempMultipleLayanan(row.getId(),row.getHarga(),row.getNama());
                TempListPickLayanan.layananList.add(layanan);
                Toast.makeText(context,"Sukses memilih layanan",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return layanan.size();
    }

    public void filterList(List<LayananDAO> filteredList) {
        layanan = filteredList;
        notifyDataSetChanged();
    }

    public class LayananView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title, Price;
        LinearLayout parent;

        public LayananView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView) itemView.findViewById(R.id.Layanan_Image);
            Title = (TextView) itemView.findViewById(R.id.Layanan_Title);
            Price = (TextView) itemView.findViewById(R.id.Layanan_Harga);
            parent = (LinearLayout) itemView.findViewById(R.id.Layanan_Parent);
        }
    }
}