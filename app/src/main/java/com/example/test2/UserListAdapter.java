package com.example.test2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;

    private UserRepository repository;
    public UserListAdapter(Context context, UserRepository userRepository){
        this.context = context;
        repository =  userRepository;
    }

    public void setUserList(List<User> userList1){
        this.userList = userList1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvFirstName.setText(this.userList.get(position).firstName);
        holder.tvLastName.setText(this.userList.get(position).lastName);
        holder.deleteButton.setOnClickListener(view -> {
            repository.deleteUser(new User(holder.tvFirstName.getText().toString(),holder.tvLastName.getText().toString()));
        });
    }


    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvFirstName;
        TextView tvLastName;
        Button deleteButton;

        public MyViewHolder(View view){
            super(view);
            tvFirstName = view.findViewById(R.id.tvFirstName);
            tvLastName = view.findViewById(R.id.tvLastName);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}
