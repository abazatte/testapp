package com.example.test2;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(String firstName, String lastName);
    }

    private Context context;
    private List<User> userList;
    private OnDeleteClickListener onDeleteClickListener;

    public UserListAdapter(Context context, OnDeleteClickListener onDeleteClickListener){
        this.context = context;
        this.onDeleteClickListener = onDeleteClickListener;
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


    //ich glaube hier den repository zu haben ist absolut harammm
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvFirstName.setText(this.userList.get(position).firstName);
        holder.tvLastName.setText(this.userList.get(position).lastName);
        holder.deleteButton.setOnClickListener(view -> {
            onDeleteClickListener.OnDeleteClickListener(this.userList.get(position).firstName,this.userList.get(position).lastName);
            Log.d("lmao","slkafjiewjfioaefjioyjf");
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
