package com.example.schedulerv8.Entities;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.schedulerv8.Database.Repository;

public class UserManager {
    private static UserManager instance;
    private User loggedInUser;
    private Repository repository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private UserManager(Repository repository) {
        this.repository = repository;
    }

    public static synchronized UserManager getInstance(Repository repository) {
        if (instance == null) {
            instance = new UserManager(repository);
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        loggedInUser = user;
    }
     public User getLoggedInUser() {
        return loggedInUser;
     }



     public MutableLiveData<Boolean> isAuthenticated(String userID, String password) {
        MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>();
        LiveData<User> userLiveData = repository.findUserByIDandPW(userID, password);

        userLiveData.observeForever(user-> {
           if (user != null) {
                   loggedInUser = user;
                   isAuthenticated.setValue(true);
               } else {
                   isAuthenticated.setValue(false);
               }
        });
        return isAuthenticated;
     }

     public void logOut() {
        loggedInUser = null;
     }


     public boolean isAdmin() {
        return loggedInUser != null && loggedInUser.getIsAdmin();
     }
}
