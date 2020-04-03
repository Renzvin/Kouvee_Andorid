package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Activity.CSActivity;
import com.app.p3l.Activity.LoginActivity;
import com.app.p3l.Activity.MainActivity;
import com.app.p3l.CRUDActivity.EditProdukActivity;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteLayananAdapter extends RecyclerView.Adapter<DeleteLayananAdapter.LayananView> {
    List<LayananDAO> layanan ;
    private Context context;

    String status = "-";
    String message = "-";
    public DeleteLayananAdapter(List<LayananDAO> layanan,Context context) {
        this.layanan = layanan;
        this.context = context;
    }

    @NonNull
    @Override
    public LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_delete_layanan,parent,false);
        final DeleteLayananAdapter.LayananView DeleteHolder = new DeleteLayananAdapter.LayananView(view);
        return DeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LayananView LayananView, int position) {
        final LayananDAO row = layanan.get(position);
        int temp = row.getId();

        LayananView.Title.setText(row.getNama());
        LayananView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(LayananView.Image);
        LayananView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "http://renzvin.com/kouvee/api/layanan/delete/";
                VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {

                                try {
                                    JSONObject obj = new JSONObject(new String(response.data));
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Sukses Menghapus Data", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context.getApplicationContext(), "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                            }
                        })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", Integer.toString(temp));
                        return params;
                    }
                };
                Volley.newRequestQueue(context.getApplicationContext()).add(volleyMultipartRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return layanan.size();
    }

    public void filterList(List<LayananDAO> filteredList){
        layanan = filteredList;
        notifyDataSetChanged();
    }

    public void filteredList(ArrayList<LayananDAO> filteredList){
        layanan = filteredList;
        notifyDataSetChanged();
    }

    public class LayananView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        LinearLayout parent;
        public LayananView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.delete_Layanan_Image);
            Title = (TextView)itemView.findViewById(R.id.delete_Layanan_Title);
            Price = (TextView)itemView.findViewById(R.id.delete_Layanan_Harga);
            parent = (LinearLayout)itemView.findViewById(R.id.delete_Layanan_Parent);
        }
    }
}
