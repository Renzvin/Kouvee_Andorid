package com.app.p3l.Temporary;

public class TempMultipleLayanan {
    int id, harga;
    String nama;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public TempMultipleLayanan(int id, int harga, String nama) {
        this.id = id;
        this.harga = harga;
        this.nama = nama;
    }
}
