package com.example.elducation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentForum_adapter extends RecyclerView.Adapter<CommentForum_holder> {
    Context c;
    ArrayList<CommentForum_model> models;

    public CommentForum_adapter(Context c, ArrayList<CommentForum_model> models){
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public CommentForum_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumcmntcard_layout, parent, false);
        return new CommentForum_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentForum_holder holder, int position) {

//        holder.imageView.setImageResource(models.get(position).getImg());
        Glide.with(this.c).load(models.get(position).getImg()).into(holder.imageView);
        holder.nama.setText(models.get(position).getNama());
        holder.comment.setText(models.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private void getUserInfo(ImageView imageView, TextView username, String publisherid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(publisherid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                username.setText(dataSnapshot.child());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
