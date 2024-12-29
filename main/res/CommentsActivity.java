package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progbar;
    List<Comments> comments;
    int id;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            comments = (List<Comments>) message.obj;
            CommentsAdapter adp = new CommentsAdapter(CommentsActivity.this, comments);
            recyclerView.setAdapter(adp);
            progbar.setVisibility(View.INVISIBLE);
            return true;
        }
    });
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        id = getIntent().getIntExtra("id",1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");

        recyclerView = findViewById(R.id.recCommentView);

        progbar = findViewById(R.id.progressBar3);

        progbar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        ExecutorService srv = ((NewsApplication)getApplication()).srv;
        NewsRepository repo = new NewsRepository();
        repo.getCommentsById(srv, handler, id);



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        id = getIntent().getIntExtra("id",1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comments");

        recyclerView = findViewById(R.id.recCommentView);

        progbar = findViewById(R.id.progressBar3);

        progbar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        ExecutorService srv = ((NewsApplication)getApplication()).srv;
        NewsRepository repo = new NewsRepository();
        repo.getCommentsById(srv, handler, id);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        else if(item.getItemId()== R.id.post_comment){
            Intent i = new Intent(this,PostCommentsActivity.class);
            i.putExtra("id",id);

            startActivity(i);
        }

        return true;
    }
}