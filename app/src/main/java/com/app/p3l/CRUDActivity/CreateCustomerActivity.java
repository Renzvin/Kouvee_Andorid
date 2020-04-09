package com.app.p3l.CRUDActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.p3l.Adapter.EditDeleteCustomerAdapter;
import com.app.p3l.R;
import com.app.p3l.Temporary.TemporaryRoleId;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateCustomerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText nama,alamat,no_hp;
    private TextView tanggal;
    private Button create,date;
    String temporary;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);
        nama = (EditText)findViewById(R.id.C_nama);
        date = (Button) findViewById(R.id.create_date_customer);
        alamat = (EditText)findViewById(R.id.C_alamat);
        no_hp = (EditText)findViewById(R.id.C_NoHP);
        create = (Button)findViewById(R.id.Cadd);
        tanggal = (TextView)findViewById(R.id.create_tanggal_lahir);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.app.p3l.Temporary.DatePicker();
                datePicker.show(getSupportFragmentManager(), "tanggal");
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCustomer(nama.getText().toString(),temporary,alamat.getText().toString(),no_hp.getText().toString());
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String dateformat = DateFormat.getInstance().format(c.getTime());
        temporary = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth);
        tanggal.setText(temporary);

    }

    private void createCustomer(final String nama,final String tanggal_lahir,final String alamat,final String no_hp) {
        String url = "http://renzvin.com/kouvee/api/customer/create";
        RequestQueue queue = Volley.newRequestQueue(CreateCustomerActivity.this);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(CreateCustomerActivity.this,"Sukses Mendaftar Customer",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreateCustomerActivity.this,"Gagal Mendaftar Customer",Toast.LENGTH_SHORT).show();
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
