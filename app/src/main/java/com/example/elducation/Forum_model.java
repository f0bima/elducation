package com.example.elducation;

import java.util.Comparator;

public class Forum_model {
    private String id,judul, pertanyaan, img, publisher, nama, imgpub;
    long tgl;
    private boolean status;
//    private int img;

    public Forum_model(String id ,String nama, String publisher, String imgpub, String judul, String pertanyaan, String img, long tgl, Boolean status){
        this.id = id;
        this.nama = nama;
        this.publisher = publisher;
        this.imgpub = imgpub;
        this.judul = judul;
        this.pertanyaan = pertanyaan;
        this.img = img;
        this.tgl = tgl;
        this.status = status;
    }

    public String getId(){return id;}
    public void setId(String id){ this.id = id;}
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan= pertanyaan;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getTgl(){return tgl;}

    public void setTgl(long tgl){this.tgl = tgl;}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImgpub() {
        return imgpub;
    }

    public void setImgpub(String imgpub) {
        this.imgpub = imgpub;
    }

    public static Comparator<Forum_model> bytimedesc = new Comparator<Forum_model>() {
        @Override
        public int compare(Forum_model o1, Forum_model o2) {
            return - Long.valueOf(o1.tgl).compareTo(Long.valueOf(o2.tgl));
        }
    };

    public static Comparator<Forum_model> bytimeasc = new Comparator<Forum_model>() {
        @Override
        public int compare(Forum_model o1, Forum_model o2) {
            return Long.valueOf(o1.tgl).compareTo(Long.valueOf(o2.tgl));
        }
    };

}
