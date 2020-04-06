package com.app.p3l.ui.ukuran_hewan;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.UkuranHewanAdapter;
import com.app.p3l.CRUDActivity.CreateUkuranHewanActivity;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.Ukuran_HewanDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UkuranHewanFragment extends Fragment {

    private RecyclerView ukuranRecycler;
    private UkuranHewanAdapter ukuranAdapter;
    private FloatingActionButton create;
    List<Ukuran_HewanDAO> ukuran = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.floating_ukuran_hewan_recycler, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ukuranRecycler =  (RecyclerView) getView().findViewById(R.id.floating_ukuran_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        ukuranAdapter = new UkuranHewanAdapter(ukuran, getContext());
        ukuranRecycler.setLayoutManager(manager);
        ukuranRecycler.setAdapter(ukuranAdapter);
        new ItemTouchHelper(supplierMoveCallback).attachToRecyclerView(ukuranRecycler);
        create = (FloatingActionButton) getView().findViewById(R.id.create_ukuran_hewan);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateUkuranHewanActivity.class);
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
        getSupplier();
    }

    private void filter(String search){
        List<Ukuran_HewanDAO> example = new ArrayList<>();
        for(Ukuran_HewanDAO temp : ukuran){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        ukuranAdapter.filterList(example);
    }

    ItemTouchHelper.SimpleCallback supplierMoveCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int temp = viewHolder.getAdapterPosition();
            ukuranAdapter.deleteItem(temp);
            ukuranAdapter.notifyDataSetChanged();
        }
    };


    private void getSupplier() {
        String url = "http://renzvin.com/kouvee/api/UkuranHewan/";
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
                                if (obj.getString("deleted_at").equalsIgnoreCase("null")) {
                                    Ukuran_HewanDAO ukr = new Ukuran_HewanDAO(obj.getString("nama"), obj.getInt("id"));
                                    ukuran.add(ukr);
                                }
                            }
                            ukuranAdapter.notifyDataSetChanged();
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
