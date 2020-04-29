package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.app.p3l.CRUDActivity.EditCustomerActivity;
import com.app.p3l.DAO.CustomerDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.TemporaryIdPegawai;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditDeleteCustomerAdapter extends RecyclerView.Adapter<EditDeleteCustomerAdapter.CustomerView> {
    List<CustomerDAO> customer ;
    private Context context;
    ProgressDialog dialog;

    public EditDeleteCustomerAdapter(List<CustomerDAO> customer,Context context) {
        this.customer = customer;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteCustomerAdapter.CustomerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_delete_customer,parent,false);
        final EditDeleteCustomerAdapter.CustomerView EditDeleteHolder = new EditDeleteCustomerAdapter.CustomerView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteCustomerAdapter.CustomerView CustomerView, int position) {
        final CustomerDAO row = customer.get(position);
        int temp = row.getId();
        dialog = new ProgressDialog(context);
        CustomerView.name.setText(row.getNama());
        CustomerView.alamat.setText(row.getAlamat());
        CustomerView.no_hp.setText(row.getNo_hp());
        CustomerView.tanggal.setText(row.getTanggal_lahir());
        CustomerView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(temp,position);
            }
        });
    }

    private void showDialog(int temp,int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Perubahan Data Customer");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Back untuk kembali!")
                .setIcon(R.drawable.paw)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Edit
                        editCustomer(position);
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete
                        deleteCustomer(temp,position);
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

    private void deleteCustomer(int temp, int position) {
        customer.remove(position);
        final String url = "http://renzvin.com/kouvee/api/customer/delete/";
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
                params.put("pegawai_id", Integer.toString(TemporaryIdPegawai.id));
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

    private void editCustomer(int position) {
        final CustomerDAO row = customer.get(position);
        Intent intent = new Intent(context, EditCustomerActivity.class);
        intent.putExtra("Cnama",row.getNama());
        intent.putExtra("Calamat",row.getAlamat());
        intent.putExtra("Ctanggal",row.getTanggal_lahir());
        intent.putExtra("CnoHP",row.getNo_hp());
        intent.putExtra("Cid",Integer.toString(row.getId()));
        context.startActivity(intent);
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
