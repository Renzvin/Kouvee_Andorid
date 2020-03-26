package com.app.p3l.DAO;

public class Detail_Transaksi_LayananDAO {
    String no_transaksi;
    int hewan_id,layanan_id,pegawai_id;

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public void setNo_transaksi(String no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public int getHewan_id() {
        return hewan_id;
    }

    public void setHewan_id(int hewan_id) {
        this.hewan_id = hewan_id;
    }

    public int getLayanan_id() {
        return layanan_id;
    }

    public void setLayanan_id(int layanan_id) {
        this.layanan_id = layanan_id;
    }

    public int getPegawai_id() {
        return pegawai_id;
    }

    public void setPegawai_id(int pegawai_id) {
        this.pegawai_id = pegawai_id;
    }
}
