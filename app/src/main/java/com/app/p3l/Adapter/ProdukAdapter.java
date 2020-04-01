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

import com.app.p3l.Activity.DescProdukActivity;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.google.api.Distribution;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukView> {
    List<ProdukDAO> produk ;
    private Context context;

    public ProdukAdapter(List<ProdukDAO> produk,Context context) {
        this.produk = produk;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_produk,parent,false);
        final ProdukAdapter.ProdukView EditHolder = new ProdukAdapter.ProdukView(view);
        return EditHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukView ProdukView, int position) {
        final ProdukDAO row = produk.get(position);
        ProdukView.Title.setText(row.getNama());
        ProdukView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(ProdukView.Image);
        ProdukView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescProdukActivity.class);
                intent.putExtra("nama",row.getNama());
                intent.putExtra("stock",Integer.toString(row.getStock()));
                intent.putExtra("harga",Integer.toString(row.getHarga()));
                intent.putExtra("created",row.getCreated_at());
                intent.putExtra("edited",row.getEdited_at());
                intent.putExtra("deleted",row.getDeleted_at());
                intent.putExtra("link",row.getLink_gambar());
                intent.putExtra("kategori",Integer.toString(row.getKategori_id()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    public class ProdukView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        LinearLayout parent;
        public ProdukView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.Produk_Image);
            Title = (TextView)itemView.findViewById(R.id.Produk_Title);
            Price = (TextView)itemView.findViewById(R.id.Produk_Harga);
            parent = (LinearLayout) itemView.findViewById(R.id.Produk_Parent);
        }
    }
}
