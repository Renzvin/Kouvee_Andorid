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

import com.app.p3l.CRUDActivity.EditLayananActivity;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.google.api.Distribution;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditLayananAdapter extends RecyclerView.Adapter<EditLayananAdapter.LayananView> {
    List<LayananDAO> layanan;
    private Context context;
    public EditLayananAdapter(List<LayananDAO> layanan,Context context) {
        this.layanan = layanan;
        this.context = context;
    }

    @NonNull
    @Override
    public LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_layanan,parent,false);
        final EditLayananAdapter.LayananView EditHolder = new EditLayananAdapter.LayananView(view);
        return EditHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LayananView LayananView, int position) {
        final LayananDAO row = layanan.get(position);
        LayananView.Title.setText(row.getNama());
        LayananView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(LayananView.Image);
        LayananView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditLayananActivity.class);
                intent.putExtra("Lnama",row.getNama());
                intent.putExtra("Lharga",Integer.toString(row.getHarga()));
                intent.putExtra("Lgambar",row.getLink_gambar());
                intent.putExtra("Lid",Integer.toString(row.getId()));
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
            Image = (ImageView)itemView.findViewById(R.id.edit_Layanan_Image);
            Title = (TextView)itemView.findViewById(R.id.edit_Layanan_Title);
            Price = (TextView)itemView.findViewById(R.id.edit_Layanan_Harga);
            parent = (LinearLayout)itemView.findViewById(R.id.edit_Layanan_Parent);
        }
    }
}
