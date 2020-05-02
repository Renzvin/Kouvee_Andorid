package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.AddProdukAdapter;
import com.app.p3l.Adapter.PemesananProdukAdapter;
import com.app.p3l.DAO.Detail_PemesananDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditAddProdukActivity extends AppCompatActivity {

    private LayoutInflater layoutInflater;
    String supp ="1";
    List<SupplierDAO> supplierDAO = new ArrayList<>();
    List<ProdukDAO> produk= new ArrayList<>();
    List<Detail_PemesananDAO> listdetail= new ArrayList<>();
    List<Detail_PemesananDAO> getAllItem= new ArrayList<>();
    List<Detail_PemesananDAO> dataproduknpemesanan= new ArrayList<>();
    List<String> temp = new ArrayList<String>();
    private AddProdukAdapter addProdukAdapter;
    private PemesananProdukAdapter pemesananProdukAdapter;
    private Spinner spinnersupplier;
    private RecyclerView edit_add_produk_recycler,edit_add_pemesanan_produk_recycler;
    private LinearLayout layout;
    private TextView txtNomorPO;
    private Button edit_tambah_produk,edit_tambah_pemesanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_produk);
        Intent intent = getIntent();
        String listed_nomor_po =intent.getStringExtra("nomor_po");
        txtNomorPO = (TextView)findViewById(R.id.edit_add_nomorPO);
        txtNomorPO.setText(listed_nomor_po);

        layout = (LinearLayout)findViewById(R.id.linearlayout_editaddproduk);
        spinnersupplier = (Spinner)findViewById(R.id.spinner_edit_add_produk_supplier);
        setSpinner();
        spinnersupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supp = parent.getItemAtPosition(position).toString();
                for(int count=0;count<supplierDAO.size();count++){
                    if(supp.equalsIgnoreCase(supplierDAO.get(count).getNama())){
                        supp = Integer.toString(supplierDAO.get(count).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edit_tambah_produk= (Button)findViewById(R.id.btn_edittambahproduk);
        edit_tambah_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View dialog = layoutInflater.inflate(R.layout.popup_pemesananproduk,null);
                final PopupWindow pw = new PopupWindow(dialog, 1200, 1800, true);
                getProduk();


                EditText search = dialog.findViewById(R.id.search_bar);
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
                edit_add_produk_recycler =  (RecyclerView)dialog.findViewById(R.id.add_produk_recycler);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
                pemesananProdukAdapter = new PemesananProdukAdapter(produk,getApplicationContext());
                edit_add_produk_recycler.setLayoutManager(gridLayoutManager);
                edit_add_produk_recycler.setAdapter(pemesananProdukAdapter);
                pemesananProdukAdapter.notifyDataSetChanged();
                pemesananProdukAdapter.setOnItemClickListener(new PemesananProdukAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ProdukDAO allitem = produk.get(position);
                        getAllItem = convertItemProduk(allitem);

                        edit_add_pemesanan_produk_recycler = (RecyclerView)findViewById(R.id.edit_add_pemesanan_produk_recycler);
                        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(getApplicationContext());
                        addProdukAdapter = new AddProdukAdapter(getAllItem, getApplicationContext());
                        edit_add_pemesanan_produk_recycler.setLayoutManager(mLayout);
                        edit_add_pemesanan_produk_recycler.setAdapter(addProdukAdapter);

                        dataproduknpemesanan = addProdukAdapter.getAllDataProdukRecycler();
                    }
                });

                pw.showAtLocation(layout, Gravity.CENTER, 0,-50);
            }
        });
        edit_tambah_pemesanan= (Button)findViewById(R.id.btn_edittambahPemesanan);
        edit_tambah_pemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addProdukAdapter.getItemCount()==0)
                {
                    Toast.makeText(EditAddProdukActivity.this, "Jumlah item masih kosong, tidak dapat membuat pesanan", Toast.LENGTH_SHORT).show();
                }else{
                    for (int i=0;i<dataproduknpemesanan.size();i++)
                    {
                        for(int j=0;j<dataproduknpemesanan.size();j++)
                        {
                            if(dataproduknpemesanan.get(i).getProduk_id()==dataproduknpemesanan.get(j).getProduk_id())
                            {
                                dataproduknpemesanan.remove(j);
                            }
                        }
                    }
                    EditPemesanan(listed_nomor_po,supp);
                    dataproduknpemesanan.clear();
                    getAllItem.clear();
                    addProdukAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void filter(String search){
        List<ProdukDAO> example = new ArrayList<>();
        for(ProdukDAO temp : produk){
            if (temp.getNama().toLowerCase().contains(search.toLowerCase())){
                example.add(temp);
            }
        }
        pemesananProdukAdapter.filterList(example);
    }

    private void EditPemesanan(String nomor_po,String supplier_id)
    {
        String url = "http://renzvin.com/kouvee/api/Pemesanan/update";
        RequestQueue queue = Volley.newRequestQueue(this);

        //Create json array for filter
        JSONArray array = new JSONArray();

        //Create json objects for two filter Ids
        JSONObject jsonParam1 = new JSONObject();

        //Add string params
        try {
            jsonParam1.put("nomor_po", nomor_po);
            jsonParam1.put("supplier_id", supplier_id);
            for (int i = 0; i < dataproduknpemesanan.size(); i++) {
                JSONObject produk = new JSONObject();
                produk.put("produk_id", Integer.toString(dataproduknpemesanan.get(i).getProduk_id()));
                produk.put("satuan", dataproduknpemesanan.get(i).getSatuan());
                produk.put("jumlah", Integer.toString(dataproduknpemesanan.get(i).getJumlah()+1));
                array.put(produk);
            }
            jsonParam1.put("produk",array);
        } catch(JSONException e){

        }

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,jsonParam1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(EditAddProdukActivity.this,"Sukses Membuat Data Pesanan", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditAddProdukActivity.this,"Gagal Membuat Data Pesanan", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonobj);
    }

    private List<Detail_PemesananDAO> convertItemProduk(ProdukDAO produkDAO) {
        Detail_PemesananDAO dao = new Detail_PemesananDAO();
        dao.setProduk_Name(produkDAO.getNama());
        dao.setProduk_id(produkDAO.getId());
        dao.setLink_gambar(produkDAO.getLink_gambar());
        listdetail.add(dao);
        return listdetail;
    }

    private void setSpinner() {
        String url = "http://renzvin.com/kouvee/api/supplier/";
        RequestQueue queue = Volley.newRequestQueue(EditAddProdukActivity.this);

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
                                    SupplierDAO sup = new SupplierDAO(obj.getString("nama"),
                                            obj.getString("alamat"),obj.getString("kota"), obj.getString("no_hp"),obj.getInt("id"));
                                    supplierDAO.add(sup);
                                }
                            }
                            for (int i = 0; i < supplierDAO.size(); i++){
                                temp.add(supplierDAO.get(i).getNama().toString());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditAddProdukActivity.this, android.R.layout.simple_spinner_item,temp);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnersupplier.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditAddProdukActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    public void getProduk() {
        String url = "http://renzvin.com/kouvee/api/Produk/";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

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
                                    ProdukDAO pro = new ProdukDAO(obj.getString("nama"),
                                            obj.getString("link_gambar"),obj.getString("deleted_at"),obj.getString("created_at"),obj.getString("updated_at"),obj.getInt("stock"), obj.getInt("harga"),obj.getInt("kategori_id"),obj.getInt("id"));
                                    produk.add(pro);
                                }
                                pemesananProdukAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}
