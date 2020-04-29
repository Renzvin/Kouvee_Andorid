package com.app.p3l.DAO;

import java.util.Date;

public class PemesananDAO {
    String nomor_po;
    Date tanggal_pesan,tanggal_masuk;
    int supplier_id;

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
}
