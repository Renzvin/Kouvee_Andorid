package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.p3l.CRUDActivity.EditLayananActivity;
import com.app.p3l.DAO.LayananDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditDeleteLayananAdapter extends RecyclerView.Adapter<EditDeleteLayananAdapter.LayananView> {
    List<LayananDAO> layanan ;
    private Context context;
    ProgressDialog dialog;

    public EditDeleteLayananAdapter(List<LayananDAO> layanan,Context context) {
        this.layanan = layanan;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteLayananAdapter.LayananView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_delete_layanan,parent,false);
        final EditDeleteLayananAdapter.LayananView EditDeleteHolder = new EditDeleteLayananAdapter.LayananView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteLayananAdapter.LayananView LayananView, int position) {
        final LayananDAO row = layanan.get(position);
        int temp = row.getId();
        dialog = new ProgressDialog(context);
        LayananView.Title.setText(row.getNama());
        LayananView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(LayananView.Image);
        LayananView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(temp,position);
            }
        });
    }

    private void showDialog(int temp,int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Perubahan Data Layanan");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Back untuk kembali!")
                .setIcon(R.drawable.paw)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Edit
                        editLayanan(position);
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete
                        deleteLayanan(temp,position);
                    }
                })
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void deleteLayanan(int temp, int position) {
        final String url = "http://renzvin.com/kouvee/api/layanan/delete/";
        VolleyMultiPartRequest volleyMultipartRequest = new VolleyMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Sukses Menghapus Data", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context.getApplicationContext(), "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(temp));
                params.put("deleted_at", format);
                return params;
            }
        };
        Volley.newRequestQueue(context.getApplicationContext()).add(volleyMultipartRequest);
    }

    private void editLayanan(int position) {
        final LayananDAO row = layanan.get(position);
        Intent intent = new Intent(context, EditLayananActivity.class);
        intent.putExtra("Lnama",row.getNama());
        intent.putExtra("Lharga",Integer.toString(row.getHarga()));
        intent.putExtra("Lgambar",row.getLink_gambar());
        intent.putExtra("Lid",Integer.toString(row.getId()));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return layanan.size();
    }

    public void filterList(List<LayananDAO> filteredList){
        layanan = filteredList;
        notifyDataSetChanged();
    }

    public class LayananView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title,Price;
        LinearLayout parent;
        public LayananView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView)itemView.findViewById(R.id.editdelete_Layanan_Image);
            Title = (TextView)itemView.findViewById(R.id.editdelete_Layanan_Title);
            Price = (TextView)itemView.findViewById(R.id.editdelete_Layanan_Harga);
            parent = (LinearLayout)itemView.findViewById(R.id.editdelete_Layanan_Parent);
        }
    }
}
