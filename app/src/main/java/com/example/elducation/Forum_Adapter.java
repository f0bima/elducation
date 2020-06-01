package com.example.elducation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Forum_Adapter extends RecyclerView.Adapter<Forum_Adapter.Forum_Holder> implements Filterable {
    Context c;

    List<Forum_model> models;
    List<Forum_model> filterdata;
    private GridLayoutManager mLayoutManager;

    public Forum_Adapter(Context c, List<Forum_model> model, GridLayoutManager layoutManager){
        this.c = c;
        models = model;
        filterdata = model;
        mLayoutManager = layoutManager;
    }

    @Override
    public Forum_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_card, parent, false);
        return new Forum_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Forum_Holder holder, int i) {
        holder.mImageView.setAnimation(AnimationUtils.loadAnimation(c, R.anim.fade_transition_animation));
        holder.mJudul.setText(filterdata.get(i).getJudul());
//        String img = "https://img.youtube.com/vi/" +models.get(i).g+ "/0.jpg";

        Picasso.get().load(filterdata.get(i).getImg()).into(holder.mImageView);

        holder.setForumClickListener(new ForumClickListener() {
            @Override
            public void onMyForumClickListener(View v, int position) {
                String judul  = filterdata.get(position).getJudul();
                String id  = filterdata.get(position).getId();
                String pertanyaan = filterdata.get(position).getPertanyaan();
                long tgl = filterdata.get(position).getTgl();
                String img = filterdata.get(i).getImg();
                String imgpub = filterdata.get(i).getImgpub();
                String namapub = filterdata.get(i).getNama();
                Intent intent = new Intent(c, ForumDiscusionActivity.class);

                intent.putExtra("id", id);
                intent.putExtra("judul", judul);
                intent.putExtra("pertanyaan", pertanyaan);
                intent.putExtra("imgpub", imgpub);
                intent.putExtra("namapub", namapub);
                intent.putExtra("img", img);
                intent.putExtra("tgl", tgl);
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterdata.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString().toLowerCase();
                if (key.isEmpty()){
                    filterdata = models;
                }
                else {
                    List<Forum_model> filtered = new ArrayList<>();
                    for (Forum_model row : models){
                        if (row.getJudul().toLowerCase().contains(key)){
                            filtered.add(row);
                        }
                    }
                    filterdata = filtered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterdata;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterdata = (List<Forum_model>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class Forum_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;
        TextView mJudul;
        TextView mDeskripsi;

        ForumClickListener forumClickListener;

        Forum_Holder(View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.text);
            mDeskripsi = itemView.findViewById(R.id.deskripsi);
            mImageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.forumClickListener.onMyForumClickListener(v, getLayoutPosition());
        }

        public void setForumClickListener(ForumClickListener fc){
            this.forumClickListener = fc;
        }
    }
}
