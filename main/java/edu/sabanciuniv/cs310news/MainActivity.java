package edu.sabanciuniv.cs310news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    Button eco_button, sport_button, poli_button;

    RecyclerView view;

    List<News> data;

    Handler catHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(@NonNull Message msg) {

            data = (List<News>) msg.obj;

            NewsAdapter msgAdp = new NewsAdapter(MainActivity.this, data);

            view.setAdapter(msgAdp);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("CS310 News");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.news_icon_13636);

        view = findViewById(R.id.viewRec);

        eco_button = findViewById(R.id.ecoButton);
        sport_button = findViewById(R.id.sportButton);
        poli_button = findViewById(R.id.poliButton);

        view.setLayoutManager(new LinearLayoutManager(this));

        eco_button.setOnClickListener(v -> {

            NewsRepo newsRepo = new NewsRepo();

            ExecutorService exeSrv = ((NewsApp)getApplication()).srv;

            newsRepo.getNewsByCategory(exeSrv, catHandler,1);
        });

        sport_button.setOnClickListener(v -> {

            NewsRepo newsRepo = new NewsRepo();

            ExecutorService exeSrv = ((NewsApp)getApplication()).srv;

            newsRepo.getNewsByCategory(exeSrv, catHandler,2);
        });

        poli_button.setOnClickListener(v -> {

            NewsRepo newsRepo = new NewsRepo();

            ExecutorService exeSrv = ((NewsApp)getApplication()).srv;

            newsRepo.getNewsByCategory(exeSrv, catHandler,3);
        });
    }
}