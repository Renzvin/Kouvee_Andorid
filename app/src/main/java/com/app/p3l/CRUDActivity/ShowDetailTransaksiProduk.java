package com.app.p3l.CRUDActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.app.p3l.Adapter.DeskripsiTransaksiProdukAdapter;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TemporaryIdPegawai;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDetailTransaksiProduk extends AppCompatActivity {
    private TextView nama,alamat,no_hp,total_harga;
    private Button aksi;
    private RecyclerView dataRecycler;
    private DeskripsiTransaksiProdukAdapter adapter;
    List<ProdukTemp> produkTemps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskirpsi_transaksi_produk);
        nama = (TextView) findViewById(R.id.tvNamaCustomer);
        alamat = (TextView) findViewById(R.id.tvAlamatCustomer);
        no_hp = (TextView) findViewById(R.id.tvnoHP);
        total_harga = (TextView) findViewById(R.id.tvTotal);
        dataRecycler = (RecyclerView) findViewById(R.id.transaksi_produk_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(ShowDetailTransaksiProduk.this);
        adapter = new DeskripsiTransaksiProdukAdapter(ShowDetailTransaksiProduk.this,produkTemps);
        dataRecycler.setLayoutManager(manager);
        dataRecycler.setAdapter(adapter);
        aksi = (Button) findViewById(R.id.btnAksiTransaksiProduk);
        aksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        EditText search = findViewById(R.id.search_bar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        getProduk();
    }

    private void filter(String search){
        List<ProdukTemp> example = new ArrayList<>();
        for(ProdukTemp temp : produkTemps){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        adapter.filterList(example);
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShowDetailTransaksiProduk.this);

        // set title dialog
        alertDialogBuilder.setTitle("Perubahan Data Customer");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Back untuk kembali!")
                .setIcon(R.drawable.paw)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(ShowDetailTransaksiProduk.this,"Belum ada fungsi edit",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete
                        deleteTransaksi();
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

    private void deleteTransaksi() {
        final String url = "http://renzvin.com/kouvee/api/TransaksiProduk/delete";
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Toast.makeText(ShowDetailTransaksiProduk.this, "Sukses Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowDetailTransaksiProduk.this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                Map<String, String> params = new HashMap<>();
                params.put("no_transaksi", getIntent().getStringExtra("no_transaksi"));
                params.put("pegawai_id", Integer.toString(TemporaryIdPegawai.id));
                return params;
            }
        };
        Volley.newRequestQueue(ShowDetailTransaksiProduk.this).add(volleyMultipartRequest);
    }
    private void getProduk() {
        String url = "http://renzvin.com/kouvee/api/TransaksiProduk?no_transaksi="+getIntent().getStringExtra("no_transaksi");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produks = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(produks);
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String item = jsonObject1.getString("produk");
                            JSONArray jsonArray1 = new JSONArray(item);
                            for(int i = 0; i<jsonArray1.length(); i++) {
                                JSONObject obj = jsonArray1.getJSONObject(i);
                                ProdukTemp pro = new ProdukTemp(obj.getString("nama"),
                                        obj.getInt("id"),obj.getInt("jumlah_beli"), obj.getInt("harga"));
                                produkTemps.add(pro);
                            }
                            adapter.notifyDataSetChanged();
                            String customer = jsonObject1.getString("customer");
                            JSONObject jsonobject2 = jsonObject1.getJSONObject("customer");
                            JSONObject object = jsonobject2;
                            nama.setText(object.getString("nama"));
                            alamat.setText(object.getString("alamat"));
                            no_hp.setText(object.getString("no_hp"));
                            String total = jsonObject1.getString("pembayaran");
                            JSONObject jsonObject3 = jsonObject1.getJSONObject("pembayaran");
                            JSONObject object1 = jsonObject3;
                            total_harga.setText(Integer.toString(object1.getInt("total")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShowDetailTransaksiProduk.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
