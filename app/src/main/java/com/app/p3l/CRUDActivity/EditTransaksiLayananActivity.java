package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.ListLayananPickAdapter;
import com.app.p3l.R;
import com.app.p3l.Temporary.PickCustomer;
import com.app.p3l.Temporary.PickHewan;
import com.app.p3l.Temporary.TempListPickLayanan;
import com.app.p3l.Temporary.TempMultipleLayanan;
import com.app.p3l.Temporary.TempMultipleProduct;
import com.app.p3l.Temporary.TemporaryIdPegawai;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditTransaksiLayananActivity extends AppCompatActivity {
    ImageButton pilih_customer,pilih_layanan,pilih_hewan;
    BottomSheetBehavior bottomSheetBehavior;
    TempMultipleProduct produk[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaksi_layanan);
        pilih_customer = (ImageButton) findViewById(R.id.button_pilih_customer);
        pilih_layanan = (ImageButton) findViewById(R.id.button_pilih_layanan);

        pilih_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditTransaksiLayananActivity.this,PickCustomerActivity.class);
                startActivity(i);
            }
        });
        pilih_layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PickHewan.tempHewan==null){
                    Toast.makeText(EditTransaksiLayananActivity.this,"Tidak bisa memilih layanan sebelum memilih hewan",Toast.LENGTH_SHORT).show();
                } else{
                    Intent i = new Intent(EditTransaksiLayananActivity.this,PickLayananActivity.class);
                    startActivity(i);
                }

            }
        });

        View bottomSheet = findViewById(R.id.test);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        TextView harga = (TextView) findViewById(R.id.tvTotal);
        TextView nama = (TextView) findViewById(R.id.tvNamaCustomer);
        TextView alamat = (TextView) findViewById(R.id.tvAlamatCustomer);
        TextView no_hp = (TextView) findViewById(R.id.tvnoHP);
        TextView nama_hewan = (TextView) findViewById(R.id.tvnamaHewan);
        TextView jenis_hewan = (TextView) findViewById(R.id.tvjenisHewan);
        if(PickCustomer.tempCustomer!=null){
            nama.setText(PickCustomer.tempCustomer.getNama());
            alamat.setText(PickCustomer.tempCustomer.getAlamat());
            no_hp.setText(PickCustomer.tempCustomer.getNo_hp());
        }
        if(PickHewan.tempHewan!=null){
            nama_hewan.setText(PickHewan.tempHewan.getNama_hewan());
            jenis_hewan.setText(PickHewan.tempHewan.getNama_jenis());
        }
        if(TempListPickLayanan.layananList!=null){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.transaksi_produk_recycler);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(EditTransaksiLayananActivity.this);
            ListLayananPickAdapter adapter = new ListLayananPickAdapter(EditTransaksiLayananActivity.this, TempListPickLayanan.layananList);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            int temp = 0;
            for (int count = 0; count<TempListPickLayanan.layananList.size();count++){
                temp = temp+TempListPickLayanan.layananList.get(count).getHarga();
            }
            harga.setText(Integer.toString(temp));
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
                    List<TempMultipleLayanan> example = new ArrayList<>();
                    for(TempMultipleLayanan temp : TempListPickLayanan.layananList){
                        if (temp.getNama().toLowerCase().contains(s.toString().toLowerCase())){
                            example.add(temp);
                        }
                    }
                    adapter.filterList(example);
                }
            });
        }
        Button asd = (Button) findViewById(R.id.btnCreateTransaksiLayanan);

        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TempListPickLayanan.layananList != null && PickCustomer.tempCustomer != null && PickHewan.tempHewan != null) {
                    try {
                        createTransaksiProduk();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(EditTransaksiLayananActivity.this, "Anda harus memiliki customer, layanan, dan hewan untuk melakukan proses transaksi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().hide();
    }

    private void createTransaksiProduk() throws JSONException {
        String url = "http://renzvin.com/kouvee/api/TransaksiLayanan/update";
        RequestQueue queue = Volley.newRequestQueue(this);

        //Create json array for filter
        JSONArray array = new JSONArray();

        //Create json objects for two filter Ids
        JSONObject jsonParam1 = new JSONObject();

        //Add string params
        try {
            jsonParam1.put("no_transaksi", getIntent().getStringExtra("no_transaksi"));
            jsonParam1.put("pegawai_id", Integer.toString(TemporaryIdPegawai.id));
            JSONObject produk = new JSONObject();
            for (int i = 0; i < TempListPickLayanan.layananList.size(); i++) {
                produk.put("layanan_id", Integer.toString(TempListPickLayanan.layananList.get(i).getId()));
                array.put(produk);
            }
            jsonParam1.put("layanan",array);
        } catch(JSONException e){

        }

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,jsonParam1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(EditTransaksiLayananActivity.this,"Sukses Membuat Data Produk", Toast.LENGTH_SHORT).show();
                        PickCustomer.tempCustomer = null;
                        PickHewan.tempHewan = null;
                        TempListPickLayanan.layananList.clear();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditTransaksiLayananActivity.this,"Gagal Membuat Data Produk", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonobj);
    }
}
