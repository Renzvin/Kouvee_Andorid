package com.app.p3l.Temporary;

public class TempCustomer {
String nama,alamat,no_hp;
int id_customer;

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

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public TempCustomer(String nama, String alamat, String no_hp, int id_customer) {
        this.nama = nama;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.id_customer = id_customer;
    }
}
