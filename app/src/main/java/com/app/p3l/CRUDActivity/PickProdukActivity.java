package com.app.p3l.CRUDActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.DeskripsiTransaksiProdukAdapter;
import com.app.p3l.Adapter.PickProdukAdapter;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.ProdukTemp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickProdukActivity extends AppCompatActivity {
    private RecyclerView dataRecycler;
    private PickProdukAdapter adapter;
    List<ProdukDAO> produk = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);
        dataRecycler = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PickProdukActivity.this,3);
        adapter = new PickProdukAdapter(PickProdukActivity.this,produk);
        dataRecycler.setLayoutManager(gridLayoutManager);
        dataRecycler.setAdapter(adapter);
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
        List<ProdukDAO> example = new ArrayList<>();
        for(ProdukDAO temp : produk){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        adapter.filterList(example);
    }

    private void getProduk() {
        String url = "http://renzvin.com/kouvee/api/Produk/";
        RequestQueue queue = Volley.newRequestQueue(PickProdukActivity.this);

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produks = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(produks);
                            for(int i = 0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                if (obj.getString("deleted_at").equalsIgnoreCase("null")) {
                                    ProdukDAO pro = new ProdukDAO(obj.getString("nama"),
                                            obj.getString("link_gambar"),obj.getString("deleted_at"),obj.getString("created_at"),obj.getString("updated_at"),obj.getInt("stock"), obj.getInt("harga"),obj.getInt("kategori_id"),obj.getInt("id"));
                                    produk.add(pro);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PickProdukActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

}
