package com.example.elducation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class basicAdapter extends FragmentPagerAdapter {
    int count;
    int[] img;
    String[] judul, desc;
    public basicAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                return new basic1(R.drawable.p1);
//            case 1:
//                return new basic1(R.drawable.p2_);
//            case 2:
//                return new basic1(R.drawable.p4);
//            default:
//                return null;
//        }
        return new basic1(img[position], judul[position], desc[position]);
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setcount(int c){
        count = c;
    }

    public void setBasic(int[] arrayImg, String[] judul, String[] desc){
        img = arrayImg;
        this.judul = judul;
        this.desc = desc;
    }
}
