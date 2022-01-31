package com.example.test2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class view_activityViewModel extends ViewModel {
    private MutableLiveData<List<User>> mUsers;

    public void init(){

    }

    public LiveData<List<User>> getUsers(){
        return mUsers;
    }

}
