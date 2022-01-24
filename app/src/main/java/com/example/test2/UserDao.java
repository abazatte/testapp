package com.example.test2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);
    @Delete
    void deleteUser(User user);

    @Query("Delete from user where firstName = :first AND lastName = :last")
    void deleteUserWithFirstAndLast (String first, String last);


    @Query("SELECT * FROM user")
    List<User> getAllUser();
    @Query("SELECT * FROM user WHERE firstName = :first AND lastName = :last")
    User getUserForUsername (String first, String last);
}
