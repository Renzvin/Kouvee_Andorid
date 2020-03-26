package com.app.p3l.ui.jenis_hewan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.p3l.Adapter.JenisHewanAdapter;
import com.app.p3l.CRUDActivity.CreateJenisHewanActivity;
import com.app.p3l.CRUDActivity.CreateUkuranHewanActivity;
import com.app.p3l.DAO.Jenis_HewanDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JenisHewanFragment extends Fragment {
    private RecyclerView jenisRecycler;
    private JenisHewanAdapter jenisAdapter;
    private FloatingActionButton create;
    List<Jenis_HewanDAO> jenis = new ArrayList<>();

    String data = "-";
    String status = "-";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.floating_jenis_hewan_recycler, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jenisRecycler =  (RecyclerView) getView().findViewById(R.id.floating_jenis_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        jenisAdapter = new JenisHewanAdapter(jenis, getContext());
        jenisRecycler.setLayoutManager(manager);
        jenisRecycler.setAdapter(jenisAdapter);
        create = (FloatingActionButton) getView().findViewById(R.id.create_jenis_hewan);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateJenisHewanActivity.class);
                startActivity(intent);
            }
        });
        new ItemTouchHelper(supplierMoveCallback).attachToRecyclerView(jenisRecycler);
        getSupplier();
    }

    ItemTouchHelper.SimpleCallback supplierMoveCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int temp = viewHolder.getAdapterPosition();
            jenisAdapter.deleteItem(temp);
            jenisAdapter.notifyDataSetChanged();
        }
    };


    private void getSupplier() {
        String url = "http://renzvin.com/kouvee/api/JenisHewan/";
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
                                    Jenis_HewanDAO jen = new Jenis_HewanDAO(obj.getString("nama"), obj.getInt("id"));
                                    jenis.add(jen);
                                }
                            }
                            jenisAdapter.notifyDataSetChanged();
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
