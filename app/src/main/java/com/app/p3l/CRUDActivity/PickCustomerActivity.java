package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.EditDeleteCustomerAdapter;
import com.app.p3l.Adapter.PickCustomerAdapter;
import com.app.p3l.DAO.CustomerDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickCustomerActivity extends AppCompatActivity {
    private RecyclerView customerRecycler;
    private PickCustomerAdapter customerAdapter;
    private FloatingActionButton create;
    List<CustomerDAO> customer = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_customer_recycler);
        customerRecycler =  (RecyclerView) findViewById(R.id.floating_customer_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(PickCustomerActivity.this);
        customerAdapter = new PickCustomerAdapter( PickCustomerActivity.this,customer);
        customerRecycler.setLayoutManager(manager);
        customerRecycler.setAdapter(customerAdapter);
        create = (FloatingActionButton) findViewById(R.id.create_customer);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickCustomerActivity.this, CreateCustomerActivity.class);
                startActivity(intent);
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

        getCustomer();
    }

    private void filter(String search){
        List<CustomerDAO> example = new ArrayList<>();
        for(CustomerDAO temp : customer){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        customerAdapter.filterList(example);
    }

    private void getCustomer() {
        String url = "http://renzvin.com/kouvee/api/customer/";
        RequestQueue queue = Volley.newRequestQueue(PickCustomerActivity.this);

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
                                    CustomerDAO sup = new CustomerDAO(obj.getString("nama"),
                                            obj.getString("alamat"),obj.getString("no_hp"), obj.getString("tanggal_lahir"),obj.getInt("pegawai_id"),obj.getInt("is_member"),obj.getInt("id"));
                                    customer.add(sup);
                                }
                            }
                            customerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PickCustomerActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
