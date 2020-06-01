package com.example.elducation;

public class Project_model {
    private String judul, deskripsi, id, video, img;
//    private int img;

    public Project_model(String id, String judul, String deskripsi, String img, String video){
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.img = img;
        this.video = video;
    }

    public String getId(){return id;}
    public void setId(String id) {
        this.judul = id;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getVideo(){return video;}

    public void setVideo(String video){this.video = video;}

}
