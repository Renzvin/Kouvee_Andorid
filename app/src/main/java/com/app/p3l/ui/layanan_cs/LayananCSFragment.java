package com.app.p3l.ui.layanan_cs;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.LayananCSAdapter;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LayananCSFragment extends Fragment {
    private RecyclerView dataRecycler;
    private LayananCSAdapter layananAdapter;
    List<LayananDAO> layanan = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.recycle, container, false);
        dataRecycler =  (RecyclerView) v.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        layananAdapter = new LayananCSAdapter(getContext(), layanan);
        dataRecycler.setLayoutManager(gridLayoutManager);
        dataRecycler.setAdapter(layananAdapter);
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
        List<LayananDAO> example = new ArrayList<>();
        for(LayananDAO temp : layanan){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        layananAdapter.filterList(example);
    }

    private void getProduk() {
        String url = "http://renzvin.com/kouvee/api/layanan";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String layanans = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(layanans);
                            for(int i = 0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                if(obj.getString("deleted_at").equalsIgnoreCase("null")){
                                    LayananDAO lay = new LayananDAO(obj.getString("nama"),
                                            obj.getString("link_gambar"), obj.getString("created_at"), obj.getString("updated_at"), obj.getInt("harga"),obj.getInt("id"));
                                    layanan.add(lay);
                                }
                            }
                            layananAdapter.notifyDataSetChanged();
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
