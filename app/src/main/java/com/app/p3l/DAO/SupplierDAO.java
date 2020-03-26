package com.app.p3l.DAO;

public class SupplierDAO {
    String nama,alamat,kota,no_hp;
    int id;
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SupplierDAO(String nama, String alamat, String kota, String no_hp, int id) {
        this.nama = nama;
        this.alamat = alamat;
        this.kota = kota;
        this.no_hp = no_hp;
        this.id = id;
    }
}
