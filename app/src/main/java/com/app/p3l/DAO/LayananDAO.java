package com.app.p3l.DAO;

public class LayananDAO {
    String nama,link_gambar,deleted_at,created_at,edited_at;
    int harga,id ;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEdited_at() {
        return edited_at;
    }

    public void setEdited_at(String edited_at) {
        this.edited_at = edited_at;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLink_gambar() {
        return link_gambar;
    }

    public void setLink_gambar(String link_gambar) {
        this.link_gambar = link_gambar;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LayananDAO(String nama, String link_gambar, String created_at, String edited_at, int harga, int id) {
        this.nama = nama;
        this.link_gambar = link_gambar;
        this.created_at = created_at;
        this.edited_at = edited_at;
        this.harga = harga;
        this.id = id;
    }
}
