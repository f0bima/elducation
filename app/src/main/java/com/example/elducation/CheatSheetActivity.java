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

public class CheatSheetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Cheatsheet_Adapter cheatsheet_adapter;
    private GridLayoutManager gridLayoutManager;
    private List<Cheatsheet_model> cheatsheet_models;
    Toolbar toolbar;
    FloatingActionButton floatingbtn;
    ImageView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        header = findViewById(R.id.header);
        header.setImageResource(R.drawable.back2);
        ActionBar actionBar = getSupportActionBar();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cheat Sheet");
        floatingbtn = findViewById(R.id.floatingbtn);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout();
                switchIcon();
            }
        });

        byte[] mBytes = getIntent().getByteArrayExtra("img");

        /*DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference("materi");*/
        cheatsheet_models = new ArrayList<>(4);

        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_TWO);
        cheatsheet_adapter = new Cheatsheet_Adapter(this,cheatsheet_models, gridLayoutManager);

        cheatsheet_models.add(new Cheatsheet_model("Resistor Tetap", R.drawable.rlogo));
        cheatsheet_models.add(new Cheatsheet_model("Resistor variable", R.drawable.vrlogo));

        cheatsheet_models.add(new Cheatsheet_model("Capacitor Polar", R.drawable.cpolar));
        cheatsheet_models.add(new Cheatsheet_model("Capacitor Non-Polar", R.drawable.clogo));
        cheatsheet_models.add(new Cheatsheet_model("Capacitor Variable", R.drawable.cvrlogo));

        cheatsheet_models.add(new Cheatsheet_model("Dioda", R.drawable.dlogo));

        cheatsheet_models.add(new Cheatsheet_model("Air core Inductor", R.drawable.airin));
        cheatsheet_models.add(new Cheatsheet_model("Ferrit core Inductor", R.drawable.ferritein));
        cheatsheet_models.add(new Cheatsheet_model("Iron core Inductor", R.drawable.ironin));
        cheatsheet_models.add(new Cheatsheet_model("Variable core Inductor", R.drawable.vrin));

        cheatsheet_adapter.notifyItemRangeChanged(0, cheatsheet_adapter.getItemCount());

        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setAdapter(cheatsheet_adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setPadding(10,0,10,0);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_TWO);
        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
        }
        cheatsheet_adapter.notifyItemRangeChanged(0, cheatsheet_adapter.getItemCount());
    }

    private void switchIcon() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_TWO) {
            floatingbtn.setImageResource(R.drawable.ic_span_3);
        } else {
            floatingbtn.setImageResource(R.drawable.ic_span_1);
        }
    }
}
