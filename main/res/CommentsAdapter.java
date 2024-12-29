package com.example.homework2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>{

    List<Comments> data;
    Context ctx;

    public CommentsAdapter(Context ctx, List<Comments> data){
        this.ctx = ctx;
        this.data = data;
    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.comments_row_layout, parent, false);
        CommentViewHolder holder = new CommentViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.imgUser.setImageResource(R.drawable.ic_launcher_foreground);
        holder.txtComment.setText(data.get(holder.getAdapterPosition()).getText());
        holder.txtUsername.setText(data.get(holder.getAdapterPosition()).getName());
        holder.imgUser.setImageResource(android.R.drawable.ic_menu_my_calendar);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView imgUser;
        TextView txtUsername;
        TextView txtComment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtComment = itemView.findViewById(R.id.txtComment);
        }
    }
}
