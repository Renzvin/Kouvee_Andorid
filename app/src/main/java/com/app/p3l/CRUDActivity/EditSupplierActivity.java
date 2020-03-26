package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Activity.CSActivity;
import com.app.p3l.Activity.LoginActivity;
import com.app.p3l.Activity.MainActivity;
import com.app.p3l.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditSupplierActivity extends AppCompatActivity {
    private EditText nama,alamat,kota,no_hp;
    private Button edit;

    String status = "-";
    String message = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_supplier);
        getSupportActionBar().hide();
        nama = (EditText) findViewById(R.id.S_edit_Nama);
        alamat = (EditText) findViewById(R.id.S_edit_Alamat);
        kota = (EditText) findViewById(R.id.S_edit_Kota);
        no_hp = (EditText) findViewById(R.id.S_edit_NoHP);

        nama.setText(getIntent().getStringExtra("Snama"));
        alamat.setText(getIntent().getStringExtra("Salamat"));
        kota.setText(getIntent().getStringExtra("Skota"));
        no_hp.setText(getIntent().getStringExtra("SnoHP"));

        edit = (Button) findViewById(R.id.S_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(nama.getText().toString(),alamat.getText().toString(),kota.getText().toString(),no_hp.getText().toString());
            }
        });
        getSupportActionBar().hide();
    }

    private void edit(final String nama, final String alamat, final String kota, final String no_hp) {
        final String url = "http://renzvin.com/kouvee/api/supplier/update/";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            status = jsonObject.getString("status");
                            message = jsonObject.getString("message");

                            System.out.println("Response : " + status);
                            System.out.println("Message  : " + message);


                            Toast.makeText(EditSupplierActivity.this,"Sukses Mengubah Data Supplier",Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditSupplierActivity.this,"Gagal Mengubah Data Supplier",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",getIntent().getStringExtra("Sid"));
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
