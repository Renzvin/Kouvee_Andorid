package com.app.p3l.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.CRUDActivity.EditSupplierActivity;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteSupplierAdapter extends RecyclerView.Adapter<DeleteSupplierAdapter.SupplierView> {
    List<SupplierDAO> supplier ;
    private Context context;

    public DeleteSupplierAdapter(List<SupplierDAO> supplier, Context context) {
        this.supplier = supplier;
        this.context = context;
    }

    @NonNull
    @Override
    public SupplierView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_delete_supplier,parent,false);
        final DeleteSupplierAdapter.SupplierView DeleteHolder = new DeleteSupplierAdapter.SupplierView(view);
        return DeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierView holder, int position) {
        final SupplierDAO row = supplier.get(position);
        holder.Title.setText(row.getNama());
        holder.Address.setText(row.getAlamat());
        holder.City.setText(row.getKota());
        holder.Phone.setText(row.getNo_hp());
    }

    public void deleteItem(int position) {
        final SupplierDAO row = supplier.get(position);
        int temp = row.getId();
        supplier.remove(position);
        notifyItemRemoved(position);
        final String url = "http://renzvin.com/kouvee/api/supplier/delete/";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(context,"Berhasil Hapus Data",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Gagal Hapus Data",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(temp));
                return params;
            }
        };
        post.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        queue.add(post);
    }


    @Override
    public int getItemCount() {
        return supplier.size();
    }

    public void filterList(List<SupplierDAO> filteredList){
        supplier = filteredList;
        notifyDataSetChanged();
    }

    public class SupplierView extends RecyclerView.ViewHolder {
        TextView Title,Address,City,Phone;
        LinearLayout parent;
        public SupplierView(@NonNull View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.S_delete_nama);
            Address = (TextView)itemView.findViewById(R.id.S_delete_alamat);
            City = (TextView)itemView.findViewById(R.id.S_delete_kota);
            Phone=(TextView)itemView.findViewById(R.id.S_delete_noHP);
            parent=(LinearLayout)itemView.findViewById(R.id.S_delete_parent);
        }
    }
}
