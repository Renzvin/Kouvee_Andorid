package com.app.p3l.DAO;

import java.util.Date;

public class HewanDAO {
    String nama;
    int ukuran_id,jenis_id,pegawai_id;
    Date tanggal_lahir;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public int getPegawai_id() {
        return pegawai_id;
    }

    public void setPegawai_id(int pegawai_id) {
        this.pegawai_id = pegawai_id;
    }

    public Date getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(Date tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }
}
