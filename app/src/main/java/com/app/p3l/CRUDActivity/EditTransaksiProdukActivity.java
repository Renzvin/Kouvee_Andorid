package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.app.p3l.Adapter.ListProdukPickAdapter;
import com.app.p3l.R;
import com.app.p3l.Temporary.PickCustomer;
import com.app.p3l.Temporary.ProdukTemp;
import com.app.p3l.Temporary.TempListPickProduk;
import com.app.p3l.Temporary.TempMultipleProduct;
import com.app.p3l.Temporary.TemporaryIdPegawai;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditTransaksiProdukActivity extends AppCompatActivity {
    ImageButton pilih_customer,pilih_produk;
    BottomSheetBehavior bottomSheetBehavior;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaksi_produk);
        pilih_produk = (ImageButton) findViewById(R.id.button_pilih_produk);

        pilih_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditTransaksiProdukActivity.this,PickProdukActivity.class);
                startActivity(i);
            }
        });
        View bottomSheet = findViewById(R.id.test);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        TextView harga = (TextView) findViewById(R.id.tvTotal);
        TextView nama = (TextView) findViewById(R.id.tvNamaCustomer);
        TextView alamat = (TextView) findViewById(R.id.tvAlamatCustomer);
        TextView no_hp = (TextView) findViewById(R.id.tvnoHP);
        if(PickCustomer.tempCustomer!=null){
            nama.setText(PickCustomer.tempCustomer.getNama());
            alamat.setText(PickCustomer.tempCustomer.getAlamat());
            no_hp.setText(PickCustomer.tempCustomer.getNo_hp());
        }
        if(TempListPickProduk.produkTemp!=null){
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.transaksi_produk_recycler);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(EditTransaksiProdukActivity.this);
            ListProdukPickAdapter adapter = new ListProdukPickAdapter(EditTransaksiProdukActivity.this, TempListPickProduk.produkTemp);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            int temp = 0;
            for (int count = 0; count<TempListPickProduk.produkTemp.size();count++){
                temp = temp+TempListPickProduk.produkTemp.get(count).getHarga();
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
                    List<ProdukTemp> example = new ArrayList<>();
                    for(ProdukTemp temp : TempListPickProduk.produkTemp){
                        if (temp.getNama().toLowerCase().contains(s.toString().toLowerCase())){
                            example.add(temp);
                        }
                    }
                    adapter.filterList(example);
                }
            });
        }
        Button asd = (Button) findViewById(R.id.btnUbahTransaksiProduk);
        asd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createTransaksiProduk();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        getSupportActionBar().hide();
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        mHandler.postDelayed(mRunnable, 2000);
        finish();
    }

    private void createTransaksiProduk() throws JSONException {
        String url = "http://renzvin.com/kouvee/api/TransaksiProduk/update";
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
            for (int i = 0; i < TempListPickProduk.produkTemp.size(); i++) {
                produk.put("produk_id", Integer.toString(TempListPickProduk.produkTemp.get(i).getId()));
                produk.put("jumlah", Integer.toString(TempListPickProduk.produkTemp.get(i).getJumlah()));
                array.put(produk);
            }
            jsonParam1.put("produk",array);
        } catch(JSONException e){

        }

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,jsonParam1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(EditTransaksiProdukActivity.this,"Sukses Membuat Data Produk", Toast.LENGTH_SHORT).show();
                        PickCustomer.tempCustomer = null;
                        TempListPickProduk.produkTemp.clear();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditTransaksiProdukActivity.this,"Gagal Membuat Data Produk", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonobj);
    }
}
