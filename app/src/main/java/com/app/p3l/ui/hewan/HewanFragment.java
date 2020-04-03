package com.app.p3l.ui.hewan;

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
import com.app.p3l.Adapter.HewanAdapter;
import com.app.p3l.DAO.HewanDAO;
import com.app.p3l.R;
import com.app.p3l.CRUDActivity.CreateHewanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HewanFragment extends Fragment {
    private RecyclerView hewanRecycler;
    private HewanAdapter hewanAdapter;
    private FloatingActionButton create;
    List<HewanDAO> hewan = new ArrayList<>();

    String data = "-";
    String status = "-";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.floating_hewan_recycler, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hewanRecycler =  (RecyclerView) getView().findViewById(R.id.floating_hewan_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        hewanAdapter = new HewanAdapter(hewan, getContext());
        hewanRecycler.setLayoutManager(manager);
        hewanRecycler.setAdapter(hewanAdapter);
        create = (FloatingActionButton) getView().findViewById(R.id.create_hewan);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateHewanActivity.class);
                startActivity(intent);
            }
        });
        new ItemTouchHelper(supplierMoveCallback).attachToRecyclerView(hewanRecycler);
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

    ItemTouchHelper.SimpleCallback supplierMoveCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int temp = viewHolder.getAdapterPosition();
            hewanAdapter.deleteItem(temp);
            hewanAdapter.notifyDataSetChanged();
        }
    };


    private void getHewan() {
        String url = "http://renzvin.com/kouvee/api/Hewan/";
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
                                    HewanDAO jen = new HewanDAO(obj.getString("nama"),obj.getString("tanggal_lahir"),obj.getInt("ukuran_id"),obj.getInt("jenis_id"), obj.getInt("pegawai_id"), obj.getInt("id"));
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
                        Toast.makeText(getActivity().getApplicationContext(),"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
