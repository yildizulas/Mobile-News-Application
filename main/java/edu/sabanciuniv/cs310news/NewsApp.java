package edu.sabanciuniv.cs310news;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsApp extends Application {

    ExecutorService srv = Executors.newCachedThreadPool();
}
