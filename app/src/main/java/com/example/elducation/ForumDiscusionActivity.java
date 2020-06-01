package com.example.elducation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForumDiscusionActivity extends AppCompatActivity {

    RecyclerView recycle_comment;
    CommentForum_adapter comment_adapter;
    EditText comment;
    ImageButton send;
    TextView judultv, desctv, countcmnt, namapub;
    String id, userid, deskripsi, judul;
    FirebaseAuth mAuth;
    ImageView imgforum;
    FirebaseUser user;
    DatabaseReference db;
    CircleImageView photo, photopub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_discusion);

        String id = getIntent().getStringExtra("id");
//        Toast.makeText(this, intent, Toast.LENGTH_LONG).show();
//        Log.e("A", intent);

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getUid();

        db = FirebaseDatabase.getInstance().getReference("forum_comment").child(id);

        judultv = findViewById(R.id.judul);
        desctv = findViewById(R.id.materi);
        photo = findViewById(R.id.profile_image);
        photopub = findViewById(R.id.imgpub);
        namapub = findViewById(R.id.namapub);
        countcmnt = findViewById(R.id.countcmnt);
        imgforum = findViewById(R.id.imgforum);

        Log.e("src", getIntent().getStringExtra("img"));
        Uri imgf = Uri.parse(getIntent().getStringExtra("imgpub"));

        Glide.with(this).load(mAuth.getCurrentUser().getPhotoUrl()).into(photo);
        Glide.with(this).load(imgf).into(photopub);

        Picasso.get().load(getIntent().getStringExtra("img")).into(imgforum);
//        Picasso.get().load(getIntent().getStringExtra("imgpub")).into(photopub);
//        Glide.with(this).load(imgf).into(imgforum);

        judultv.setText(getIntent().getStringExtra("judul"));
        desctv.setText(getIntent().getStringExtra("pertanyaan"));
        namapub.setText(getIntent().getStringExtra("namapub"));
        comment = findViewById(R.id.comment);
        send = findViewById(R.id.btn_send);
        send.setOnClickListener(v -> sendclick());
        recycle_comment = findViewById(R.id.recycle_comment);
        recycle_comment.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<CommentForum_model> models = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("forum_comment").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CommentForum_model m;
                models.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    m = new CommentForum_model();
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
        comment_adapter = new CommentForum_adapter(this, models);
        recycle_comment.setAdapter(comment_adapter);
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
}
