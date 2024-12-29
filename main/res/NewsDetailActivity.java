package com.example.homework2;

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
import java.util.concurrent.ExecutorService;

public class NewsDetailActivity extends AppCompatActivity {

    News data;
    boolean imageDownloaded;
    ImageView pngNews;
    TextView txtDateD;
    TextView txtTitleD;
    TextView txtNews;
    ProgressBar progressBar;
    int id;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            data = (News) message.obj;
            getSupportActionBar().setTitle(data.getCategoryName());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                DateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");

                try {
                    txtDateD.setText(formatter1.format(formatter.parse(data.getDate())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            txtTitleD.setText(data.getTitle());
            txtNews.setText(data.getText());

            ExecutorService svr = ((NewsApplication)getApplication()).srv;
            NewsRepository repo = new NewsRepository();
            repo.downloadImage(svr,imgHandler,data.getImage());
            return true;
        }
    });
    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bitmap bitmap = (Bitmap) message.obj;
            pngNews.setImageBitmap(bitmap);
            progressBar.setVisibility(View.INVISIBLE);

            imageDownloaded = true;
            return true;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.detail_comment){
            Intent i = new Intent(this,CommentsActivity.class);

            i.putExtra("id",id);

            startActivity(i);
        }
        else if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getIntExtra("id", 1);


        txtDateD = findViewById(R.id.txtDateD);
        txtTitleD = findViewById(R.id.txtTitleD);
        txtNews = findViewById(R.id.txtNews);
        pngNews = findViewById(R.id.pngNews);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);



        NewsRepository repo = new NewsRepository();
        ExecutorService svr = ((NewsApplication)getApplication()).srv;
        repo.getNewsById(svr,handler,id);




    }

    public void downloadImage(ExecutorService srv, String path){
        if(!imageDownloaded){
            NewsRepository repo = new NewsRepository();
            repo.downloadImage(srv, imgHandler, path);
        }
    }

}