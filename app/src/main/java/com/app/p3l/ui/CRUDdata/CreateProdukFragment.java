package com.app.p3l.ui.CRUDdata;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Activity.CSActivity;
import com.app.p3l.Activity.DescProdukActivity;
import com.app.p3l.Activity.LoginActivity;
import com.app.p3l.Activity.MainActivity;
import com.app.p3l.DAO.Kategori_ProdukDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.firebase.ui.auth.AuthUI.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class CreateProdukFragment extends Fragment implements  View.OnClickListener{
    private EditText nama,stock,harga;
    private Spinner kategori;
    private Button gambar,tambah;
    private ImageView image;
    private Bitmap bitmap;
    String kat = "1";
    private final int IMG_REQUEST = 1;
    public final static String url = "http://renzvin.com/kouvee/api/produk/create/";
    List<Kategori_ProdukDAO> kategori_produkDAOS = new ArrayList<>();
    List<String> temp = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View View =  inflater.inflate(R.layout.fragment_create_produk, container, false);
        return View;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nama = (EditText) getView().findViewById(R.id.P_Nama);
        stock = (EditText) getView().findViewById(R.id.P_Stock);
        harga = (EditText) getView().findViewById(R.id.P_Harga);
        kategori = (Spinner) getView().findViewById(R.id.spinner_produk);
        setSpinner();
        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kat = parent.getItemAtPosition(position).toString();
                for(int count=0;count<kategori_produkDAOS.size();count++){
                    if(kat.equalsIgnoreCase(kategori_produkDAOS.get(count).getNama())){
                        kat = Integer.toString(kategori_produkDAOS.get(count).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        image = (ImageView) getView().findViewById(R.id.imageProduk);
        gambar = (Button) getView().findViewById(R.id.P_foto);
        gambar.setOnClickListener(this);
        tambah = (Button) getView().findViewById(R.id.btnTambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create(nama.getText().toString(), stock.getText().toString(), harga.getText().toString(), kat, bitmap);
            }
        });
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),IMG_REQUEST);
    }

    public byte[] getStringImage(Bitmap bmp){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        if(resultCode == getActivity().RESULT_OK){
            Uri path = data.getData();
            try{
                Toast.makeText(getContext(), path.toString(), Toast.LENGTH_SHORT).show();
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),path);
                image.setImageBitmap(getResizedBitmap(bitmap, 1024));
                Toast.makeText(getActivity().getApplicationContext(), "Successfully get image", Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void setSpinner(){
        String url = "http://renzvin.com/kouvee/api/KategoriProduk/";
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
                                Kategori_ProdukDAO pro = new Kategori_ProdukDAO(obj.getString("nama"), obj.getInt("id"));
                                kategori_produkDAOS.add(pro);
                            }
                            for (int i = 0; i < kategori_produkDAOS.size(); i++){
                                temp.add(kategori_produkDAOS.get(i).getNama().toString());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item,temp);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            kategori.setAdapter(spinnerArrayAdapter);
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

    private void Create(final String nama, final String stock, final String harga, final String kategori,final Bitmap bitmap) {

        //our custom volley request
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getActivity().getApplicationContext(), "Sukses Membuat Data", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Membuat Data", Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("stock", stock);
                params.put("harga", harga);
                params.put("kategori", kategori);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, VolleyMultiPartRequest.DataPart> getByteData() {
                Map<String, VolleyMultiPartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new VolleyMultiPartRequest.DataPart(imagename + ".png", getStringImage(bitmap)));
                return params;
            }
        };

        // adding the request to volley
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(volleyMultipartRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.P_foto:
                selectImage();
                break;
        }
    }

}