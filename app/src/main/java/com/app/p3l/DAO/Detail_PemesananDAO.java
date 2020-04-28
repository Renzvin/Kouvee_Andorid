package com.app.p3l.DAO;

import android.os.Parcel;
import android.os.Parcelable;

public class Detail_PemesananDAO {
    String satuan,produk_name,gambar;
    int produk_id,jumlah;

    public Detail_PemesananDAO(){
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getLink_gambar() {
        return gambar;
    }

    public void setLink_gambar(String gambar) {
        this.gambar = gambar;
    }

    public String getProduk_Name() {
        return produk_name;
    }

    public void setProduk_Name(String produk_name) {
        this.produk_name = produk_name;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getProduk_id() {
        return produk_id;
    }

    public void setProduk_id(int produk_id) {
        this.produk_id = produk_id;
    }

    public String toString(){
        return satuan;
    }

    public Detail_PemesananDAO(int produk_id, String produk_name, String satuan,  int jumlah){
        this.produk_id=produk_id;
        this.satuan=satuan;
        this.produk_name=produk_name;
        this.jumlah=jumlah;
    }

}
