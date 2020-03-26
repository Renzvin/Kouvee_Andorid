package com.app.p3l.ui.layanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.p3l.Adapter.LayananAdapter;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LayananFragment extends Fragment {

    private RecyclerView dataRecycler;
    private LayananAdapter layananAdapter;

    String data = "-";
    String status = "-";



    List<LayananDAO> layanan = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.recycle, container, false);
        dataRecycler =  (RecyclerView) v.findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
        layananAdapter = new LayananAdapter(getActivity().getApplicationContext(), layanan);
        dataRecycler.setLayoutManager(gridLayoutManager);
        dataRecycler.setAdapter(layananAdapter);

        getProduk();
        return v;
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

                            status = jsonObject.getString("status");
                            data = jsonObject.getString("data");
                            System.out.println("Response : " + status);
                            System.out.println("Message  : " + data);


                            String layanans = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(layanans);

                            for(int i = 0; i<jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                if(obj.getString("deleted_at").equalsIgnoreCase("null")){
                                    LayananDAO lay = new LayananDAO(obj.getString("nama"),
                                            obj.getString("link_gambar"), obj.getInt("harga"),obj.getInt("id"));
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
