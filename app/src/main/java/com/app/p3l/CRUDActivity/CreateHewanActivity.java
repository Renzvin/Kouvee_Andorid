package com.app.p3l.CRUDActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.app.p3l.DAO.Jenis_HewanDAO;
import com.app.p3l.DAO.Ukuran_HewanDAO;
import com.app.p3l.R;
import com.app.p3l.Temporary.TemporaryRoleId;
import com.app.p3l.ui.data.DataFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateHewanActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText nama;
    private TextView tanggal;
    private Spinner spinnerjenis,spinnerukuran;
    private Button create,date;
    List<Jenis_HewanDAO> jenis = new ArrayList<>();
    List<String> temp = new ArrayList<String>();
    List<Ukuran_HewanDAO> ukuran = new ArrayList<>();
    List<String> temp2 = new ArrayList<String>();

    String status = "-";
    String message = "-";
    String data = "-";
    String kat1 = "1", kat2 = "1";
    String temporary = "9999-12-31";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hewan);
        nama = (EditText) findViewById(R.id.create_nama_hewan);
        tanggal = (TextView) findViewById(R.id.create_tanggal_lahir);
        spinnerjenis = (Spinner) findViewById(R.id.spinner_jenis);
        spinnerukuran = (Spinner) findViewById(R.id.spinner_ukuran);
        date = (Button) findViewById(R.id.create_date);
        create = (Button) findViewById(R.id.hewan_tambah);
        getJenis();
        getUkuran();
        spinnerukuran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kat1 = parent.getItemAtPosition(position).toString();
                for(int count=0;count<ukuran.size();count++){
                    if(kat1.equalsIgnoreCase(ukuran.get(count).getNama())){
                        kat1 = Integer.toString(ukuran.get(count).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerjenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kat2 = parent.getItemAtPosition(position).toString();
                for(int count=0;count<jenis.size();count++){
                    if(kat2.equalsIgnoreCase(jenis.get(count).getNama())){
                        kat2 = Integer.toString(jenis.get(count).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                createHewan(nama.getText().toString(),temporary,kat2,kat1,Integer.toString(TemporaryRoleId.id));
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
        tanggal.setText(dateformat);
        temporary = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth);
    }

    private void getJenis() {
        String url = "http://renzvin.com/kouvee/api/JenisHewan/";
        RequestQueue queue = Volley.newRequestQueue(CreateHewanActivity.this);

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
                            for (int i = 0; i < jenis.size(); i++){
                                temp.add(jenis.get(i).getNama().toString());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CreateHewanActivity.this, android.R.layout.simple_spinner_item,temp);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerjenis.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateHewanActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    private void getUkuran() {
        String url = "http://renzvin.com/kouvee/api/UkuranHewan/";
        RequestQueue queue = Volley.newRequestQueue(CreateHewanActivity.this);

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
                                    Ukuran_HewanDAO ukr = new Ukuran_HewanDAO(obj.getString("nama"), obj.getInt("id"));
                                    ukuran.add(ukr);
                                }
                            }
                            for (int i = 0; i < ukuran.size(); i++){
                                temp2.add(ukuran.get(i).getNama().toString());
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CreateHewanActivity.this, android.R.layout.simple_spinner_item,temp2);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerukuran.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateHewanActivity.this,"Gagal Fetch Data",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    private void createHewan(final String nama,final String tanggal_lahir,final String jenis,final String ukuran, final String pegawai) {
        String url = "http://renzvin.com/kouvee/api/hewan/create";
        RequestQueue queue = Volley.newRequestQueue(CreateHewanActivity.this);
        StringRequest post = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            status = jsonObject.getString("status");
                            message = jsonObject.getString("message");

                            System.out.println("Response : " + status);
                            System.out.println("Message  : " + message);

                            Toast.makeText(CreateHewanActivity.this,"Sukses Mendaftar Hewan",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CreateHewanActivity.this,"Gagal Mendaftar Hewan",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("tanggal_lahir", tanggal_lahir);
                params.put("jenis_id", jenis);
                params.put("ukuran_id", ukuran);
                params.put("pegawai_id", pegawai);

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
