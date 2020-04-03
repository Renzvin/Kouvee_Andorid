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
import com.app.p3l.CRUDActivity.EditJenisHewanActivity;
import com.app.p3l.DAO.HewanDAO;
import com.app.p3l.DAO.Jenis_HewanDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HewanAdapter extends  RecyclerView.Adapter<HewanAdapter.HewanView> {
    List<HewanDAO> hewan ;
    private Context context;
    String status = "-";
    String message = "-";

    public HewanAdapter(List<HewanDAO> hewan, Context context) {
        this.hewan = hewan;
        this.context = context;
    }

    @NonNull
    @Override
    public HewanAdapter.HewanView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_hewan,parent,false);
        final HewanAdapter.HewanView HewanHolder = new HewanAdapter.HewanView(view);
        return HewanHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HewanAdapter.HewanView holder, int position) {
        final HewanDAO row = hewan.get(position);
        holder.Title.setText(row.getNama());
        holder.tanggal.setText(row.getTanggal_lahir());
        holder.jenis.setText(Integer.toString(row.getUkuran_id()));
        holder.ukuran.setText(Integer.toString(row.getUkuran_id()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditJenisHewanActivity.class);
                intent.putExtra("Jnama",row.getNama());
                intent.putExtra("Jid",Integer.toString(row.getId()));
                context.startActivity(intent);
            }
        });
    }

    public void deleteItem(int position) {
        final HewanDAO row = hewan.get(position);
        int temp = row.getId();
        hewan.remove(position);
        notifyItemRemoved(position);
        final String url = "http://renzvin.com/kouvee/api/Hewan/delete/";
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


                            Toast.makeText(context, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal Hapus Data", Toast.LENGTH_SHORT).show();
            }
        }) {
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

    public int getItemCount() {
        return hewan.size();
    }

    public void filterList(List<HewanDAO> filteredList){
        hewan = filteredList;
        notifyDataSetChanged();
    }

    public class HewanView extends RecyclerView.ViewHolder {
        TextView Title,jenis,ukuran,tanggal;
        LinearLayout parent;
        public HewanView(@NonNull View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.nama_hewan);
            jenis = (TextView)itemView.findViewById(R.id.jenis);
            ukuran = (TextView)itemView.findViewById(R.id.ukuran);
            tanggal=(TextView)itemView.findViewById(R.id.tanggal_lahir);
            parent=(LinearLayout)itemView.findViewById(R.id.hewan_parent);
        }
    }
}
