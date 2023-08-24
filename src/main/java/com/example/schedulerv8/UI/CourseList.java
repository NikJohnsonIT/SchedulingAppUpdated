package com.example.schedulerv8.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.schedulerv8.Adapters.CourseAdapter;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseList extends AppCompatActivity {

    private Repository repository;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        repository = new Repository(getApplication());
        setupRecyclerView(userId);


        FloatingActionButton addCourseFAB = findViewById(R.id.courseListAddCourseFAB);
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseDetails.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent backIntent = new Intent(this, MainActivity.class);
                backIntent.putExtra("userId", userId);
                startActivity(backIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.putExtra("userId", userId);
        startActivity(backIntent);
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
       Intent intent = getIntent();
       userId = intent.getStringExtra("userId");
        setupRecyclerView(userId);
    }

    private void setupRecyclerView(String userId) {
        RecyclerView courseListRV = findViewById(R.id.coursesListAllCoursesRV);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        courseListRV.setAdapter(courseAdapter);
        courseListRV.setLayoutManager(new LinearLayoutManager(this));

        Log.d("CourseListDebug", "UserId passed: " + userId);

        if ("Adm!n".equals(userId)) {
            repository.getAllCourses().observe(this, new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
                    Log.d("CourseListDebug", "Admin Courses count: " + courses.size());
                    courseAdapter.setCourses(courses);
                }
            });
        } else {
            repository.getUserCourses(userId).observe(this, new Observer<List<Course>>() {
                @Override
                public void onChanged(List<Course> courses) {
                    Log.d("CourseListDebug", "User courses count: " + courses.size());
                    courseAdapter.setCourses(courses);
                }
            });
        }
    }
}