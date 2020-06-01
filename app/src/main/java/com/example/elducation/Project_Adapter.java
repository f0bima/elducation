package com.example.elducation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class Project_Adapter extends RecyclerView.Adapter<Project_Adapter.Slide_Holder> implements Filterable {
    Context c;

    List<Project_model> models;
    List<Project_model> filterdata;
    private GridLayoutManager mLayoutManager;

    public Project_Adapter(Context c, List<Project_model> model, GridLayoutManager layoutManager){
        this.c = c;
        models = model;
        filterdata = model;
        mLayoutManager = layoutManager;
    }

    @Override
    public Slide_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_card, parent, false);
        return new Slide_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Slide_Holder holder, int i) {
        holder.mImageView.setAnimation(AnimationUtils.loadAnimation(c, R.anim.fade_transition_animation));
//        Materi_model model = models.get(i % 4);
        holder.mJudul.setText(filterdata.get(i).getJudul());
//        holder.mDeskripsi.setText(models.get(i).getDeskripsi());
        String img = "https://img.youtube.com/vi/" +filterdata.get(i).getVideo()+ "/0.jpg";
//        holder.mImageView.setImageURI(Uri.parse(img));
        Picasso.get().load(img).into(holder.mImageView);

        holder.setProjectClickListener(new ProjectClickListener() {
            @Override
            public void onMylabClickListener(View v, int position) {
                String id = filterdata.get(position).getId();
                String judul  = filterdata.get(position).getJudul();
                String deskripsi = filterdata.get(position).getDeskripsi();
                String video = filterdata.get(position).getVideo();
//                String gdesk = models.get(position).getDeskripsi();
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.mImageView.getDrawable();
//
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//                byte[] bytes = stream.toByteArray();

                Intent intent = new Intent(c, MateriActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("judul", judul);
                intent.putExtra("deskripsi", deskripsi);
                intent.putExtra("video", video);
//                intent.putExtra("deskripsi", gdesk);
//                intent.putExtra("img", bytes);
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
                    List<Project_model> filtered = new ArrayList<>();
                    for (Project_model row : models){
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
                filterdata = (List<Project_model>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class Slide_Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;
        TextView mJudul;
        TextView mDeskripsi;

        ProjectClickListener projectClickListener;

        Slide_Holder(View itemView) {
            super(itemView);
            mJudul = itemView.findViewById(R.id.text);
            mDeskripsi = itemView.findViewById(R.id.deskripsi);
            mImageView = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.projectClickListener.onMylabClickListener(v, getLayoutPosition());
        }

        public void setProjectClickListener(ProjectClickListener mc){
            this.projectClickListener = mc;
        }
    }
}
