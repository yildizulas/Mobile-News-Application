package edu.sabanciuniv.cs310news;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
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
    public newsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {

        //called when the row is drawn on the screen first time

        View view = LayoutInflater.from(ctx).inflate(R.layout.news_row_layout, parent, false);

        newsViewHolder holder = new newsViewHolder(view);

        holder.setIsRecyclable(false);

        return holder;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull newsViewHolder holder, int position) {

        //called when the row is displayed again
        //it is used for filling the UI elements with data

        holder.textTitle.setText(data.get(holder.getAdapterPosition()).getTitle());

        DateFormat orgFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            orgFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            try {

                holder.textDate.setText(format.format(Objects.requireNonNull(orgFormat.parse(data.get(holder.getAdapterPosition()).getDate()))));
            }

            catch (ParseException e) {

                e.printStackTrace();
            }
        }

        holder.progBar.setVisibility(View.VISIBLE);

        NewsApp newsapp = (NewsApp)((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(newsapp.srv, data.get(holder.getAdapterPosition()).getImage());

        holder.constrain.setOnClickListener(v->{

            Intent i = new Intent(ctx, NewsAct.class);

            i.putExtra("id", data.get(holder.getAdapterPosition()).getNews_id());

           ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class newsViewHolder extends RecyclerView.ViewHolder{

        Handler imageHandler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(@NonNull Message message) {

                Bitmap bitmap = (Bitmap) message.obj;

                imageNews.setImageBitmap(bitmap);

                progBar.setVisibility(View.INVISIBLE);

                image = true;

                return true;
            }
        });

        ImageView imageNews;
        TextView textDate;
        TextView textTitle;
        ConstraintLayout constrain;
        ProgressBar progBar;
        boolean image;

        public newsViewHolder(@NonNull View itemView) {

            super(itemView);

            imageNews = itemView.findViewById(R.id.imgNews);

            textDate = itemView.findViewById(R.id.txtDate);

            textTitle = itemView.findViewById(R.id.txtTitle);

            progBar = itemView.findViewById(R.id.progeres);

            constrain = itemView.findViewById(R.id.row);
        }

        public void downloadImage(ExecutorService exe_srv, String path){

            if (!image){

                NewsRepo newsrepo = new NewsRepo();

                newsrepo.Image(exe_srv, imageHandler, path);
            }
        }
    }
}