package com.example.elducation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.elducation.Materi_Adapter.SPAN_COUNT_ONE;
import static com.example.elducation.Materi_Adapter.SPAN_COUNT_TWO;

public class BasicMoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Materi_Adapter materi_adapter;
    private GridLayoutManager gridLayoutManager;
    private List<Materi_model> materi_models;
    Toolbar toolbar;
    FloatingActionButton floatingbtn;
    ImageView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        header = findViewById(R.id.header);
        header.setImageResource(R.drawable.back3);
        ActionBar actionBar = getSupportActionBar();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Materi Dasar");
        floatingbtn = findViewById(R.id.floatingbtn);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout();
                switchIcon();
            }
        });

        byte[] mBytes = getIntent().getByteArrayExtra("img");

        materi_models = new ArrayList<>(4);
        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_TWO);
        materi_adapter = new Materi_Adapter(this,materi_models, gridLayoutManager);

        materi_models.add(new Materi_model("Resistor", "Ini Resistor", R.drawable.resistor_png));
        materi_models.add(new Materi_model("Capacitor", "Ini Capacitor", R.drawable.capacitor));
        materi_models.add(new Materi_model("Induktor", "Ini Induktor", R.drawable.inductor));
        materi_models.add(new Materi_model("IC", "Ini IC", R.drawable.ic));
        materi_adapter.notifyItemRangeChanged(0, materi_adapter.getItemCount());


        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setAdapter(materi_adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setPadding(10,0,10,0);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_TWO);
        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
        }
        materi_adapter.notifyItemRangeChanged(0, materi_adapter.getItemCount());
    }

    private void switchIcon() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_TWO) {
            floatingbtn.setImageResource(R.drawable.ic_span_3);
        } else {
            floatingbtn.setImageResource(R.drawable.ic_span_1);
        }
    }
}
