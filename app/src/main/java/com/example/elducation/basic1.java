package com.example.elducation;


import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class basic1 extends Fragment {
    int xx;
    String judul, desc;
    ImageView imageView;
    TextView judultv, desctv;

    public basic1(int image, String judul, String desc) {
        xx = image;
        this.judul = judul;
        this.desc = desc;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic1, container, false);
        imageView = view.findViewById(R.id.image_view);
        judultv = view.findViewById(R.id.judul);
        desctv = view.findViewById(R.id.deskripsi);
        judultv.setText(judul);
        desctv.setText(desc);
        imageView.setImageResource(xx);

        return view;
    }

}
