package com.app.p3l.ui.CRUDdata;

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
import com.app.p3l.Adapter.DeleteSupplierAdapter;
import com.app.p3l.Adapter.EditSupplierAdapter;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeleteSupplierFragment extends Fragment {
    private RecyclerView supplierRecycler;
    private DeleteSupplierAdapter supplierAdapter;
    List<SupplierDAO> supplier = new ArrayList<>();

    String data = "-";
    String status = "-";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.recycle, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        supplierRecycler =  (RecyclerView) getView().findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        supplierAdapter = new DeleteSupplierAdapter(supplier, getContext());
        supplierRecycler.setLayoutManager(manager);
        supplierRecycler.setAdapter(supplierAdapter);
        new ItemTouchHelper(supplierMoveCallback).attachToRecyclerView(supplierRecycler);
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
        List<SupplierDAO> example = new ArrayList<>();
        for(SupplierDAO temp : supplier){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        supplierAdapter.filterList(example);
    }

    ItemTouchHelper.SimpleCallback supplierMoveCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int temp = viewHolder.getAdapterPosition();
            supplierAdapter.deleteItem(temp);
            supplierAdapter.notifyDataSetChanged();
        }
    };


    private void getSupplier() {
        String url = "http://renzvin.com/kouvee/api/supplier/";
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
                                    SupplierDAO sup = new SupplierDAO(obj.getString("nama"),
                                            obj.getString("alamat"),obj.getString("kota"), obj.getString("no_hp"),obj.getInt("id"));
                                    supplier.add(sup);
                                }
                            }
                            supplierAdapter.notifyDataSetChanged();
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
