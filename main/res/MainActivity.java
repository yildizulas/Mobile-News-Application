package com.example.homework2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;
    List<NewsCategories> data;
    Handler categoryHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            data = (List<NewsCategories>)message.obj;
            VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            vpAdapter.addFragment(new Fragment1(), data.get(0).getCategoryName());
            vpAdapter.addFragment(new Fragment2(), data.get(1).getCategoryName());
            vpAdapter.addFragment(new Fragment3(), data.get(2).getCategoryName());
            viewPager.setAdapter(vpAdapter);
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("CS310 News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.news_icon_13636);

        tab = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        tab.setupWithViewPager(viewPager);

        NewsRepository repo = new NewsRepository();
        ExecutorService svr = ((NewsApplication)getApplication()).srv;
        repo.getAllNewsCategory(svr,categoryHandler);
        Log.i("DEV","-----------------------On create --------------");

    }


}