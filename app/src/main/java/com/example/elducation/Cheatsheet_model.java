package com.example.elducation;

public class Cheatsheet_model {
    private String judul;
    private int img;

    public Cheatsheet_model(String judul, int img){
        this.judul = judul;
        this.img = img;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
