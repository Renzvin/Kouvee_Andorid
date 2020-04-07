package com.app.p3l.CRUDActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateSupplierActivity extends AppCompatActivity {
    private EditText nama,alamat,kota,no_hp;
    private Button create;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_supplier);
        nama = (EditText) findViewById(R.id.S_Nama);
        alamat = (EditText) findViewById(R.id.S_Alamat);
        kota = (EditText) findViewById(R.id.S_Kota);
        no_hp = (EditText) findViewById(R.id.S_NoHP);
        create = (Button) findViewById(R.id.Sadd);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateSupplier(nama.getText().toString(),alamat.getText().toString(),kota.getText().toString(),no_hp.getText().toString());
            }
        });
    }

    private void CreateSupplier(final String nama, final String alamat, final String kota, final String no_hp) {
        final String url = "http://renzvin.com/kouvee/api/supplier/create/";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),"Sukses Mendaftar Supplier",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Gagal Mendaftar Supplier",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("alamat", alamat);
                params.put("kota", kota);
                params.put("no_hp", no_hp);

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
}
