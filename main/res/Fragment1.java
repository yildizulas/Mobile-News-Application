package com.example.homework2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Fragment1 extends Fragment {

    RecyclerView recView;
    ProgressBar pgBar;
    List<News> data;
    List<NewsCategories> dataCat;
    Context ctx;
    int id;
    Handler handlerResult = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            data = (List<News>) message.obj;
            NewsAdapter adp = new NewsAdapter(ctx, data);
            recView.setAdapter(adp);
            pgBar.setVisibility(View.INVISIBLE);
            return true;
        }
    });

    Handler handleCategories = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            dataCat = (List<NewsCategories>)message.obj;
            id = dataCat.get(0).getId();
            NewsRepository repo = new NewsRepository();
            NewsApplication app = (NewsApplication)((AppCompatActivity)ctx).getApplication();
            repo.getNewsByCategory((app.srv), handlerResult, id);
            return true;
        }
    });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_layout, container, false);
        ctx = v.getContext();
        pgBar = v.findViewById(R.id.pgBar);
        recView = v.findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(ctx));

        pgBar.setVisibility(View.VISIBLE);
        NewsRepository repo = new NewsRepository();
        NewsApplication app = (NewsApplication)((AppCompatActivity)ctx).getApplication();
        repo.getAllNewsCategory(app.srv, handleCategories);





        return v;
    }
}
