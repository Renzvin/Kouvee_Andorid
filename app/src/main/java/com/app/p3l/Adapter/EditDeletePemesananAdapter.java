package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.CRUDActivity.EditAddProdukActivity;
import com.app.p3l.DAO.Detail_PemesananDAO;
import com.app.p3l.DAO.PemesananDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditDeletePemesananAdapter extends  RecyclerView.Adapter<EditDeletePemesananAdapter.PemesananView> {
    List<PemesananDAO> pemesanan;
    ProgressDialog dialog;
    private EditDeleteDetailPemesananAdapter dpemesananadapter;
    private Context context;

    public EditDeletePemesananAdapter(List<PemesananDAO> pemesanan, Context context) {
        this.pemesanan = pemesanan;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeletePemesananAdapter.PemesananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_pemesanan_produk, parent, false);
        dialog = new ProgressDialog(context);
        final EditDeletePemesananAdapter.PemesananView EditDeleteHolder = new EditDeletePemesananAdapter.PemesananView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeletePemesananAdapter.PemesananView PemesananView, int position) {
        final PemesananDAO row = pemesanan.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date tanggal_pesan = row.getTanggal_pesan();
        String tanggalPesan = sdf.format(tanggal_pesan);

        PemesananView.nomorPO.setText(row.getNomor_po());
        PemesananView.supplierName.setText(row.getSupplier_name());
        PemesananView.status.setText(row.getStatus());
        PemesananView.dateOrder.setText(tanggalPesan);
        if(row.getStatus().equalsIgnoreCase("Dicetak")) {
            PemesananView.scrollViewParent.setBackgroundColor(Color.parseColor("#FFDAD8D9"));
        }else{
            PemesananView.scrollViewParent.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        PemesananView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(row.getNomor_po(),row.getStatus(),position);
            }
        });

        do{
            if(getDetailPemesanan(row.getNomor_po())==null){
                getDetailPemesanan(row.getNomor_po());
            } else {
                RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
                dpemesananadapter = new EditDeleteDetailPemesananAdapter(getDetailPemesanan(row.getNomor_po()), context);
                PemesananView.pemesanan_recycler.setLayoutManager(manager);
                PemesananView.pemesanan_recycler.setAdapter(dpemesananadapter);
            }
        } while(getDetailPemesanan(row.getNomor_po())==null);

    }

    private void showDialog(String nomor_po,String status,int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        Button btnubahPemesanan = (Button)view.findViewById(R.id.ubah_pemesanan);
        Button btnhapusPemesanan = (Button)view.findViewById(R.id.hapus_pemesanan);
        Button btnkonfirmasiPesanan = (Button)view.findViewById(R.id.catat_produk);
        Button btnkembali = (Button)view.findViewById(R.id.kembali);

        final AlertDialog alertDialog=builder.create();

        btnubahPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equalsIgnoreCase("Dicetak")) {
                    Toast.makeText(context, "Pemesanan telah dicetak, tidak dapat diubah!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(context, EditAddProdukActivity.class);
                    i.putExtra("nomor_po",nomor_po);
                    context.startActivity(i);
                }
            }
        });
        
        btnhapusPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equalsIgnoreCase("Dicetak")) {
                    Toast.makeText(context, "Pemesanan telah dicetak, tidak dapat dihapus!", Toast.LENGTH_SHORT).show();
                }else{
                    deleteDetailPemesanan(nomor_po,position);
                }
            }
        });
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnkonfirmasiPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Konfirmasi Pemesanan")
                        .setMessage("Kamu yakin pesanan sudah datang?")
                        .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progDialog();
                                orderCame(nomor_po);
                                waitingResponse();
                            }
                        })
                        .setNegativeButton("Cancel",null);
                builder1.show();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void progDialog() {
        dialog.setMessage("Sedang mengonfirmasi pesanan...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                dialog.dismiss();
            }
        }, 4000);
        Toast.makeText(context, "Pesanan sudah dikonfirmasi, Stok produk sudah bertambah", Toast.LENGTH_SHORT).show();
    }

    private void orderCame(String nomor_po){
        final String url = "http://renzvin.com/kouvee/api/Pemesanan/ordercame";
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Toast.makeText(context, "Sukses Mengkonfirmasi Pesanan", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Gagal Mengkonfirmasi Pesanan", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nomor_po", nomor_po);
                return params;
            }
        };
        dpemesananadapter.notifyDataSetChanged();
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    private void deleteDetailPemesanan(String nomor_po,int position) {
        pemesanan.remove(position);
        final String url = "http://renzvin.com/kouvee/api/Pemesanan/delete";
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Toast.makeText(context, "Sukses Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String format = simpleDateFormat.format(new Date());
                Map<String, String> params = new HashMap<>();
                params.put("nomor_po", nomor_po);
                params.put("deleted_at",format);
                return params;
            }
        };
        dpemesananadapter.notifyDataSetChanged();
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }

    private List<Detail_PemesananDAO> getDetailPemesanan(String nomor_po){
        String url2 = "http://renzvin.com/kouvee/api/pemesanan/detail?nomor_po=";
        RequestQueue queue = Volley.newRequestQueue(context);
        List<Detail_PemesananDAO> detailProduk = new ArrayList<>();
        StringRequest getRequest = new StringRequest(Request.Method.GET, url2+nomor_po,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String pemesanans = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(pemesanans);
                            for(int i = 0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                if (obj.getString("deleted_at").equalsIgnoreCase("null")) {
                                    JSONArray array_detail = obj.getJSONArray("detail_pemesanan");
                                    for(int i2=0;i2<array_detail.length();i2++)
                                    {
                                        JSONObject obj2 = array_detail.getJSONObject(i2);
                                        if(array_detail!=null)
                                        {
                                            Detail_PemesananDAO dpem = new Detail_PemesananDAO(obj2.getInt("produk_id"),obj2.getString("nama"),obj2.getString("satuan"),obj2.getInt("jumlah"));
                                            detailProduk.add(dpem);
                                        }
                                    }
                                }
                            }
                            dpemesananadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
        return detailProduk;
    }

    @Override
    public int getItemCount() {
        return pemesanan.size();
    }

    public void filterList(List<PemesananDAO> filteredList) {
        pemesanan = filteredList;
        notifyDataSetChanged();
    }

    public class PemesananView extends RecyclerView.ViewHolder {
        TextView nomorPO,supplierName,status,dateOrder;
        RecyclerView pemesanan_recycler;
        ScrollView scrollViewParent;
        LinearLayout parent;
        public PemesananView(@NonNull View itemView) {
            super(itemView);
            pemesanan_recycler=(RecyclerView)itemView.findViewById(R.id.pemesanan_produk_recycler);
            nomorPO = (TextView) itemView.findViewById(R.id.pp_nomor_po);
            supplierName = (TextView) itemView.findViewById(R.id.pp_nama_supplier);
            status = (TextView) itemView.findViewById(R.id.pp_status);
            dateOrder = (TextView) itemView.findViewById(R.id.pp_tanggal_pesan);
           scrollViewParent = (ScrollView) itemView.findViewById(R.id.scrollView_parent);
            parent = (LinearLayout)itemView.findViewById(R.id.linearlayout_parent);
        }
    }
}
