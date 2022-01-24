package com.example.test2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class Datenbank extends RoomDatabase {
    public static final String DB_NAME = "Database";
    private static volatile Datenbank INSTANCE;
    static ExecutorService databaseWriterExecutorService = Executors.newSingleThreadExecutor();

    public static Datenbank resetAndCreateNewInstance(final Context appContext){
        appContext.deleteDatabase(DB_NAME);
        Datenbank.INSTANCE = null;
        return getInstance(appContext);
    }

    public static Datenbank getInstance(final Context appContext){
        if (INSTANCE == null){
            synchronized (Datenbank.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            appContext.getApplicationContext(),
                            Datenbank.class,
                            DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract UserDao userDao();
}
