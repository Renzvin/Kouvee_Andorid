package com.app.p3l.CRUDActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.DAO.Kategori_ProdukDAO;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;
import com.google.api.LogDescriptor;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProdukActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nama,stock,harga;
    private Spinner kategori;
    private Button gambar,edit;
    private ImageView image;
    private Bitmap bitmap;
    public final static String url = "http://renzvin.com/kouvee/api/produk/update/";
    String kat = "1";
    String status = "0";
    private Handler mHandler = new Handler();
    private final int IMG_REQUEST = 1;
    List<Kategori_ProdukDAO> kategori_produkDAOS = new ArrayList<>();
    List<String> temp = new ArrayList<String>();
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);
        nama = (EditText) findViewById(R.id.edit_P_Nama);
        stock = (EditText) findViewById(R.id.edit_P_Stock);
        harga = (EditText) findViewById(R.id.edit_P_Harga);
        gambar = (Button) findViewById(R.id.edit_P_foto);
        image = (ImageView) findViewById(R.id.edit_imageProduk);
        kategori = (Spinner) findViewById(R.id.edit_spinner_produk);
        dialog = new ProgressDialog(this);
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
        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        nama.setText(getIntent().getStringExtra("Pnama"));
        stock.setText(getIntent().getStringExtra("Pstock"));
        harga.setText(getIntent().getStringExtra("Pharga"));
        new GetImageFromUrl(image).execute(getIntent().getStringExtra("Pgambar"));
        edit = (Button) findViewById(R.id.Pedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create(nama.getText().toString(), stock.getText().toString(), harga.getText().toString(), kat, bitmap);
                progDialog();
                waitingResponse();
            }
        });
        getSupportActionBar().hide();
    }

    private void progDialog() {
        dialog.setMessage("Sedang mengubah data...");
        dialog.show();
    }

    private void waitingResponse() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(status);
                if(status.equalsIgnoreCase("1")) {
                    Toast.makeText(EditProdukActivity.this, "Sukses Mengubah Data Produk", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EditProdukActivity.this, "Gagal Mengubah Data Produk", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        }, 4000);
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
        Toast.makeText(EditProdukActivity.this, "", Toast.LENGTH_SHORT).show();
        if(resultCode == EditProdukActivity.RESULT_OK){
            Uri path = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(EditProdukActivity.this.getContentResolver(),path);
                image.setImageBitmap(getResizedBitmap(bitmap, 1024));
                Toast.makeText(EditProdukActivity.this, "Successfully get image", Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                Toast.makeText(EditProdukActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(EditProdukActivity.this);

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
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditProdukActivity.this, android.R.layout.simple_spinner_item,temp);
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
                        Toast.makeText(EditProdukActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
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
                            status = "1";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProdukActivity.this, "Kesalahan Koneksi", Toast.LENGTH_SHORT).show();
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
                params.put("id",getIntent().getStringExtra("Pid"));
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
                Log.d("Error","Fuck");
                params.put("image", new VolleyMultiPartRequest.DataPart(imagename + ".png", getStringImage(bitmap)));
                return params;
            }
        };

        // adding the request to volley
        Volley.newRequestQueue(EditProdukActivity.this).add(volleyMultipartRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.P_foto:
                selectImage();
                break;
        }
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public GetImageFromUrl(ImageView img){
            this.imageView = img;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}

