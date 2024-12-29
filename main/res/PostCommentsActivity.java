package com.example.homework2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class PostCommentsActivity extends AppCompatActivity {

    EditText editTxtName;
    EditText editTxtComment;
    Button btnPost;
    int id;
    int check;
    ProgressDialog progressDoalog;
    String name, comment;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            check = (int) message.obj;
            if( check == 1){
                Intent i = new Intent(PostCommentsActivity.this, CommentsActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
            else{
                AlertDialog alertDialog = new AlertDialog.Builder(PostCommentsActivity.this).create();
                alertDialog.setTitle("ERROR:");
                alertDialog.setMessage("You leave some spaces empty please try again");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        getSupportActionBar().setTitle("Post Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTxtComment = findViewById(R.id.txtMcomments);
        editTxtName = findViewById(R.id.editTxtName);
        btnPost = findViewById(R.id.btnPost);

         id = getIntent().getIntExtra("id",1);

         btnPost.setOnClickListener(v->{
             name = editTxtName.getText().toString();
             comment = editTxtComment.getText().toString();
             ExecutorService srv = ((NewsApplication)getApplication()).srv;
             NewsRepository repo = new NewsRepository();
             repo.postComment(srv,handler,name, comment, String.valueOf(id) );
             progressDoalog = new ProgressDialog(PostCommentsActivity.this);
             progressDoalog.setMax(100);
             progressDoalog.setMessage("Commenting....");
             progressDoalog.setTitle("Posting Comment");
             progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
             progressDoalog.show();



         });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}