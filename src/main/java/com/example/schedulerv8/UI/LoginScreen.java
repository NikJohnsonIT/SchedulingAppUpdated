package com.example.schedulerv8.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.Entities.UserManager;
import com.example.schedulerv8.R;

public class LoginScreen extends AppCompatActivity {

    EditText userIDEditTxt;
    EditText passwordEditTxt;
    Button loginButton;
    Repository repository;
    UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG_LoginScreen", "onCreate called");
        repository = new Repository(getApplication());
        setContentView(R.layout.activity_login_screen);
        userManager = UserManager.getInstance(repository);
        userIDEditTxt = findViewById(R.id.userIDEdit);
        passwordEditTxt = findViewById(R.id.passwordEdit);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userIDString = userIDEditTxt.getText().toString();

                String password = passwordEditTxt.getText().toString();
                //int userId = Integer.parseInt(userIDString);

                if (validateInputs(userIDString, password)) {
                    userManager.isAuthenticated(userIDString, password).observe(LoginScreen.this, isAuthenticated -> {
                        if (isAuthenticated) {
                            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                            intent.putExtra("userId", userIDString);
                            startActivity(intent);
                            //finish();
                        } else {
                            Toast.makeText(LoginScreen.this, "Invalid information provided", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginScreen.this, "Please enter valid information", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean validateInputs(String userID, String password) {
        return !TextUtils.isEmpty(userID) && !TextUtils.isEmpty(password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG_LoginScreen", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG_LoginScreen", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG_LoginScreen", "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG_LoginScreen", "onDestroy called");
    }
}