package com.example.elducation;

public class Materi_model {
    private String judul, deskripsi;
    private int img;

    public Materi_model(String judul, String deskripsi, int img){
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.img = img;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
