package com.example.schedulerv8.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schedulerv8.Entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE user_id = :userID AND password = :password")
    LiveData<User> findUserByIDandPW(String userID, String password);

    @Query("SELECT DISTINCT user_id FROM users")
    LiveData<List<String>> getAllUserIds();
}
