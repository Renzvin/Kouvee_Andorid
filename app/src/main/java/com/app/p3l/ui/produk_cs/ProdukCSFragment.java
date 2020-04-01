package com.app.p3l.ui.produk_cs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.ProdukAdapter;
import com.app.p3l.Adapter.ProdukCSAdapter;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProdukCSFragment extends Fragment {
    private RecyclerView produkRecycler;
    private ProdukCSAdapter produkAdapter;

    String data = "-";
    String status = "-";



    List<ProdukDAO> produk = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.recycle, container, false);
        produkRecycler =  (RecyclerView) v.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),4);
        produkAdapter = new ProdukCSAdapter(getContext(), produk);
        produkRecycler.setLayoutManager(gridLayoutManager);
        produkRecycler.setAdapter(produkAdapter);

        getProduk();
        return v;
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
