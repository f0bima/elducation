package com.example.elducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

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

public class CardListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Materi_Adapter materi_adapter;
    private GridLayoutManager gridLayoutManager;
    private List<Materi_model> materi_models;
    Toolbar toolbar;
    FloatingActionButton floatingbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        ActionBar actionBar = getSupportActionBar();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("judul"));
        floatingbtn = findViewById(R.id.floatingbtn);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLayout();
                switchIcon();
            }
        });

        byte[] mBytes = getIntent().getByteArrayExtra("img");

        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference("materi");
        materi_models = new ArrayList<>(4);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                materi_models.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String judul = postSnapshot.child("judul").getValue(String.class);
                    materi_models.add(new Materi_model(judul, "", R.drawable.p4));
                    Log.e("Get Data", judul.toString());
                }
                materi_adapter.notifyItemRangeChanged(0, materi_adapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_TWO);
        materi_adapter = new Materi_Adapter(this,materi_models, gridLayoutManager);
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
