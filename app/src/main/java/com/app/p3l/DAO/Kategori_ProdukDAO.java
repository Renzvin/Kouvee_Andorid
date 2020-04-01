package com.app.p3l.DAO;

public class Kategori_ProdukDAO {
    String nama;
    int id;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kategori_ProdukDAO(String nama, int id) {
        this.nama = nama;
        this.id = id;
    }
}
