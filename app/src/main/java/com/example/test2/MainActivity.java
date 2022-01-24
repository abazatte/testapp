package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    EditText editText_FirstName;
    EditText editText_LastName;
    Button button_SaveUser;
    Button button_ChangeView;
    Handler handler;

    UserRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editText_FirstName = this.findViewById(R.id.editText_FirstName);
        this.editText_LastName = this.findViewById(R.id.editText_LastName);
        this.button_SaveUser = this.findViewById(R.id.button_SaveUser);
        this.button_ChangeView = this.findViewById(R.id.button_ChangeView);
        this.handler = new Handler(Looper.getMainLooper());
        this.repository = new UserRepository(this.getApplication());

        this.button_SaveUser.setOnClickListener(this::saveUser);
        this.button_ChangeView.setOnClickListener(this::changeView);
    }

    private void saveUser(View v){

        String firstName = this.editText_FirstName.getText().toString();
        String lastName = this.editText_LastName.getText().toString();

        CompletableFuture<User> userCompletableFuture = MainActivity.this.repository.addUser(firstName,lastName);
        CompletableFuture<Void> voidCompletableFuture = userCompletableFuture.thenAccept((user -> {
            MainActivity.this.handler.post(() -> {
                if (user == null) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                } else {
                    String toastMessage = user.id + " " + user.firstName + " " + user.lastName + " wurde hinzugefuegt.";
                    Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_LONG).show();
                }
            });
        }));
    }

    private void changeView(View v){

        startActivity(new Intent(this, view_activity.class));
    }
}