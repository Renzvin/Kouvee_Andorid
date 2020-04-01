package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.Activity.DescLayananActivity;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.LayananView>{
    List<LayananDAO> layanan ;
    private Context context;

    public LayananAdapter(List<LayananDAO> layanan, Context context) {
        this.layanan = layanan;
        this.context = context;
    }

    @NonNull
    @Override
    public LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_layanan,parent,false);
        final LayananAdapter.LayananView LayananHolder = new LayananAdapter.LayananView(view);
        return LayananHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull LayananView holder, int position) {
        final LayananDAO row = layanan.get(position);
        holder.Title.setText(row.getNama());
        holder.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(holder.Image);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescLayananActivity.class);
                intent.putExtra("nama",row.getNama());
                intent.putExtra("harga",Integer.toString(row.getHarga()));
                intent.putExtra("created",row.getCreated_at());
                intent.putExtra("edited",row.getEdited_at());
                intent.putExtra("link",row.getLink_gambar());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return layanan.size();
    }

    public class LayananView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        LinearLayout parent;
        public LayananView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.Layanan_Image);
            Title = (TextView)itemView.findViewById(R.id.Layanan_Title);
            Price = (TextView)itemView.findViewById(R.id.Layanan_Harga);
            parent = (LinearLayout)itemView.findViewById(R.id.Layanan_Parent);
        }
    }
}
