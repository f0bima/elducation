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

public class Materi_Adapter extends RecyclerView.Adapter<Materi_Adapter.Slide_Holder> {
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_TWO = 2;
    Context c;
    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    List<Materi_model> models;
    private GridLayoutManager mLayoutManager;

    public Materi_Adapter(Context c, List<Materi_model> model, GridLayoutManager layoutManager){
        this.c = c;
        models = model;
        mLayoutManager = layoutManager;
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }
    }

    @Override
    public Slide_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zoom_card, parent, false);
        }
        return new Slide_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Slide_Holder holder, int i) {
        holder.mImageView.setAnimation(AnimationUtils.loadAnimation(c, R.anim.fade_transition_animation));
//        Materi_model model = models.get(i % 4);
        holder.mJudul.setText(models.get(i).getJudul());
        holder.mDeskripsi.setText(models.get(i).getDeskripsi());
        holder.mImageView.setImageResource(models.get(i).getImg());

        holder.setMateriClickListener(new MateriClickListener() {
            @Override
            public void onMateriClickListener(View v, int position) {
                String gTitle = models.get(position).getJudul();
                Intent intent = new Intent(c, BasicActivity.class);
                intent.putExtra("judul", gTitle);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class Slide_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;
        TextView mJudul;
        TextView mDeskripsi;

        MateriClickListener materiClickListener;

        Slide_Holder(View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.text);
            mDeskripsi = itemView.findViewById(R.id.deskripsi);
            mImageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.materiClickListener.onMateriClickListener(v, getLayoutPosition());
        }

        public void setMateriClickListener(MateriClickListener mc){
            this.materiClickListener = mc;
        }
    }
}
