package com.example.schedulerv8.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.Entities.UserManager;
import com.example.schedulerv8.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            userId = savedInstanceState.getString("userId");
        } else {
            Intent intent = getIntent();
            userId = intent.getStringExtra("userId");
        }
        Log.d("DEBUG_MAINACTIVITY", "userId passed: " + userId);


        Repository repository = new Repository(getApplication());

        //Define Buttons. Only use floating action button if it is the only button as per android documentation.
        Button termButton = findViewById(R.id.mainScreenTermsButton);
        Button courseButton = findViewById(R.id.mainScreenCoursesButton);
        Button assessmentButton = findViewById(R.id.mainScreenAssessmentsButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        boolean isAdmin = UserManager.getInstance(repository).isAdmin();
        Button reportsButton = findViewById(R.id.reportsButtonAdminOnly);

        if (isAdmin) {
            reportsButton.setVisibility(View.VISIBLE);
            reportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Reports.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            });
        } else {
            reportsButton.setVisibility(View.GONE);
            reportsButton.setEnabled(false);
        }

        termButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This is telling the app where it is and where it is going following the button being clicked/pushed.
                Intent intent = new Intent(MainActivity.this, TermsList.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CourseList.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AssessmentList.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManager userManager = UserManager.getInstance(repository);
                userManager.logOut();
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("userId", userId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userId = savedInstanceState.getString("userId");
    }

    @Override
    public void onBackPressed() {
        Log.d("DEBUG_MAINACTIVITY", "onBackPressed called");
        Repository repository = new Repository(getApplication());
        UserManager userManager = UserManager.getInstance(repository);
        userManager.logOut();

        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG_MAINACTIVITY", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG_MAINACTIVITY", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG_MAINACTIVITY", "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG_MAINACTIVITY", "onDestroy called");
    }
}
