package com.example.test2;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApplication extends Application {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);


}
