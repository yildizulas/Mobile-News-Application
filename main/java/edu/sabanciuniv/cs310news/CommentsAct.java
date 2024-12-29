package edu.sabanciuniv.cs310news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.Menu;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class CommentsAct extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progresbar;
    List<Comments> comments;

    int id;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            comments = (List<Comments>) message.obj;

            CommentsAdapter com_adp = new CommentsAdapter(CommentsAct.this, comments);

            recyclerView.setAdapter(com_adp);

            progresbar.setVisibility(View.INVISIBLE);

            return true;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.post_comment,menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.comments_act);

        id = getIntent().getIntExtra("id",1);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Comments");

        recyclerView = findViewById(R.id.comment_view);

        progresbar = findViewById(R.id.progressBar);

        progresbar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ExecutorService exe_service = ((NewsApp)getApplication()).srv;

        NewsRepo news_repo = new NewsRepo();

        news_repo.getCommentsById(exe_service, handler, id);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        id = getIntent().getIntExtra("id",1);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Comments");

        recyclerView = findViewById(R.id.comment_view);

        progresbar = findViewById(R.id.progressBar);

        progresbar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ExecutorService exe_service = ((NewsApp)getApplication()).srv;

        NewsRepo news_repo = new NewsRepo();

        news_repo.getCommentsById(exe_service, handler, id);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){

            finish();
        }

        else if (item.getItemId()== R.id.post_comment){

            Intent i = new Intent(this, PostAct.class);

            i.putExtra("id",id);

            startActivity(i);
        }

        return true;
    }
}