package edu.sabanciuniv.cs310news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class NewsAct extends AppCompatActivity {

    News data;

    boolean image;

    ImageView newsView;

    TextView textDate;

    TextView title;

    TextView textNews;

    ProgressBar progBar;

    int id;

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message message) {

            data = (News) message.obj;

            getSupportActionBar().setTitle(data.getCategoryName());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

                @SuppressLint("SimpleDateFormat") DateFormat orjDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {

                    textDate.setText(dateFormat.format(Objects.requireNonNull(orjDateFormat.parse(data.getDate()))));
                }
                catch (ParseException e) {

                    e.printStackTrace();
                }
            }

            title.setText(data.getTitle());

            textNews.setText(data.getText());

            ExecutorService exe_svr = ((NewsApp)getApplication()).srv;

            NewsRepo newsrepo = new NewsRepo();

            newsrepo.Image(exe_svr,imageHandler,data.getImage());

            return true;
        }
    });

    Handler imageHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message message) {

            Bitmap bitmap = (Bitmap) message.obj;

            newsView.setImageBitmap(bitmap);

            progBar.setVisibility(View.INVISIBLE);

            image = true;

            return true;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.post_menu_inside,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItemi) {

        if (menuItemi.getItemId() == R.id.detail_comment){

            Intent i = new Intent(this, CommentsAct.class);

            i.putExtra("id",id);

            startActivity(i);
        }

        else if (menuItemi.getItemId() == android.R.id.home){

            finish();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_act);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        id = getIntent().getIntExtra("id", 1);

        textDate = findViewById(R.id.textDate);

        title = findViewById(R.id.titleText);

        textNews = findViewById(R.id.newsText);

        newsView = findViewById(R.id.newsImage);

        progBar = findViewById(R.id.progressBar);

        progBar.setVisibility(View.VISIBLE);

        NewsRepo newsrepo = new NewsRepo();

        ExecutorService exe_svr = ((NewsApp)getApplication()).srv;

        newsrepo.getNewsById(exe_svr,handler,id);
    }

    public void downloadImage(ExecutorService exe_srv, String img){

        if(!image){

            NewsRepo newsrepo = new NewsRepo();

            newsrepo.Image(exe_srv, imageHandler, img);
        }
    }
}