package com.app.p3l.DAO;

public class Jenis_HewanDAO {
    String nama,deleted_at;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Jenis_HewanDAO(String nama, int id) {
        this.nama = nama;
        this.id = id;
    }
}
