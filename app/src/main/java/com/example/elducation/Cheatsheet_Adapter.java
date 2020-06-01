package com.example.elducation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Cheatsheet_Adapter extends RecyclerView.Adapter<Cheatsheet_Adapter.Slide_Holder> {
    Context c;

    List<Cheatsheet_model> models;
    private GridLayoutManager mLayoutManager;

    public Cheatsheet_Adapter(Context c, List<Cheatsheet_model> model, GridLayoutManager layoutManager){
        this.c = c;
        models = model;
        mLayoutManager = layoutManager;
    }

    @Override
    public Slide_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheatsheet_card, parent, false);
        return new Slide_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Slide_Holder holder, int i) {
        holder.mImageView.setAnimation(AnimationUtils.loadAnimation(c, R.anim.fade_transition_animation));
//        Materi_model model = models.get(i % 4);
        holder.mJudul.setText(models.get(i).getJudul());
        holder.mImageView.setImageResource(models.get(i).getImg());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class Slide_Holder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mJudul;


        Slide_Holder(View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.text);
            mImageView = itemView.findViewById(R.id.img);
        }
    }
}
