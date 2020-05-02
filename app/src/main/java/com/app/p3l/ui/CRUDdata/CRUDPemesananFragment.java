package com.app.p3l.ui.CRUDdata;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
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
import com.app.p3l.Adapter.EditDeletePemesananAdapter;
import com.app.p3l.CRUDActivity.CreateAddProdukActivity;
import com.app.p3l.DAO.PemesananDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CRUDPemesananFragment extends Fragment {
    private RecyclerView pemesanan_recycler;
    private EditDeletePemesananAdapter pemesananproAdapter;
    private FloatingActionButton more;
    List <PemesananDAO> pemesananDAOS = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.pemesanan_recycler, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pemesanan_recycler =  (RecyclerView) getView().findViewById(R.id.pemesanan_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        pemesananproAdapter = new EditDeletePemesananAdapter(pemesananDAOS, getContext());
        pemesanan_recycler.setLayoutManager(manager);
        pemesanan_recycler.setAdapter(pemesananproAdapter);

        more = (FloatingActionButton) getView().findViewById(R.id.more);

        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateAddProdukActivity.class);
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

        getPemesanan();
    }

    private void filter(String search){
        List<PemesananDAO> example = new ArrayList<>();
        for(PemesananDAO temp : pemesananDAOS){
            if (temp.getNomor_po().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        pemesananproAdapter.filterList(example);
    }

    private void getPemesanan() {
        String url = "http://renzvin.com/kouvee/api/pemesanan/";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date_order = obj.getString("tanggal_pesan");
                                Date date_orderr = sdf.parse(date_order);
                                if (obj.getString("deleted_at").equalsIgnoreCase("null")) {
                                    PemesananDAO pem = new PemesananDAO(obj.getInt("supplier_id"),
                                            obj.getString("nomor_po"),date_orderr,obj.getString("supplier_name"),obj.getInt("id"),obj.getString("status"));
                                    pemesananDAOS.add(pem);
                                }
                            }
                            pemesananproAdapter.notifyDataSetChanged();
                        } catch (JSONException | ParseException e) {
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
