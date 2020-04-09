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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.EditDeleteCustomerAdapter;
import com.app.p3l.Adapter.EditDeleteLayananAdapter;
import com.app.p3l.Adapter.EditDeleteSupplierAdapter;
import com.app.p3l.CRUDActivity.CreateCustomerActivity;
import com.app.p3l.CRUDActivity.CreateLayananActivity;
import com.app.p3l.CRUDActivity.CreateSupplierActivity;
import com.app.p3l.DAO.CustomerDAO;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CRUDCustomerFragment extends Fragment {
    private RecyclerView customerRecycler;
    private EditDeleteCustomerAdapter customerAdapter;
    private FloatingActionButton create;
    List<CustomerDAO> customer = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.floating_customer_recycler, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerRecycler =  (RecyclerView) getView().findViewById(R.id.floating_customer_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        customerAdapter = new EditDeleteCustomerAdapter(customer, getContext());
        customerRecycler.setLayoutManager(manager);
        customerRecycler.setAdapter(customerAdapter);
        create = (FloatingActionButton) getView().findViewById(R.id.create_customer);
        create.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateCustomerActivity.class);
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

        getCustomer();
    }

    private void filter(String search){
        List<CustomerDAO> example = new ArrayList<>();
        for(CustomerDAO temp : customer){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        customerAdapter.filterList(example);
    }

    private void getCustomer() {
        String url = "http://renzvin.com/kouvee/api/customer/";
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
                                    CustomerDAO sup = new CustomerDAO(obj.getString("nama"),
                                            obj.getString("alamat"),obj.getString("no_hp"), obj.getString("tanggal_lahir"),obj.getInt("pegawai_id"),obj.getInt("is_member"),obj.getInt("id"));
                                    customer.add(sup);
                                }
                            }
                            customerAdapter.notifyDataSetChanged();
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
