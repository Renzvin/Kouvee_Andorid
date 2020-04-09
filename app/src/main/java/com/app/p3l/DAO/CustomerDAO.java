package com.app.p3l.DAO;

import java.util.Date;

public class CustomerDAO {
    String nama,alamat, no_hp,tanggal_lahir;
    int pegawai_id,is_member,id;

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

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public int getIs_member() {
        return is_member;
    }

    public void setIs_member(int is_member) {
        this.is_member = is_member;
    }

    public int getPegawai_id() {
        return pegawai_id;
    }

    public void setPegawai_id(int pegawai_id) {
        this.pegawai_id = pegawai_id;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerDAO(String nama, String alamat, String no_hp, String tanggal_lahir, int pegawai_id, int is_member, int id) {
        this.nama = nama;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.tanggal_lahir = tanggal_lahir;
        this.pegawai_id = pegawai_id;
        this.is_member = is_member;
        this.id = id;
    }
}
