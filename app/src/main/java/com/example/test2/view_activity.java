package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class view_activity extends AppCompatActivity {

    private UserListAdapter userListAdapter;
    private UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        this.repository = new UserRepository(this.getApplication(),Datenbank.getInstance(this.getApplicationContext()));

        initRecyclerView();

        //new Thread(this::loadUserList).start();
        loadUserListAsync();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(this, repository);
        recyclerView.setAdapter(userListAdapter);


    }

    private void loadUserListAsync(){
        //userListAdapter.setUserList(repository.showUser());
        CompletableFuture<List<User>> userListCompletableFuture = view_activity.this.repository.showUser();

        try {
            userListAdapter.setUserList(userListCompletableFuture.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadUserList() {

        Datenbank db = Datenbank.getInstance(this.getApplicationContext());
        List<User> userList = db.userDao().getAllUser();
        userListAdapter.setUserList(userList);

    }

}
