package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditDeleteSupplierAdapter extends RecyclerView.Adapter<EditDeleteSupplierAdapter.SupplierView>{
    List<SupplierDAO> supplier ;
    private Context context;

    public EditDeleteSupplierAdapter(List<SupplierDAO> supplier, Context context) {
        this.supplier = supplier;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteSupplierAdapter.SupplierView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_delete_supplier,parent,false);
        final EditDeleteSupplierAdapter.SupplierView EditDeleteHolder = new EditDeleteSupplierAdapter.SupplierView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteSupplierAdapter.SupplierView holder, int position) {
        final SupplierDAO row = supplier.get(position);
        int temp = row.getId();
        holder.Title.setText(row.getNama());
        holder.Address.setText(row.getAlamat());
        holder.City.setText(row.getKota());
        holder.Phone.setText(row.getNo_hp());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(temp,position);
            }
        });
    }

    private void showDialog(int temp,int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Perubahan Data Supplier");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Back untuk kembali!")
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Edit
                        editSupplier(position);
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete
                        deleteItem(temp,position);
                    }
                })
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void editSupplier(int position) {
        final SupplierDAO row = supplier.get(position);
        Intent intent = new Intent(context, EditSupplierActivity.class);
        intent.putExtra("Snama",row.getNama());
        intent.putExtra("Salamat",row.getAlamat());
        intent.putExtra("Skota",row.getKota());
        intent.putExtra("SnoHP",row.getNo_hp());
        intent.putExtra("Sid",Integer.toString(row.getId()));
        context.startActivity(intent);
    }

    public void deleteItem(int temp,int position) {
        supplier.remove(position);
        final String url = "http://renzvin.com/kouvee/api/supplier/delete/";
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            notifyItemRemoved(position);
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(temp));
                params.put("deleted_at", format);
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
            Title = (TextView)itemView.findViewById(R.id.S_edit_delete_nama);
            Address = (TextView)itemView.findViewById(R.id.S_edit_delete_alamat);
            City = (TextView)itemView.findViewById(R.id.S_edit_delete_kota);
            Phone=(TextView)itemView.findViewById(R.id.S_edit_delete_noHP);
            parent=(LinearLayout)itemView.findViewById(R.id.S_edit_delete_parent);
        }
    }
}
