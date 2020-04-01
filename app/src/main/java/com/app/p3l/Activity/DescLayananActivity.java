package com.app.p3l.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.p3l.CRUDActivity.EditProdukActivity;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

public class DescLayananActivity extends AppCompatActivity {
    private TextView nama,harga,created,updated,status;
    private ImageView gambar;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_layanan);
        nama = (TextView) findViewById(R.id.desc_l_nama);
        harga = (TextView) findViewById(R.id.desc_l_harga);
        created = (TextView) findViewById(R.id.desc_l_created);
        updated = (TextView) findViewById(R.id.desc_l_edited);
        status = (TextView) findViewById(R.id.desc_l_status);
        gambar = (ImageView) findViewById(R.id.desc_imageLayanan);
        nama.setText(getIntent().getStringExtra("nama"));
        harga.setText(getIntent().getStringExtra("harga"));
        created.setText(getIntent().getStringExtra("created"));
        updated.setText(getIntent().getStringExtra("edited"));
        String edit = getIntent().getStringExtra("edited");
        if(edit.equalsIgnoreCase("null")){
            status.setText("Created");
        } else {
            status.setText("Edited");
        }
        Picasso.get().load(getIntent().getStringExtra("link")).into(gambar);
        getSupportActionBar().hide();
    }
}
