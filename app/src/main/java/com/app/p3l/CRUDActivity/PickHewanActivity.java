package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.HewanAdapter;
import com.app.p3l.Adapter.PickHewanAdapter;
import com.app.p3l.DAO.HewanDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickHewanActivity extends AppCompatActivity {
    private RecyclerView hewanRecycler;
    private PickHewanAdapter hewanAdapter;
    private FloatingActionButton create;
    List<HewanDAO> hewan = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_hewan_recycler);
        hewanRecycler =  (RecyclerView) findViewById(R.id.floating_hewan_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(PickHewanActivity.this);
        hewanAdapter = new PickHewanAdapter(hewan, PickHewanActivity.this);
        hewanRecycler.setLayoutManager(manager);
        hewanRecycler.setAdapter(hewanAdapter);
        create = (FloatingActionButton) findViewById(R.id.create_hewan);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PickHewanActivity.this, CreateHewanActivity.class);
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
        getHewan();
    }

    private void filter(String search){
        List<HewanDAO> example = new ArrayList<>();
        for(HewanDAO temp : hewan){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        hewanAdapter.filterList(example);
    }

    private void getHewan() {
        String url = "http://renzvin.com/kouvee/api/Hewan/";
        RequestQueue queue = Volley.newRequestQueue(PickHewanActivity.this);

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
                                    HewanDAO jen = new HewanDAO(obj.getString("nama"),obj.getString("tanggal_lahir"),obj.getString("jenis_hewan"),obj.getString("ukuran_hewan"),obj.getInt("ukuran_id"),obj.getInt("jenis_id"), obj.getInt("pegawai_id"), obj.getInt("id"));
                                    hewan.add(jen);
                                }
                            }
                            hewanAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PickHewanActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
