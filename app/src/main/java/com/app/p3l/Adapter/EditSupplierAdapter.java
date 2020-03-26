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

import com.app.p3l.CRUDActivity.EditProdukActivity;
import com.app.p3l.CRUDActivity.EditSupplierActivity;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditSupplierAdapter extends RecyclerView.Adapter<EditSupplierAdapter.SupplierView> {
    List<SupplierDAO> supplier ;
    private Context context;

    public EditSupplierAdapter(List<SupplierDAO> supplier, Context context) {
        this.supplier = supplier;
        this.context = context;
    }

    @NonNull
    @Override
    public SupplierView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_supplier,parent,false);
        final EditSupplierAdapter.SupplierView EditHolder = new EditSupplierAdapter.SupplierView(view);
        return EditHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierView holder, int position) {
        final SupplierDAO row = supplier.get(position);
        holder.Title.setText(row.getNama());
        holder.Address.setText(row.getAlamat());
        holder.City.setText(row.getKota());
        holder.Phone.setText(row.getNo_hp());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditSupplierActivity.class);
                intent.putExtra("Snama",row.getNama());
                intent.putExtra("Salamat",row.getAlamat());
                intent.putExtra("Skota",row.getKota());
                intent.putExtra("SnoHP",row.getNo_hp());
                intent.putExtra("Sid",Integer.toString(row.getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplier.size();
    }

    public class SupplierView extends RecyclerView.ViewHolder {
        TextView Title,Address,City,Phone;
        LinearLayout parent;
        public SupplierView(@NonNull View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.S_edit_nama);
            Address = (TextView)itemView.findViewById(R.id.S_edit_alamat);
            City = (TextView)itemView.findViewById(R.id.S_edit_kota);
            Phone=(TextView)itemView.findViewById(R.id.S_edit_noHP);
            parent=(LinearLayout)itemView.findViewById(R.id.S_parent);
        }
    }
}
