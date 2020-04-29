package com.app.p3l.Temporary;

public class TempHewan {
    int id_hewan,id_jenis,id_ukuran;
    String nama_hewan,nama_jenis,nama_ukuran;

    public int getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(int id_hewan) {
        this.id_hewan = id_hewan;
    }

    public int getId_jenis() {
        return id_jenis;
    }

    public void setId_jenis(int id_jenis) {
        this.id_jenis = id_jenis;
    }

    public int getId_ukuran() {
        return id_ukuran;
    }

    public void setId_ukuran(int id_ukuran) {
        this.id_ukuran = id_ukuran;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getNama_jenis() {
        return nama_jenis;
    }

    public void setNama_jenis(String nama_jenis) {
        this.nama_jenis = nama_jenis;
    }

    public String getNama_ukuran() {
        return nama_ukuran;
    }

    public void setNama_ukuran(String nama_ukuran) {
        this.nama_ukuran = nama_ukuran;
    }

    public TempHewan(int id_hewan, int id_jenis, int id_ukuran, String nama_hewan, String nama_jenis, String nama_ukuran) {
        this.id_hewan = id_hewan;
        this.id_jenis = id_jenis;
        this.id_ukuran = id_ukuran;
        this.nama_hewan = nama_hewan;
        this.nama_jenis = nama_jenis;
        this.nama_ukuran = nama_ukuran;
    }
}
