package com.example.schedulerv8.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @ColumnInfo(name = "is_admin")
    private boolean isAdmin;

    @PrimaryKey
    @ColumnInfo(name = "user_id")
    @NonNull
    private String userID;

    @ColumnInfo(name = "password")
    private String password;

    public User(boolean isAdmin, String userID, String password) {
        this.isAdmin = isAdmin;
        this.userID = userID;
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", password=' " + password + '\'' +
                '}';
    }


}