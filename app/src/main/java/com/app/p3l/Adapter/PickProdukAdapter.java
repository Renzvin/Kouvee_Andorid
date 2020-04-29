package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.Activity.DescProdukActivity;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TempListPickProduk;
import com.app.p3l.ui.dialog.pop_up_produk_dialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PickProdukAdapter extends RecyclerView.Adapter<PickProdukAdapter.ProdukView> {
    private Context context;
    private List<ProdukDAO> produk;

    public PickProdukAdapter(Context context, List<ProdukDAO> produk) {
        this.context = context;
        this.produk = produk;
    }

    @NonNull
    @Override
    public PickProdukAdapter.ProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_produk,parent,false);
        final PickProdukAdapter.ProdukView EditHolder = new PickProdukAdapter.ProdukView(view);
        return EditHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickProdukAdapter.ProdukView ProdukView, int position) {
        final ProdukDAO row = produk.get(position);
        ProdukView.Title.setText(row.getNama());
        ProdukView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(ProdukView.Image);
        ProdukView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_up_produk_dialog dialog = new pop_up_produk_dialog();
                Bundle bundle = new Bundle();
                bundle.putInt("id", row.getId());
                bundle.putString("nama", row.getNama());
                bundle.putInt("harga",row.getHarga());
                dialog.setArguments(bundle);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"Create Produk");

            }
        });
    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    public void filterList(List<ProdukDAO> filteredList){
        produk = filteredList;
        notifyDataSetChanged();
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
