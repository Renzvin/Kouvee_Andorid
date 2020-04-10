package com.app.p3l.CRUDActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.R;
import com.app.p3l.Temporary.TemporaryRoleId;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditCustomerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText nama,alamat,no_hp;
    private TextView tanggal;
    private Button edit,date;
    String temporary;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        nama = (EditText)findViewById(R.id.C_edit_nama);
        date = (Button) findViewById(R.id.edit_date_customer);
        alamat = (EditText)findViewById(R.id.C_edit_alamat);
        no_hp = (EditText)findViewById(R.id.C_edit_NoHP);
        edit = (Button)findViewById(R.id.C_edit);
        tanggal = (TextView)findViewById(R.id.edit_tanggal_lahir);
        nama.setText(getIntent().getStringExtra("Cnama"));
        tanggal.setText(getIntent().getStringExtra("Ctanggal"));
        alamat.setText(getIntent().getStringExtra("Calamat"));
        no_hp.setText(getIntent().getStringExtra("CnoHP"));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.app.p3l.Temporary.DatePicker();
                datePicker.show(getSupportFragmentManager(), "tanggal");
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCustomer(nama.getText().toString(),temporary,alamat.getText().toString(),no_hp.getText().toString());
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        temporary = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth);
        tanggal.setText(temporary);

    }

    private void editCustomer(final String nama,final String tanggal_lahir,final String alamat,final String no_hp) {
        String url = "http://renzvin.com/kouvee/api/customer/update";
        RequestQueue queue = Volley.newRequestQueue(EditCustomerActivity.this);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(EditCustomerActivity.this,"Sukses Mengubah Customer",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditCustomerActivity.this,"Gagal Mengubah Customer",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                int is_member = 1;
                if(nama.equalsIgnoreCase("Guest")){
                    is_member = 0;
                }
                params.put("nama", nama);
                params.put("tanggal_lahir", tanggal_lahir);
                params.put("alamat", alamat);
                params.put("no_hp", no_hp);
                params.put("pegawai_id", Integer.toString(TemporaryRoleId.id));
                params.put("is_member", Integer.toString(is_member));
                params.put("id",getIntent().getStringExtra("Cid"));

                return params;
            }
        };
        post.setRetryPolicy(
                new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        queue.add(post);
    }
}
