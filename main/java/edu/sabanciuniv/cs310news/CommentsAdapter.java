package edu.sabanciuniv.cs310news;

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

        View view = LayoutInflater.from(ctx).inflate(R.layout.comments_row_layout, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

        holder.imgUser.setImageResource(R.drawable.ic_launcher_foreground);

        holder.txtComment.setText(data.get(holder.getAdapterPosition()).getText());

        holder.txtUsername.setText(data.get(holder.getAdapterPosition()).getName());

        holder.imgUser.setImageResource(android.R.drawable.ic_menu_save);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder{

        ImageView imgUser;

        TextView txtUsername;

        TextView txtComment;

        public CommentViewHolder(@NonNull View itemView) {

            super(itemView);

            imgUser = itemView.findViewById(R.id.image);

            txtUsername = itemView.findViewById(R.id.txtUsername);

            txtComment = itemView.findViewById(R.id.txtComment);
        }
    }
}