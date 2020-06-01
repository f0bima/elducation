package com.example.elducation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Comment_holder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView nama, comment;

    public Comment_holder(@NonNull View itemView){
        super(itemView);
        this.imageView = itemView.findViewById(R.id.image_comment);
        this.nama = itemView.findViewById(R.id.nama_comment);
        this.comment = itemView.findViewById(R.id.comment);
    }

}
