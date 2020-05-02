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

import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PemesananProdukAdapter extends RecyclerView.Adapter<PemesananProdukAdapter.PemesananProdukView>{
    List<ProdukDAO> pro ;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public PemesananProdukAdapter(List<ProdukDAO> pro, Context context) {
        this.pro = pro;
        this.context = context;
    }

    @NonNull
    @Override
    public PemesananProdukAdapter.PemesananProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_produk,parent,false);
        final PemesananProdukAdapter.PemesananProdukView EditHolder = new PemesananProdukAdapter.PemesananProdukView(view, mListener);
        return EditHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PemesananProdukAdapter.PemesananProdukView ProdukView, int position) {
        final ProdukDAO row = pro.get(position);
        ProdukView.Title.setText(row.getNama());
        ProdukView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(ProdukView.Image);
    }

    @Override
    public int getItemCount() {
        return pro.size();
    }

    public void filterList(List<ProdukDAO> filteredList){
        pro = filteredList;
        notifyDataSetChanged();
    }

    public class PemesananProdukView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        LinearLayout parent;
        public PemesananProdukView(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.Produk_Image);
            Title = (TextView)itemView.findViewById(R.id.Produk_Title);
            Price = (TextView)itemView.findViewById(R.id.Produk_Harga);
            parent = (LinearLayout) itemView.findViewById(R.id.Produk_Parent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if(listener !=null){
                            int position =   getAdapterPosition();
                            if(position!= RecyclerView.NO_POSITION){
                                listener.onItemClick(position);
                            }
                        }
                }
            });
        }
    }
}
