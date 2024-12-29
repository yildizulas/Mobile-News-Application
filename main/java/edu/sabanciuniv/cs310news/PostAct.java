package edu.sabanciuniv.cs310news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class PostAct extends AppCompatActivity {

    EditText textName;
    EditText commentText;
    Button button;
    int id, controll;
    String name, comment;

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message message) {

            controll = (int) message.obj;

            if (controll == 1){

                Intent intnt = new Intent(PostAct.this, CommentsAct.class);

                intnt.putExtra("id", id);

                startActivity(intnt);

                return true;
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle state) {

        super.onCreate(state);

        setContentView(R.layout.post_act);

        getSupportActionBar().setTitle("Post Comment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commentText = findViewById(R.id.commentText);

        textName = findViewById(R.id.commentName);

        button = findViewById(R.id.commentButton);

         id = getIntent().getIntExtra("id",1);

        button.setOnClickListener(v->{

             name = textName.getText().toString();

             comment = commentText.getText().toString();

             ExecutorService exe_srv = ((NewsApp)getApplication()).srv;

             NewsRepo newsrepo = new NewsRepo();

             newsrepo.postComment(exe_srv, handler, name, comment, String.valueOf(id));
         });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu_item) {

        if (menu_item.getItemId() == android.R.id.home) {

            finish();
        }

        return true;
    }
}