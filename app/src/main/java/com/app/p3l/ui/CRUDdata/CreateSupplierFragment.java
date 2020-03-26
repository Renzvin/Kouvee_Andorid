package com.app.p3l.ui.CRUDdata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class CreateSupplierFragment extends Fragment {
    private EditText nama,alamat,kota,no_hp;
    private Button create;


    String status = "-";
    String message = "-";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View =  inflater.inflate(R.layout.fragment_create_supplier, container, false);
        return View;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nama = (EditText) getView().findViewById(R.id.S_Nama);
        alamat = (EditText) getView().findViewById(R.id.S_Alamat);
        kota = (EditText) getView().findViewById(R.id.S_Kota);
        no_hp = (EditText) getView().findViewById(R.id.S_NoHP);
        create = (Button) getView().findViewById(R.id.Sadd);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateSupplier(nama.getText().toString(),alamat.getText().toString(),kota.getText().toString(),no_hp.getText().toString());
            }
        });
    }

    private void CreateSupplier(final String nama, final String alamat, final String kota, final String no_hp) {
        final String url = "http://renzvin.com/kouvee/api/supplier/create/";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
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

                            Toast.makeText(getActivity().getApplicationContext(),"Sukses Mendaftar Supplier",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"Gagal Mendaftar Supplier",Toast.LENGTH_SHORT).show();
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
