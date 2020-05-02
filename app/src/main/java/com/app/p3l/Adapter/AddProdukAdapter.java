package com.app.p3l.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.p3l.DAO.Detail_PemesananDAO;
import com.app.p3l.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddProdukAdapter extends RecyclerView.Adapter<AddProdukAdapter.AddProdukView>{
    List<Detail_PemesananDAO> detail_pemesananDAOS ;
    List<Detail_PemesananDAO> list_storedItem = new ArrayList<>();
    private int counter[] = new int[20];
    private Context context;
    private String satuan;

    public AddProdukAdapter(List<Detail_PemesananDAO> detail_pemesananDAOS, Context context) {
        this.detail_pemesananDAOS = detail_pemesananDAOS;
        this.context = context;
    }

    @NonNull
    @Override
    public AddProdukAdapter.AddProdukView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_add_produk,parent,false);
        final AddProdukAdapter.AddProdukView EditDeleteHolder = new AddProdukAdapter.AddProdukView(view);
        return EditDeleteHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddProdukAdapter.AddProdukView holder, int position) {
        final Detail_PemesananDAO row = detail_pemesananDAOS.get(position);
        holder.name.setText(row.getProduk_Name());
        counter[position]=0;
        Picasso.get().load(row.getLink_gambar()).into(holder.produk_Image);
        holder.jumlah.setText(String.valueOf(counter[position]));
        holder.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter[position]++;
                holder.jumlah.setText(String.valueOf(counter[position]));
                Log.d("Tambah", String.valueOf(counter[position]));
            }
        });
        holder.btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter[position]--;
                Log.d("Kurang", String.valueOf(counter[position]));
                if(counter[position] <=-1)
                {
                    detail_pemesananDAOS.remove(position);
                    notifyItemRemoved(position);
                }else{
                    holder.jumlah.setText(String.valueOf(counter[position]));
                }
            }
        });
        holder.spinnerSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               satuan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter[position]=-1;
                detail_pemesananDAOS.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String jumlah = s.toString();
                if(jumlah== null || jumlah.equals("")){
                    counter[position]=0;
                }else{
                    counter[position]=Integer.parseInt(jumlah);
                    storeProduk(row.getProduk_id(),counter[position],holder.spinnerSatuan.getSelectedItem().toString());
                }
            }
        });
    }

    private void storeProduk(Integer produk_id,Integer jumlah, String satuan) {
        Detail_PemesananDAO dao = new Detail_PemesananDAO();
        dao.setProduk_id(produk_id);
        dao.setJumlah(jumlah);
        dao.setSatuan(satuan);
        list_storedItem.add(dao);
    }

    public  List<Detail_PemesananDAO> getAllDataProdukRecycler(){
        return list_storedItem;
    }

    @Override
    public int getItemCount() {
        return detail_pemesananDAOS.size();
    }

    public void filterList(List<Detail_PemesananDAO> filteredList){
        detail_pemesananDAOS = filteredList;
        notifyDataSetChanged();
    }

    public class AddProdukView extends RecyclerView.ViewHolder {
        TextView name;
        EditText jumlah;
        ImageView produk_Image,btnReset;
        Button btnKurang,btnTambah;
        Spinner spinnerSatuan;
        LinearLayout parent;
        public AddProdukView(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.add_produk_namaProduk);
            jumlah = (EditText) itemView.findViewById(R.id.add_produk_jumlah);
            produk_Image = (ImageView) itemView.findViewById(R.id.add_produk_gambar);
            btnReset = (ImageView)itemView.findViewById(R.id.add_produk_reset);
            btnKurang = (Button)itemView.findViewById(R.id.add_produk_kurang);
            btnTambah = (Button)itemView.findViewById(R.id.add_produk_tambah);
            spinnerSatuan = (Spinner)itemView.findViewById(R.id.add_produk_Satuan);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.satuan,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSatuan.setAdapter(adapter);
            spinnerSatuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String satuan = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
}
