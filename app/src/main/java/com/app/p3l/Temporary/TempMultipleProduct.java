package com.app.p3l.Temporary;

public class TempMultipleProduct {
    int produk_id,jumlah;

    public int getId() {
        return produk_id;
    }

    public void setId(int produk_id) {
        this.produk_id = produk_id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public TempMultipleProduct(int produk_id, int jumlah) {
        this.produk_id = produk_id;
        this.jumlah = jumlah;
    }
}
