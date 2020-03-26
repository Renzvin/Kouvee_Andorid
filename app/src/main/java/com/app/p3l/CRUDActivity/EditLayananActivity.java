package com.app.p3l.CRUDActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditLayananActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText nama,harga;
    private Button gambar,edit;
    private ImageView image;
    private Bitmap bitmap;

    public final static String url = "http://renzvin.com/kouvee/api/layanan/update/";

    String status = "-";
    String message = "-";
    String kat = "1";

    private final int IMG_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_layanan);
        nama = (EditText) findViewById(R.id.edit_L_Nama);
        harga = (EditText) findViewById(R.id.edit_L_Harga);
        gambar = (Button) findViewById(R.id.edit_L_foto);
        image = (ImageView) findViewById(R.id.edit_imageLayanan);
        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        nama.setText(getIntent().getStringExtra("Lnama"));
        harga.setText(getIntent().getStringExtra("Lharga"));
        Picasso.get().load(getIntent().getStringExtra("Lgambar")).into(image);
        edit = (Button) findViewById(R.id.Ledit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create(nama.getText().toString(), harga.getText().toString(), bitmap);
            }
        });
        getSupportActionBar().hide();
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
        Toast.makeText(EditLayananActivity.this, "", Toast.LENGTH_SHORT).show();
        if(resultCode == EditProdukActivity.RESULT_OK){
            Uri path = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(EditLayananActivity.this.getContentResolver(),path);
                image.setImageBitmap(getResizedBitmap(bitmap, 1024));
                Toast.makeText(EditLayananActivity.this, "Successfully get image", Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                Toast.makeText(EditLayananActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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

    private void Create(final String nama, final String harga,final Bitmap bitmap) {


        //our custom volley request
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(EditLayananActivity.this, "Sukses Mengubah Data", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditLayananActivity.this, "Gagal Mengubah Data", Toast.LENGTH_SHORT).show();
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
                params.put("id",getIntent().getStringExtra("Lid"));
                params.put("nama", nama);
                params.put("harga", harga);
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
        Volley.newRequestQueue(EditLayananActivity.this).add(volleyMultipartRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.L_foto:
                selectImage();
                break;
        }
    }
}
