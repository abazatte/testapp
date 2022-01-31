package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class view_activity extends AppCompatActivity implements UserListAdapter.OnDeleteClickListener {

    private UserListAdapter userListAdapter;
    private UserRepository repository;
    private view_activityViewModel mview_activityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        this.repository = new UserRepository(this.getApplication(),Datenbank.getInstance(this.getApplicationContext()));

        this.mview_activityViewModel = new ViewModelProvider(this).get(view_activityViewModel.class);

        /*
        this.mview_activityViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userListAdapter.notifyDataSetChanged();
            }
        });*/

        initRecyclerView();

        //new Thread(this::loadUserList).start();
        loadUserListAsync();
        //iwie wird das halt nie geupdated
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        userListAdapter = new UserListAdapter(this, this);
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

    //doesnt work dont use
    private void loadUserList() {

        Datenbank db = Datenbank.getInstance(this.getApplicationContext());
        List<User> userList = db.userDao().getAllUser();
        userListAdapter.setUserList(userList);

    }

    @Override
    public void OnDeleteClickListener(String firstName, String lastName) {
        repository.deleteUserByName(firstName,lastName);

        loadUserListAsync();
    }
}
