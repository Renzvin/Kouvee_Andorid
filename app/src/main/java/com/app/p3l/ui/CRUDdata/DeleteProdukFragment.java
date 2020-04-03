package com.app.p3l.ui.CRUDdata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.DeleteProdukAdapter;
import com.app.p3l.Adapter.ProdukAdapter;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DeleteProdukFragment extends Fragment {
    private RecyclerView produkRecycler;
    private DeleteProdukAdapter produkAdapter;

    String data = "-";
    String status = "-";



    List<ProdukDAO> produk = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.recycle, container, false);
        produkRecycler =  (RecyclerView) v.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
        produkAdapter = new DeleteProdukAdapter(produk,getActivity().getApplicationContext());
        produkRecycler.setLayoutManager(gridLayoutManager);
        produkRecycler.setAdapter(produkAdapter);
        EditText search = v.findViewById(R.id.search_bar);
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
        return v;
    }

    private void filter(String search){
        List<ProdukDAO> example = new ArrayList<>();
        for(ProdukDAO temp : produk){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        produkAdapter.filterList(example);
    }

    private void getProduk() {
        String url = "http://renzvin.com/kouvee/api/produk/";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            status = jsonObject.getString("status");
                            data = jsonObject.getString("data");
                            System.out.println("Response : " + status);
                            System.out.println("Message  : " + data);


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

                            produkAdapter.notifyDataSetChanged();
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

