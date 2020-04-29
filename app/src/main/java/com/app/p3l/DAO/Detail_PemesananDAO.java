package com.app.p3l.DAO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Detail_PemesananDAO implements Parcelable {
    String satuan,produk_name,gambar;
    int produk_id,jumlah;

    public Detail_PemesananDAO(){
    }

    protected Detail_PemesananDAO(Parcel in) {
        satuan = in.readString();
        produk_name = in.readString();
        produk_id = in.readInt();
        jumlah = in.readInt();
    }

    public static final Creator<Detail_PemesananDAO> CREATOR = new Creator<Detail_PemesananDAO>() {
        @Override
        public Detail_PemesananDAO createFromParcel(Parcel in) {
            return new Detail_PemesananDAO(in);
        }

        @Override
        public Detail_PemesananDAO[] newArray(int size) {
            return new Detail_PemesananDAO[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(satuan);
        dest.writeString(produk_name);
        dest.writeString(gambar);
        dest.writeInt(produk_id);
        dest.writeInt(jumlah);
    }
}
