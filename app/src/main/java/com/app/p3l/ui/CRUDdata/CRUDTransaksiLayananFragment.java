package com.app.p3l.ui.CRUDdata;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.EditDeleteTransaksiLayananAdapter;
import com.app.p3l.Adapter.EditDeleteTransaksiProdukAdapter;
import com.app.p3l.CRUDActivity.CreateTransaksiLayananActivity;
import com.app.p3l.DAO.TransaksiDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CRUDTransaksiLayananFragment extends Fragment {
    private RecyclerView transaksiProdukRecycler;
    private EditDeleteTransaksiLayananAdapter transaksiProdukAdapter;
    private FloatingActionButton create;
    List<TransaksiDAO> transaksi_DAOS = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.floating_transaksi, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        transaksiProdukRecycler =  (RecyclerView) getView().findViewById(R.id.floating_transaksi_produk_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        transaksiProdukAdapter = new EditDeleteTransaksiLayananAdapter(transaksi_DAOS, getContext());
        transaksiProdukRecycler.setLayoutManager(manager);
        transaksiProdukRecycler.setAdapter(transaksiProdukAdapter);
        create = (FloatingActionButton) getView().findViewById(R.id.create_transaksi_produk);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateTransaksiLayananActivity.class);
                startActivity(intent);
            }
        });
        EditText search = getView().findViewById(R.id.search_bar);
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

        getTransaksi();
    }

    private void filter(String search){
        List<TransaksiDAO> example = new ArrayList<>();
        for(TransaksiDAO temp : transaksi_DAOS){
            if (temp.getNo_transaksi().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        transaksiProdukAdapter.filterList(example);
    }

    private void getTransaksi() {
        String url = "http://renzvin.com/kouvee/api/TransaksiLayanan";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produks = jsonObject.getString("data");
                            System.out.println(produks);
                            JSONArray jsonArray = new JSONArray(produks);
                            for(int i = 0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                TransaksiDAO trans = new TransaksiDAO(obj.getString("no_transaksi"),obj.getString("tanggal"),obj.getString("status"),obj.getInt("id"),obj.getInt("customer_id"),obj.getInt("cs_id"),obj.getInt("pegawai_id"));
                                transaksi_DAOS.add(trans);
                            }
                            transaksiProdukAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
