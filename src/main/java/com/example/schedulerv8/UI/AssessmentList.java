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

import com.example.schedulerv8.Adapters.AssessmentAdapter;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentList extends AppCompatActivity {
    private Repository repository;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        repository = new Repository(getApplication());
        setupRecyclerView(userId);

        FloatingActionButton addAssessmentFAB = findViewById(R.id.assessListAddNewFAB);
        addAssessmentFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        setupRecyclerView(userId);

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

    private void setupRecyclerView(String userId) {
        RecyclerView assessmentListRV = findViewById(R.id.assessListAllRV);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        assessmentListRV.setAdapter(assessmentAdapter);
        assessmentListRV.setLayoutManager(new LinearLayoutManager(this));
        Log.d("AssessmentListDebug", "UserId passed: " + userId);

        if("Adm!n".equals(userId)) {
            repository.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
                @Override
                public void onChanged(List<Assessment> assessments) {
                    Log.d("AssessmentListDebug", "All assessments count: " + assessments.size());
                    assessmentAdapter.setAssessments(assessments);
                }
            });
        } else {
            repository.getAssessmentsCreatedByUser(userId).observe(this, new Observer<List<Assessment>>() {
                @Override
                public void onChanged(List<Assessment> assessments) {
                    Log.d("AssessmentListDebug", "User assessments count: " + assessments.size());
                    assessmentAdapter.setAssessments(assessments);
                }
            });
        }
    }
}