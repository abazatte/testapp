package com.example.test2;

import android.app.Application;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRepository{

    private UserDao userDao;

    public UserRepository(Application application){
        Datenbank datenbank = Datenbank.resetAndCreateNewInstance(application);
        this.userDao = datenbank.userDao();
    }

    public CompletableFuture<User> addUser(final String first, final String last){
        return CompletableFuture.supplyAsync(() -> {
            this.userDao.addUser(new User(first,last));

            User userForUsername = this.userDao.getUserForUsername(first,last);
            return userForUsername;
        }, Datenbank.databaseWriterExecutorService);
    }

    public CompletableFuture<List<User>> showUser (){
        return CompletableFuture.supplyAsync(() -> {
            List<User> listOfUser = this.userDao.getAllUser();
            return listOfUser;
        }, Datenbank.databaseWriterExecutorService);
    }
}
