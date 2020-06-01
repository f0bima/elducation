package com.example.elducation;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import petrov.kristiyan.colorpicker.ColorPicker;


/**
 * A simple {@link Fragment} subclass.
 */
public class basic2 extends Fragment {

    CircleImageView color1, color2, color3, color4, color5;
    TextView[] tv= new TextView[4];
    TextView hasiltv, percent;
    Button switchgelang, cek;
    LinearLayout gelang2;
    boolean gl5 = true;

    int[] gelang = {0,0,0,0,0};

    public basic2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic2, container, false);
        color1 = view.findViewById(R.id.color1);
        color2 = view.findViewById(R.id.color2);
        color3 = view.findViewById(R.id.color3);
        color4 = view.findViewById(R.id.color4);
        color5 = view.findViewById(R.id.color5);
        gelang2 = view.findViewById(R.id.gelang2);
//        tv = new TextView[4];
        tv[0] = view.findViewById(R.id.textg2);
        tv[1] = view.findViewById(R.id.textg3);
        tv[2] = view.findViewById(R.id.textg4);
        tv[3] = view.findViewById(R.id.textg5);
        hasiltv = view.findViewById(R.id.hasil);
        percent = view.findViewById(R.id.percent);
        switchgelang = view.findViewById(R.id.swithgelang);
        cek = view.findViewById(R.id.cek);
       /* color1.setOnClickListener(v -> setcolor(color1,0));
        color2.setOnClickListener(v -> setcolor(color2,1));
        color3.setOnClickListener(v -> setcolor(color3,2));
        color4.setOnClickListener(v -> setcolor(color4,3));
        color5.setOnClickListener(v -> setcolor2(color5));
        switchgelang.setOnClickListener(v -> switchgl());
        cek.setOnClickListener(v -> hitung());*/
        return view;
    }

    private void hitung(){
        int hasil;
        double[] p = {1,2, 0.5, 0.25, 0.1, 0.05, 5, 10, 20};
        if (gl5){
            hasil = gelang[0]*100;
            hasil = hasil + gelang[1]*10;
            hasil = hasil + gelang[2];
            hasil = hasil * (int) Math.pow(10, gelang[3]);
        }
        else{
            hasil = gelang[0]*10;
            hasil = hasil + gelang[2];
            hasil = hasil * (int) Math.pow(10, gelang[3]);
        }
        double percentage = p[gelang[4]];
        hasiltv.setText(Integer.toString(hasil));
        percent.setText(Double.toString(percentage) +"%");

    }

    private void switchgl(){
        if (gl5){
            switchgelang.setText("5 Gelang");
            gelang2.setVisibility(View.GONE);
            for(int i=0; i<3;i++){
                tv[i+1].setText("Gelang "+ Integer.toString(i+2));
            }
        }
        else {
            switchgelang.setText("4 Gelang");
            gelang2.setVisibility(View.VISIBLE);
            for(int i=0; i<4;i++){
                tv[i].setText("Gelang "+ Integer.toString(i+2));
            }
        }
        gl5 = !gl5;
    }

    /*private void setcolor(CircleImageView item, int rb) {
        final ColorPicker colorPicker = new ColorPicker(SimulasiActivity.);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#000000");
        colors.add("#96580D");
        colors.add("#ff0000");
        colors.add("#fe8f09");
        colors.add("#fedb10");
        colors.add("#00ff00");
        colors.add("#0000ff");
        colors.add("#730977");
        colors.add("#666666");
        colors.add("#ffffff");
        colors.add("#c1a90b");
        colors.add("#b3b3b3");

        colorPicker.setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
//                        item.setBackgroundColor(color);
                        item.setImageDrawable(new ColorDrawable(color));
                        gelang[rb] = position;
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    private void setcolor2(CircleImageView item) {
        final ColorPicker colorPicker = new ColorPicker(SimulasiActivity.Ac);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#96580D");
        colors.add("#ff0000");

        colors.add("#00ff00");
        colors.add("#0000ff");
        colors.add("#730977");
        colors.add("#666666");

        colors.add("#c1a90b");
        colors.add("#b3b3b3");
        colors.add("#ffffff");

        colorPicker.setColors(colors)
                .setColumns(4)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
//                        item.setBackgroundColor(color);
                        item.setImageDrawable(new ColorDrawable(color));
                        gelang[4] = position;
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }*/

}
