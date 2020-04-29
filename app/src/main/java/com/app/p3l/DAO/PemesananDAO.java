package com.app.p3l.DAO;

import android.content.Context;

import com.app.p3l.R;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PemesananDAO {
    String nomor_po,supplier_name,status;
    Date tanggal_pesan;
    Date tanggal_masuk;
    int supplier_id,id;

    public static String getAppPath(Context applicationContext) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory()
        +File.separator
                + applicationContext.getResources().getString(R.string.app_name)
                +File.separator
                );
                if(!dir.exists())
                    dir.mkdir();
                return dir.getPath() + File.separator;
    }

    public String getNomor_po() {
        return nomor_po;
    }

    public void setNomor_po(String nomor_po) {
        this.nomor_po = nomor_po;
    }

    public Date getTanggal_pesan() {
        return tanggal_pesan;
    }

    public void setTanggal_pesan(Date tanggal_pesan) {
        this.tanggal_pesan = tanggal_pesan;
    }

    public Date getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(Date tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(int supplier_id) {
        this.supplier_name = supplier_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus(){return status;}

    public void setStatus(String status){this.status=status;}

    public PemesananDAO(int supplier_id, String nomor_po, String supplier_name, Date tanggal_pesan, Date tanggal_masuk, int id,String status) {
        this.supplier_id=supplier_id;
        this.supplier_name=supplier_name;
        this.nomor_po=nomor_po;
        this.tanggal_pesan=tanggal_pesan;
        this.tanggal_masuk=tanggal_masuk;
        this.id=id;
        this.status=status;
    }


}
