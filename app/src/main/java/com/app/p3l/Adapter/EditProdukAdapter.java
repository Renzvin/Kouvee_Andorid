package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.CRUDActivity.EditProdukActivity;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.google.api.Distribution;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditProdukAdapter extends RecyclerView.Adapter<EditProdukAdapter.ProdukView> {
    List<ProdukDAO> produk ;
    private Context context;
    public EditProdukAdapter(List<ProdukDAO> produk,Context context) {
        this.produk = produk;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_produk,parent,false);
        final EditProdukAdapter.ProdukView EditHolder = new EditProdukAdapter.ProdukView(view);
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
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProdukActivity.class);
                intent.putExtra("Pnama",row.getNama());
                intent.putExtra("Pstock",Integer.toString(row.getStock()));
                intent.putExtra("Pharga",Integer.toString(row.getHarga()));
                intent.putExtra("Pgambar",row.getLink_gambar());
                intent.putExtra("Pid",Integer.toString(row.getId()));
                context.startActivity(intent);
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
            Image = (ImageView)itemView.findViewById(R.id.edit_Produk_Image);
            Title = (TextView)itemView.findViewById(R.id.edit_Produk_Title);
            Price = (TextView)itemView.findViewById(R.id.edit_Produk_Harga);
            parent = (LinearLayout)itemView.findViewById(R.id.edit_Produk_Parent);
        }
    }
}
