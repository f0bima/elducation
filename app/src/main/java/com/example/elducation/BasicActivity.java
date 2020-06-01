package com.example.elducation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BasicActivity extends AppCompatActivity {
    ViewPager viewPager;
    String[] judul,desc;
    int img[] = {};
    TextView indicator;
    int indicatorlength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.vp);
        indicator = findViewById(R.id.indicator);
        String intent = getIntent().getStringExtra("judul");
        Toast toast = Toast.makeText(BasicActivity.this, intent, Toast.LENGTH_SHORT);
        toast.show();

        basicAdapter adapter = new basicAdapter(getSupportFragmentManager());

        getitem(intent);

        String x = "";
        for (int i=0; i<indicatorlength; i++){
            if (i==0){
                x = x + "\u25CF";
            }
            else{
                x = x+ "\u25CB";
            }
        }
        indicator.setText(x);
        adapter.setcount(judul.length);
        adapter.setBasic(img, judul, desc);

        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                String x = "";
                for (int i=0; i<indicatorlength; i++){
                    if (position==i){
                        x = x + "\u25CF";
                    }
                    else{
                        x = x+ "\u25CB";
                    }
                }

                indicator.setText(x);
            }
        });
    }

    private void getitem(String component){
        Resources res = getResources();
        switch (component){
            case "Resistor":
                img = new int[]{R.drawable.resistor_png, R.drawable.ohm, R.drawable.r, R.drawable.vr};
                judul = res.getStringArray(R.array.resistor_judul);
                desc = res.getStringArray(R.array.resistor_desc);
                break;
            case "Capacitor":
                img = new int[]{R.drawable.capacitor, R.drawable.faradsym, R.drawable.elco, R.drawable.nonpolar};
                judul = res.getStringArray(R.array.capacitor_judul);
                desc = res.getStringArray(R.array.capacitor_desc);
                break;
            case "Induktor":
                img = new int[]{R.drawable.inductor, R.drawable.in1, R.drawable.in2, R.drawable.airin, R.drawable.ferritein, R.drawable.ironin, R.drawable.vrin};
                judul = res.getStringArray(R.array.inductor_judul);
                desc = res.getStringArray(R.array.inductor_desc);
                break;
        }

        indicatorlength = judul.length;
    }
}
