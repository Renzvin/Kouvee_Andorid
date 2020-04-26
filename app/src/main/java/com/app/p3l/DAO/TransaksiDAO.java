package com.app.p3l.DAO;

public class TransaksiDAO {
    String no_transaksi, tanggal, status;
    int id_transaksi, customer_id, cs_id, pegawai_id, kasir_id;

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public void setNo_transaksi(String no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCs_id() {
        return cs_id;
    }

    public void setCs_id(int cs_id) {
        this.cs_id = cs_id;
    }

    public int getPegawai_id() {
        return pegawai_id;
    }

    public void setPegawai_id(int pegawai_id) {
        this.pegawai_id = pegawai_id;
    }

    public int getKasir_id() {
        return kasir_id;
    }

    public void setKasir_id(int kasir_id) {
        this.kasir_id = kasir_id;
    }

    public TransaksiDAO(String no_transaksi, String tanggal, String status, int id_transaksi, int customer_id, int cs_id, int pegawai_id) {
        this.no_transaksi = no_transaksi;
        this.tanggal = tanggal;
        this.status = status;
        this.id_transaksi = id_transaksi;
        this.customer_id = customer_id;
        this.cs_id = cs_id;
        this.pegawai_id = pegawai_id;
    }
}
