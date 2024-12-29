package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.newsViewHolder>{


    private Context ctx;
    private List<News> data;

    public NewsAdapter(Context ctx, List<News> data){
        this.ctx = ctx;
        this.data = data;
    }
    @NonNull
    @Override
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.news_row_layout, parent, false);
        newsViewHolder holder = new newsViewHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull newsViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(holder.getAdapterPosition()).getTitle());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            DateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");

            try {
                holder.txtDate.setText(formatter1.format(formatter.parse(data.get(holder.getAdapterPosition()).getDate())));;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        holder.progressBar.setVisibility(View.VISIBLE);

        NewsApplication app = (NewsApplication)((AppCompatActivity)ctx).getApplication();
        holder.Image(app.srv, data.get(holder.getAdapterPosition()).getImage());
        holder.row.setOnClickListener(v->{
            Intent i = new Intent(ctx, NewsDetailActivity.class);
            i.putExtra("id", data.get(holder.getAdapterPosition()).getNews_id());
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class newsViewHolder extends RecyclerView.ViewHolder{

        Handler imgHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                Bitmap bitmap = (Bitmap) message.obj;
                imgNews.setImageBitmap(bitmap);
                progressBar.setVisibility(View.INVISIBLE);
                imageDownloaded = true;
                return true;
            }
        });
        ImageView imgNews;
        TextView txtDate;
        TextView txtTitle;
        ConstraintLayout row;
        ProgressBar progressBar;
        boolean imageDownloaded;
        public newsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgNews);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            progressBar = itemView.findViewById(R.id.progeres);
            row = itemView.findViewById(R.id.row);
        }

        public void downloadImage(ExecutorService srv, String path){
            if(!imageDownloaded){
                NewsRepository repo = new NewsRepository();
                repo.Image(srv, imgHandler, path);
            }
        }
    }
}
