package com.app.p3l.Temporary;

public class ProdukTemp {
    String nama;
    int id_produk,jumlah,harga;

    public int getId() {
        return id_produk;
    }

    public void setId(int id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public ProdukTemp(String nama, int id_produk, int jumlah, int harga) {
        this.nama = nama;
        this.id_produk = id_produk;
        this.jumlah = jumlah;
        this.harga = harga;
    }
}
