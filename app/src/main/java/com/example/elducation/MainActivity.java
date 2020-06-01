package com.example.elducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;

import static com.example.elducation.Materi_Adapter.SPAN_COUNT_TWO;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvMateri, rvProject;
    private Materi_Adapter materi_adapter;
    private Project_Adapter project_adapter;

    private List<Project_model> project_model;
    private GridLayoutManager gridLayoutManager;
    private List<Materi_model> materi_models;

    FloatingActionButton floatingbtn;

    Button basic_more, cheetsheet, simulasi;
    Boolean isScrolling = false;
    String uid;
    FirebaseAuth mAuth;
    CircleImageView photo;
//    int currentItems, totalItems, scrollOutitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        basic_more = findViewById(R.id.basic);
        floatingbtn = findViewById(R.id.floatingbtn);
        cheetsheet = findViewById(R.id.cheetsheet);
        simulasi = findViewById(R.id.simulasi);
        photo = findViewById(R.id.profile_image);
        photo.setOnClickListener(v -> logout());

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();

        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                project_adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                        NestedScrollView ns;
                        ns= findViewById(R.id.nestedsv);
                        ns.smoothScrollTo(0, 0);
                        break;
                    case "Forum" :
                        Intent intent = new Intent(MainActivity.this, ForumActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        getSupportActionBar().setTitle(mAuth.getCurrentUser().getDisplayName());

        Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(photo);
        Log.e("photo", String.valueOf(mAuth.getCurrentUser().getPhotoUrl()));

        basic_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasicMoreActivity.class);
                startActivity(intent);
            }
        });

        materi_models = new ArrayList<>(4);
        project_model = new ArrayList<>(4);
        gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT_TWO);
        materi_adapter = new Materi_Adapter(this,materi_models, gridLayoutManager);
        project_adapter = new Project_Adapter(this,project_model, gridLayoutManager);

        materi_models.add(new Materi_model("Resistor", "Ini Resistor", R.drawable.resistor_png));
        materi_models.add(new Materi_model("Capacitor", "Ini Capacitor", R.drawable.capacitor));
        materi_models.add(new Materi_model("Induktor", "Ini Induktor", R.drawable.inductor));
        materi_adapter.notifyItemRangeChanged(0, materi_adapter.getItemCount());

        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                NestedScrollView ns;
                ns= findViewById(R.id.nestedsv);
                ns.smoothScrollTo(0, 0);
            }
        });

        cheetsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheatSheetActivity.class);
                startActivity(intent);
            }
        });

        simulasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SimulasiActivity.class);
                startActivity(intent);
            }
        });

        byte[] mBytes = getIntent().getByteArrayExtra("img");

        DatabaseReference db;
        db = FirebaseDatabase.getInstance().getReference("materi");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                materi_models.clear();
                project_model.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue(String.class);
                    String judul = postSnapshot.child("judul").getValue(String.class);
                    String img = postSnapshot.child("img").getValue(String.class);
                    String deskripsi = postSnapshot.child("isi").getValue(String.class);
                    String video = postSnapshot.child("video").getValue(String.class);

//                    materi_models.add(new Materi_model(judul, "", R.drawable.p4));

                    project_model.add(new Project_model(id, judul, deskripsi, img, video));
//                    Log.e("Get Data", judul.toString());
                }
//                materi_adapter.notifyItemRangeChanged(0, materi_adapter.getItemCount());
                project_adapter.notifyItemRangeChanged(0, project_adapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);


        rvMateri = (RecyclerView) findViewById(R.id.rv);
        rvProject = findViewById(R.id.rv2);
        rvProject.setLayoutManager(gridLayoutManager);
        rvProject.setAdapter(project_adapter);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMateri.setLayoutManager(layoutManager);
        rvMateri.setAdapter(materi_adapter);
//        rvMateri.setLayoutManager(gridLayoutManager);

        rvMateri.setPadding(130,0,130,0);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvMateri);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    RecyclerView.ViewHolder viewHolder = rvMateri.findViewHolderForAdapterPosition(0);
                    RelativeLayout ril = viewHolder.itemView.findViewById(R.id.ril);
                    ril.animate().scaleX(1).scaleY(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();
                }
                catch (Exception e){

                }
            }
        }, 100);

        rvMateri.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View v = snapHelper.findSnapView(layoutManager);
                int pos = layoutManager.getPosition(v);

                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
                RelativeLayout ril = viewHolder.itemView.findViewById(R.id.ril);

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    ril.animate().setDuration(350).scaleX(1).scaleY(1).setInterpolator(new AccelerateInterpolator()).start();
                }
                else {
                    ril.animate().setDuration(350).scaleX(0.9f).scaleY(0.9f).setInterpolator(new AccelerateInterpolator()).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void logout(){
        AuthActivity.Logout();
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

//    private void forum() {
//        Intent intent = new Intent(this, SimulasiActivity.class);
//        intent.putExtra("project", "A");
//                intent.putExtra("deskripsi", gdesk);
//                intent.putExtra("img", bytes);
//        this.startActivity(intent);
//    }

}
