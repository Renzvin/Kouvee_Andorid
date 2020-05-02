package com.app.p3l.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
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
import com.app.p3l.Activity.CSActivity;
import com.app.p3l.Activity.LoginActivity;
import com.app.p3l.Activity.MainActivity;
import com.app.p3l.CRUDActivity.EditProdukActivity;
import com.app.p3l.DAO.ProdukDAO;
import com.app.p3l.DAO.SupplierDAO;
import com.app.p3l.Endpoints.VolleyMultiPartRequest;
import com.app.p3l.R;
import com.app.p3l.Temporary.TemporaryRoleId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditDeleteProdukAdapter extends RecyclerView.Adapter<EditDeleteProdukAdapter.ProdukView> {
    List<ProdukDAO> produk;
    private Context context;
    ProgressDialog dialog;

    public EditDeleteProdukAdapter(List<ProdukDAO> produk, Context context) {
        this.produk = produk;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDeleteProdukAdapter.ProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_delete_produk, parent, false);
        final EditDeleteProdukAdapter.ProdukView EditDeleteHolder = new EditDeleteProdukAdapter.ProdukView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteProdukAdapter.ProdukView ProdukView, int position) {
        final ProdukDAO row = produk.get(position);
        int temp = row.getId();
        dialog = new ProgressDialog(context);
        ProdukView.Title.setText(row.getNama());
        ProdukView.Price.setText(Integer.toString(row.getHarga()));
        Picasso.get().load(row.getLink_gambar()).into(ProdukView.Image);
        ProdukView.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(temp,position);
            }
        });

    }

    private void showDialog(int temp,int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title dialog
        alertDialogBuilder.setTitle("Perubahan Data Produk");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Back untuk kembali!")
                .setIcon(R.drawable.paw)
                .setCancelable(false)
                .setPositiveButton("Edit",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Edit
                        editProduk(position);
                    }
                })
                .setNegativeButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Delete
                        deleteProduk(temp,position);
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

    private void editProduk(int position) {
        final ProdukDAO row = produk.get(position);
        Intent intent = new Intent(context, EditProdukActivity.class);
        intent.putExtra("Pnama",row.getNama());
        intent.putExtra("Pstock",Integer.toString(row.getStock()));
        intent.putExtra("Pharga",Integer.toString(row.getHarga()));
        intent.putExtra("Pgambar",row.getLink_gambar());
        intent.putExtra("Pid",Integer.toString(row.getId()));
        context.startActivity(intent);
    }

    private void deleteProduk(int temp,int position) {
        produk.remove(position);
        final String url = "http://renzvin.com/kouvee/api/produk/delete/";
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
                }) {
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


    @Override
    public int getItemCount() {
        return produk.size();
    }

    public void filterList(List<ProdukDAO> filteredList) {
        produk = filteredList;
        notifyDataSetChanged();
    }

    public class ProdukView extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView Title, Price;
        LinearLayout parent;

        public ProdukView(@NonNull View itemView) {
            super(itemView);
            Image = (ImageView) itemView.findViewById(R.id.editdelete_Produk_Image);
            Title = (TextView) itemView.findViewById(R.id.editdelete_Produk_Title);
            Price = (TextView) itemView.findViewById(R.id.editdelete_Produk_Harga);
            parent = (LinearLayout) itemView.findViewById(R.id.editdelete_Produk_Parent);
        }
    }
}
