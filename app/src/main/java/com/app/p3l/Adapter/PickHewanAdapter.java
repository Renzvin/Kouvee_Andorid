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
import com.app.p3l.CRUDActivity.EditHewanActivity;
import com.app.p3l.CRUDActivity.PickCustomerActivity;
import com.app.p3l.CRUDActivity.PickHewanActivity;
import com.app.p3l.DAO.HewanDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.PickHewan;
import com.app.p3l.Temporary.TempHewan;
import com.app.p3l.Temporary.TemporaryIdPegawai;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickHewanAdapter extends  RecyclerView.Adapter<PickHewanAdapter.HewanView> {
    List<HewanDAO> hewan ;
    private Context context;

    public PickHewanAdapter(List<HewanDAO> hewan, Context context) {
        this.hewan = hewan;
        this.context = context;
    }

    @NonNull
    @Override
    public PickHewanAdapter.HewanView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_hewan,parent,false);
        final PickHewanAdapter.HewanView HewanHolder = new PickHewanAdapter.HewanView(view);
        return HewanHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PickHewanAdapter.HewanView holder, int position) {
        final HewanDAO row = hewan.get(position);
        holder.Title.setText(row.getNama());
        holder.tanggal.setText(row.getTanggal_lahir());
        holder.jenis.setText(row.getNama_jenis());
        holder.ukuran.setText(row.getNama_ukuran());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickHewan.tempHewan = new TempHewan(row.getId(),row.getJenis_id(),row.getUkuran_id(),row.getNama(),row.getNama_jenis(),row.getNama_ukuran());
                ((PickHewanActivity)context).finish();
            }
        });
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
