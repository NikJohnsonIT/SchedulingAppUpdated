package com.example.schedulerv8.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.schedulerv8.Adapters.UserAdapter;
import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.CourseDTO;
import com.example.schedulerv8.Entities.User;
import com.example.schedulerv8.Entities.UserDisplayModel;
import com.example.schedulerv8.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Reports extends AppCompatActivity {
    private Repository repository;
    private Context context;
    private String userId;
    EditText totalCoursesEditText;
    EditText synchronousCoursesEditText;
    EditText asynchronousCoursesEditText;
    Button mainMenuButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("userId");
        }

        context = this;

        RecyclerView recyclerView = findViewById(R.id.reportsRecyclerView);
        SearchView searchView = findViewById(R.id.searchView);
        ListView searchResultsListView = findViewById(R.id.searchResultsListView);
        repository = new Repository(getApplication());

        TextView dateStampTextView = findViewById(R.id.dateStampTextView);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyy HH:mm", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        dateStampTextView.setText(currentDateAndTime);

        final UserAdapter userAdapter = new UserAdapter(this, repository);
        recyclerView.setAdapter(userAdapter);

        totalCoursesEditText = findViewById(R.id.totalCoursesEditText);
        synchronousCoursesEditText = findViewById(R.id.synchronousCountText);
        asynchronousCoursesEditText = findViewById(R.id.asynchronousCountText);

        repository.countTotalCourses().observe(this, integer -> totalCoursesEditText.setText(String.valueOf(integer)));
        repository.countCoursesSync().observe(this, integer -> synchronousCoursesEditText.setText(String.valueOf(integer)));
        repository.countCoursesAsync().observe(this, integer -> asynchronousCoursesEditText.setText(String.valueOf(integer)));

        List<UserDisplayModel> displayDataList = new ArrayList<>();

        repository.getAllUserIds().observe(this, userIds -> {
            for(String userId : userIds) {
                LiveData<Integer> coursesCountLiveData = repository.coursesCreatedByUserCount(userId);
                LiveData<Integer> termsCountLiveData = repository.getTermsCreatedByUserCount(userId);

                coursesCountLiveData.observe(this, coursesCount -> {
                    termsCountLiveData.observe(this, termsCount -> {
                        UserDisplayModel displayModel = new UserDisplayModel(userId, termsCount, coursesCount);
                        displayDataList.add(displayModel);

                        if(displayDataList.size() == userIds.size()) {
                            userAdapter.setUserDisplayData(displayDataList);
                        }
                    });
                });
            }
        });

        mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reports.this, MainActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               repository.searchCourses("%" + query + "%").observe(Reports.this, searchResults -> {
                   ArrayAdapter<CourseDTO> adapter = new ArrayAdapter<>(Reports.this, android.R.layout.simple_expandable_list_item_1, searchResults);
                   searchResultsListView.setAdapter(adapter);
               });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}