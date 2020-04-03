package com.app.p3l.DAO;

import java.util.Date;

public class HewanDAO {
    String nama,tanggal_lahir;
    int ukuran_id,jenis_id,pegawai_id,id;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public int getUkuran_id() {
        return ukuran_id;
    }

    public void setUkuran_id(int ukuran_id) {
        this.ukuran_id = ukuran_id;
    }

    public int getJenis_id() {
        return jenis_id;
    }

    public void setJenis_id(int jenis_id) {
        this.jenis_id = jenis_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPegawai_id() {
        return pegawai_id;
    }

    public void setPegawai_id(int pegawai_id) {
        this.pegawai_id = pegawai_id;
    }

    public HewanDAO(String nama, String tanggal_lahir, int ukuran_id, int jenis_id, int pegawai_id,int id) {
        this.nama = nama;
        this.tanggal_lahir = tanggal_lahir;
        this.ukuran_id = ukuran_id;
        this.jenis_id = jenis_id;
        this.pegawai_id = pegawai_id;
        this.id = id;
    }
}
