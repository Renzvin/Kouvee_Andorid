package com.app.p3l.Activity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.DAO.Kategori_ProdukDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescProdukActivity extends AppCompatActivity {
    TextView nama,stock,harga,kategori_produk,status,created,edited;
    ImageView gambar;
    String message = "-";
    String data = "-";
    String kate = "-";
    List<Kategori_ProdukDAO> kateegori = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_produk);
        nama = (TextView) findViewById(R.id.desc_p_nama);
        stock = (TextView) findViewById(R.id.desc_p_stock);
        harga = (TextView) findViewById(R.id.desc_p_harga);
        kategori_produk = (TextView) findViewById(R.id.desc_p_kategori);
        status = (TextView) findViewById(R.id.desc_p_status);
        created = (TextView) findViewById(R.id.desc_p_created);
        edited = (TextView) findViewById(R.id.desc_p_edited);
        gambar = (ImageView) findViewById(R.id.desc_imageProduk);
        nama.setText(getIntent().getStringExtra("nama"));
        stock.setText(getIntent().getStringExtra("stock"));
        harga.setText(getIntent().getStringExtra("harga"));
        created.setText(getIntent().getStringExtra("created"));
        edited.setText(getIntent().getStringExtra("edited"));
        String delete =getIntent().getStringExtra("deleted");
        String edit = getIntent().getStringExtra("edited");
        if(delete.equalsIgnoreCase("null") && edit.equalsIgnoreCase("null")){
            status.setText("Created");
        } else if(delete.equalsIgnoreCase("null")&& edit != "null"){
            status.setText("Edited");
        } else {
            status.setText("Deleted");
        }
        Picasso.get().load(getIntent().getStringExtra("link")).into(gambar);
        getKategori();
        getSupportActionBar().hide();
    }

    private void getKategori() {
        String url = "http://renzvin.com/kouvee/api/KategoriProduk/";
        RequestQueue queue = Volley.newRequestQueue(DescProdukActivity.this);

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            data = jsonObject.getString("data");
                            System.out.println("Message  : " + data);


                            String kategori = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(kategori);

                            for(int i = 0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Kategori_ProdukDAO pro = new Kategori_ProdukDAO(obj.getString("nama"), obj.getInt("id"));
                                kateegori.add(pro);
                            }
                            int temp = Integer.parseInt(getIntent().getStringExtra("kategori"));
                            for(int count=0;count<kateegori.size();count++){
                                if(temp == kateegori.get(count).getId()){
                                    kate = kateegori.get(count).getNama();
                                }
                            }
                            kategori_produk.setText(kate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DescProdukActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
