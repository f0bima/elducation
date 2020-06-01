package com.example.elducation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MateriActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener {
    RecyclerView recycle_comment;
    Comment_adapter comment_adapter;
    YouTubePlayerView youTubePlayerView;
    EditText comment;
    ImageButton send;
    TextView judultv, desctv, countcmnt;
    String videoid, id, userid, deskripsi, judul;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference db;
    CircleImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);
//        toolbar.setTitle(getIntent().getStringExtra("id"));
        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getUid();

        id = getIntent().getStringExtra("id");
        db = FirebaseDatabase.getInstance().getReference("comment").child(id);
        videoid = getIntent().getStringExtra("video");

        judultv = findViewById(R.id.judul);
        desctv = findViewById(R.id.materi);
        photo = findViewById(R.id.profile_image);
        countcmnt = findViewById(R.id.countcmnt);

        Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(photo);
        judultv.setText(getIntent().getStringExtra("judul"));
        desctv.setText(getIntent().getStringExtra("deskripsi"));

//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        comment = findViewById(R.id.comment);
        send = findViewById(R.id.btn_send);
        send.setOnClickListener(v -> sendclick());
        recycle_comment = findViewById(R.id.recycle_comment);
        recycle_comment.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Comment_model> models = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comment").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Comment_model m;
                models.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    m = new Comment_model();
                    m.setNama(snapshot.child("nama").getValue(String.class));
                    m.setComment(snapshot.child("comment").getValue(String.class));
                    m.setImg(snapshot.child("img").getValue(String.class));
                    models.add(m);
                }
                comment_adapter.notifyItemRangeChanged(0, comment_adapter.getItemCount());
                countcmnt.setText(Integer.toString(comment_adapter.getItemCount())+ " Comments");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        comment_adapter = new Comment_adapter(this, models);
        recycle_comment.setAdapter(comment_adapter);

        youTubePlayerView = findViewById(R.id.videoplayer);
        youTubePlayerView.initialize(YtConfig.getAPi_key(), this);
    }

    private void sendclick(){
        if(comment.getText().toString().equals("")){
            Toast.makeText(this, "Comment tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
        else{
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("img", mAuth.getCurrentUser().getPhotoUrl().toString());
            hashMap.put("nama", mAuth.getCurrentUser().getDisplayName());
            hashMap.put("comment", comment.getText().toString());
            hashMap.put("publisher", userid);
            db.push().setValue(hashMap);
            comment.setText("");
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        if (!b){
            youTubePlayer.cueVideo(videoid);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }
}
