package com.app.p3l.CRUDActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.app.p3l.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateUkuranHewanActivity extends AppCompatActivity {
    private EditText nama;
    private Button create;

    String status = "-";
    String message = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ukuran_hewan);
        nama = (EditText) findViewById(R.id.create_nama_ukuran);
        create = (Button) findViewById(R.id.ukuran_tambah);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create(nama.getText().toString());
            }
        });
        getSupportActionBar().hide();
    }

    private void create(final String nama){
        final String url = "http://renzvin.com/kouvee/api/UkuranHewan/create/";
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


                            Toast.makeText(CreateUkuranHewanActivity.this,"Sukses Mengubah Data Ukuran",Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreateUkuranHewanActivity.this,"Gagal Mengubah Data Ukuran",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);

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
