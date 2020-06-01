package com.example.elducation;

public class Upload {
    private String name;
    private String imgurl;

    public Upload(){

    }

    public Upload(String name, String imgurl){
        if (name.trim().equals("")){
            name = "No name";
        }
        this.name = name;
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
