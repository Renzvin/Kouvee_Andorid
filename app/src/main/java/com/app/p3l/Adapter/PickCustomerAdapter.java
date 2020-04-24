package com.app.p3l.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.CRUDActivity.CreateTransaksiProdukActivity;
import com.app.p3l.CRUDActivity.PickCustomerActivity;
import com.app.p3l.DAO.CustomerDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.PickCustomer;
import com.app.p3l.Temporary.TempCustomer;

import java.util.List;

public class PickCustomerAdapter extends RecyclerView.Adapter<PickCustomerAdapter.CustomerView>{
    private Context context;
    List<CustomerDAO> customer;

    public PickCustomerAdapter(Context context, List<CustomerDAO> customer) {
        this.context = context;
        this.customer = customer;
    }

    @NonNull
    @Override
    public PickCustomerAdapter.CustomerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_delete_customer,parent,false);
        final PickCustomerAdapter.CustomerView EditDeleteHolder = new PickCustomerAdapter.CustomerView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickCustomerAdapter.CustomerView CustomerView, int position) {
        final CustomerDAO row = customer.get(position);
        CustomerView.name.setText(row.getNama());
        CustomerView.alamat.setText(row.getAlamat());
        CustomerView.no_hp.setText(row.getNo_hp());
        CustomerView.tanggal.setText(row.getTanggal_lahir());
        CustomerView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickCustomer.tempCustomer = new TempCustomer(row.getNama(),row.getAlamat(),row.getNo_hp(),row.getId());
                Intent i = new Intent(context, CreateTransaksiProdukActivity.class);
                context.startActivity(i);
                ((PickCustomerActivity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customer.size();
    }

    public void filterList(List<CustomerDAO> filteredList){
        customer = filteredList;
        notifyDataSetChanged();
    }


    public class CustomerView extends RecyclerView.ViewHolder {
        TextView name,alamat,no_hp,tanggal;
        LinearLayout parent;
        public CustomerView(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.C_edit_delete_nama);
            alamat = (TextView)itemView.findViewById(R.id.C_edit_delete_alamat);
            no_hp = (TextView)itemView.findViewById(R.id.C_edit_delete_noHP);
            tanggal = (TextView)itemView.findViewById(R.id.C_edit_delete_tanggal);
            parent = (LinearLayout)itemView.findViewById(R.id.C_edit_delete_parent);
        }
    }
}
