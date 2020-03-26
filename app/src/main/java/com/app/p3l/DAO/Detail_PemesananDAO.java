package com.app.p3l.DAO;

public class Detail_PemesananDAO {
    String nomor_po,satuan;
    int produk_id,jumlah;

    public String getNomor_po() {
        return nomor_po;
    }

    public void setNomor_po(String nomor_po) {
        this.nomor_po = nomor_po;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getProduk_id() {
        return produk_id;
    }

    public void setProduk_id(int produk_id) {
        this.produk_id = produk_id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
