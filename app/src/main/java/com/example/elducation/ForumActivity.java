package com.example.elducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static com.example.elducation.Materi_Adapter.SPAN_COUNT_ONE;
import static com.example.elducation.Materi_Adapter.SPAN_COUNT_TWO;

public class ForumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Forum_Adapter forum_adapter;
    boolean timeasc = false;
    private GridLayoutManager gridLayoutManager;
    private List<Forum_model> forum_models;
    Toolbar toolbar;
    FloatingActionButton floatingbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        EditText search = findViewById(R.id.search);
        FabSpeedDial fabSpeedDial = findViewById(R.id.floatingbtn2);
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                String x = menuItem.getTitle().toString();
                switch(x){
                    case "Up":
                        search.isFocused();
                        NestedScrollView ns;
                        ns= findViewById(R.id.nestedsv);
                        ns.smoothScrollTo(0, 0);
                        break;
                    case "Tulis" :
                        Intent intent = new Intent(ForumActivity.this, forumpostActivity.class);
                        startActivity(intent);
                        break;
                    case "Sort" :
                        if (timeasc){
                            Collections.sort(forum_models, Forum_model.bytimeasc);
                        }
                        else {
                            Collections.sort(forum_models, Forum_model.bytimedesc);
                        }
                        timeasc = !timeasc;
                        forum_adapter.notifyDataSetChanged();
                        break;

                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                forum_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        floatingbtn = findViewById(R.id.floatingbtn);
//        floatingbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(ForumActivity.this, forumpostActivity.class);
//                startActivity(intent);
//                if (timeasc){
//                    Collections.sort(forum_models, Forum_model.bytimeasc);
//                }
//                else {
//                    Collections.sort(forum_models, Forum_model.bytimedesc);
//                }
//                timeasc = !timeasc;
//                forum_adapter.notifyDataSetChanged();
//                finish();
//            }
//        });

        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference("forum");
        forum_models = new ArrayList<>(4);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                forum_models.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String judul = postSnapshot.child("judul").getValue(String.class);
                    String img = postSnapshot.child("img").getValue(String.class);
                    String imgpub = postSnapshot.child("imgpub").getValue(String.class);
                    Boolean status = postSnapshot.child("status").getValue(Boolean.class);
                    long tgl = postSnapshot.child("tgl").getValue(long.class);
                    String pertanyaan = postSnapshot.child("pertanyaan").getValue(String.class);
                    String nama = postSnapshot.child("nama").getValue(String.class);
                    String publisher = postSnapshot.child("publisher").getValue(String.class);
                    String id = postSnapshot.getKey();

                    forum_models.add(new Forum_model(id,nama, publisher, imgpub, judul, pertanyaan, img, tgl, status));

                }
                Collections.sort(forum_models, Forum_model.bytimedesc);
                timeasc = true;
                forum_adapter.notifyItemRangeChanged(0, forum_adapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_TWO);
        forum_adapter = new Forum_Adapter(this,forum_models, gridLayoutManager);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        recyclerView.setAdapter(forum_adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setPadding(10,0,10,0);
    }

}
