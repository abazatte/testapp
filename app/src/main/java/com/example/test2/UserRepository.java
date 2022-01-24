package com.example.test2;

import android.app.Application;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRepository{

    private UserDao userDao;

    /**
     * @author Abdurrahman Azattemür
     *
     * <p></p>
     * <P>Quelle: Roosmann Vorlesung</P>
     * */
    //diesen Konstruktor nur beim ersten mal Aufrufen
    public UserRepository(Application application){
        Datenbank datenbank = Datenbank.getInstance(application);
        this.userDao = datenbank.userDao();
    }

    /**
     * @author Maximilian Jaesch
     * */
    public UserRepository(Application application, Datenbank datenbank){
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

    public void deleteUser(User user){
        CompletableFuture.supplyAsync(() -> {
            this.userDao.deleteUser(user);

            return null;
        }, Datenbank.databaseWriterExecutorService);
    }
}
