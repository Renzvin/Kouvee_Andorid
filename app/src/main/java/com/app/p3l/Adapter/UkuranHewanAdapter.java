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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.CRUDActivity.EditUkuranHewanActivity;
import com.app.p3l.DAO.Jenis_HewanDAO;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.Ukuran_HewanDAO;
import com.app.p3l.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UkuranHewanAdapter extends  RecyclerView.Adapter<UkuranHewanAdapter.UkuranView> {
    List<Ukuran_HewanDAO> ukuran ;
    private Context context;

    String status = "-";
    String message = "-";


    public UkuranHewanAdapter(List<Ukuran_HewanDAO> ukuran, Context context) {
        this.ukuran = ukuran;
        this.context = context;
    }

    @NonNull
    @Override
    public UkuranView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_ukuran_hewan,parent,false);
        final UkuranHewanAdapter.UkuranView UkuranHolder = new UkuranHewanAdapter.UkuranView(view);
        return UkuranHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UkuranView holder, int position) {
        final Ukuran_HewanDAO row = ukuran.get(position);
        holder.Title.setText(row.getNama());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditUkuranHewanActivity.class);
                intent.putExtra("Uid",Integer.toString(row.getId()));
                intent.putExtra("Unama",row.getNama());
                context.startActivity(intent);
            }
        });
    }

    public void deleteItem(int position) {
        final Ukuran_HewanDAO row = ukuran.get(position);
        int temp = row.getId();
        ukuran.remove(position);
        notifyItemRemoved(position);
        final String url = "http://renzvin.com/kouvee/api/UkuranHewan/delete/";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            status = jsonObject.getString("status");
                            message = jsonObject.getString("data");

                            System.out.println("Response : " + status);
                            System.out.println("Message  : " + message);



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
        return ukuran.size();
    }

    public void filterList(List<Ukuran_HewanDAO> filteredList){
        ukuran = filteredList;
        notifyDataSetChanged();
    }

    public class UkuranView extends RecyclerView.ViewHolder {
        TextView Title;
        LinearLayout parent;
        public UkuranView(@NonNull View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.ukuran_hewan);
            parent=(LinearLayout)itemView.findViewById(R.id.ukuran_parent);
        }
    }
}
