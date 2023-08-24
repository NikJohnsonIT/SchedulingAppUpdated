package com.example.schedulerv8.UI;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.schedulerv8.Database.Repository;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.Entities.User;
import com.example.schedulerv8.Utils.PasswordUtil;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


public class MyApplicationDataClass extends Application {
    private Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(this);

        MutableLiveData<Boolean> isEmptyLiveData = new MutableLiveData<>();
        new Handler(Looper.getMainLooper()).post(() -> {
            repository.getAllUsers().observeForever(new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    isEmptyLiveData.setValue(users.isEmpty());
                    repository.getAllUsers().removeObserver(this);
                }
            });
        });


        isEmptyLiveData.observeForever(isEmpty -> {
            if (isEmpty) {
                createSampleUsers();
                createSampleTerms();
                createSampleCourses();
                createSampleAssessments();

                repository.getAllUsers().observeForever(new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        for (User user : users) {
                            Log.d("MyApplicationDataClass", user.toString());
                        }
                        repository.getAllUsers().removeObserver(this);
                    }
                });
            }
        });
    }

    private void createSampleUsers() {

        User sampleUser1 = new User (true, "Adm!n", "password1");
        User sampleUser2 = new User(false, "Number2", "password2");
        User sampleUser3 = new User(false, "Number3", "password3");
        User sampleUser4 = new User(false, "Number4", "password4");
        User sampleUser5 = new User(false, "Number5", "password5");

        repository.insert(sampleUser1);
        repository.insert(sampleUser2);
        repository.insert(sampleUser3);
        repository.insert(sampleUser4);
        repository.insert(sampleUser5);
    }

    private void createSampleTerms(){
        //Admin Sample Data, User ID 1
        Term adminTermSample = new Term(1, "Admin Sample Term", "08/01/2023", "08/28/2023", "Adm!n");
        //User ID 2 sample data, 1 term, 2 courses, 2 assessments.
        Term user2SampleTerm = new Term( 2,"Sample Term User 2", "08/20/2023", "09/30/2023", "Number2");
        //User ID 3 sample data, 1 term, 2 courses, 2 assessments.
        Term user3SampleTerm = new Term(3, "Sample Term User 3", "08/25/2023", "10/10/2023", "Number3");
        //User ID 4 sample data, 1 term, 2 course, 2 assessments.
        Term user4SampleTerm = new Term(4, "Sample Term User 4", "10/12/2023", "11/29/2023", "Number4");
        //User ID 5 sample data, 1 term, 2 course, 2 assessments.
        Term user5SampleTerm = new Term(5, "Sample Term User 4", "10/12/2023", "11/29/2023", "Number5");

        repository.insert(adminTermSample);
        repository.insert(user2SampleTerm);
        repository.insert(user3SampleTerm);
        repository.insert(user4SampleTerm);
        repository.insert(user5SampleTerm);
    }

    private void createSampleCourses(){
        //sample async for admin
        Course.AsynchronousCourse adminSampleCourseAsyn = new Course.AsynchronousCourse(
                1, 1,  "Intro to Administration", "08/01/2023", "08/28/2023", "IN PROGRESS", "Self", "Self", "Self", "Course Note", "Adm!n",
                "No", "N/A"
        );
        //sample sync for admin
        Course.SynchronousCourse adminSampleCourseSyn = new Course.SynchronousCourse(
                1, 2, "Admin meeting block", "08/12/2023", "08/22/2023", "PLAN TO TAKE", "Self", "Self", "admin@group.email", "notes", "Adm!n",
                "MWF 9:00-11:00AM PST");
        //sample sync for user 2
        Course.SynchronousCourse sampleCourseUser2Sync = new Course.SynchronousCourse(2, 3, "Sample Synchronous user 2", "08/20/2023", "09/20/2023", "PLAN TO TAKE", "Ex Name", "Ex Phone", "Ex Email", "Ex Note Text", "Number2",
                 "MWF 9:00–11:00AM PST");
        //sample async for user 2
        Course.AsynchronousCourse sampleCourseUser2Async = new Course.AsynchronousCourse(2, 4, "Sample Asynchronous user 2", "08/21/2023", "09/25/2023", "PLAN TO TAKE", "Ex Name2", "Ex Phone2", "Ex Email2", "Ex Note Text 2", "Number2",
               "No", "N/A");
        //sample sync for user 3
        Course.SynchronousCourse sampleCourseUser3Sync = new Course.SynchronousCourse(3, 5, "Sample Synchronous User 3", "08/25/2023", "09/15/2023", "PLAN TO TAKE", "Ex Name3", "Ex Phone3", "Ex Email3", "Ex Note Text 3", "Number3",
                "MWF 9:00–11:00AM PST");
        //sample async for user 3
        Course.AsynchronousCourse sampleCourseUser3Async = new Course.AsynchronousCourse(3, 6, "Sample Asynchronous User 3", "09/20/2023", "10/10/2023", "PLAN TO TAKE", "Ex Name4", "Ex Phone4", "Ex Email4", "Ex Note Text 4", "Number3",
                "No", "N/A");
        //sample sync for user 4
        Course.SynchronousCourse sampleCourseUser4Sync = new Course.SynchronousCourse(4, 7, "Sample Synchronous User 4", "10/12/2023", "10/31/2023", "PLAN TO TAKE", "Ex Name5", "Ex Phone5", "Ex Email5", "Ex Note Text 5", "Number4",
                "MWF 9:00–11:00AM PST");
        //sample async for user 4
        Course.AsynchronousCourse sampleCourseUser4Async = new Course.AsynchronousCourse(4, 8, "Sample Asynchronous User 4", "11/01/2023", "11/29/2023", "PLAN TO TAKE", "Ex Name6", "Ex Phone6", "Ex Email6", "Ex Note Text 6", "Number4",
                "No", "N/A");
        //sample sync for user 5
        Course.SynchronousCourse sampleCourseUser5Sync = new Course.SynchronousCourse(5, 9, "Sample Synchronous User 5", "12/01/2023", "12/31/2023", "PLAN TO TAKE", "Ex Name7", "Ex Phone7", "Ex Email7", "Ex Note Text 7", "Number5",
                "MWF 9:00–11:00AM PST");
        //sample async for user 5
        Course.AsynchronousCourse sampleCourseUser5Async = new Course.AsynchronousCourse(5, 10, "Sample Asynchronous User 5", "12/15/2023", "12/31/2023", "PLAN TO TAKE", "Ex Name8", "Ex Phone8", "Ex Email8", "Ex Note Text 8", "Number5",
                "No", "N/A");

        repository.insertAsyncCourse(adminSampleCourseAsyn);
        repository.insertSyncCourse(adminSampleCourseSyn);
        repository.insertSyncCourse(sampleCourseUser2Sync);
        repository.insertAsyncCourse(sampleCourseUser2Async);
        repository.insertSyncCourse(sampleCourseUser3Sync);
        repository.insertAsyncCourse(sampleCourseUser3Async);
        repository.insertSyncCourse(sampleCourseUser4Sync);
        repository.insertAsyncCourse(sampleCourseUser4Async);
        repository.insertSyncCourse(sampleCourseUser5Sync);
        repository.insertAsyncCourse(sampleCourseUser5Async);
    }

    private void createSampleAssessments(){
        Assessment adminAssessmentSample = new Assessment(1, 1, "Admin Sample Assessment", "08/11/2023", "08/11/2023", "Performance Assessment", "Adm!n");
        Assessment sampleAssessment1 = new Assessment(2, 3, "Sample Assessment 1", "06/30/2023", "06/30/2023", "Objective Assessment", "Number2");
        Assessment sampleAssessment2 = new Assessment(3, 4, "Sample Assessment 2", "09/30/2023", "09/30/2023", "Performance Assessment", "Number2");
        Assessment sampleAssessment3 = new Assessment(4, 5, "Sample Assessment 3", "09/12/2023", "09/12/2023", "Objective Assessment", "Number3");
        Assessment sampleAssessment4 = new Assessment(5, 6, "Sample Assessment 4", "10/10/2023", "10/10/2023", "Performance Assessment", "Number3");
        Assessment sampleAssessment5 = new Assessment(6, 7, "Sample Assessment 5", "10/31/2023", "10/31/2023", "Objective Assessment", "Number4");
        Assessment sampleAssessment6 = new Assessment(7, 8, "Sample Assessment 6", "11/29/2023", "11/29/2023", "Performance Assessment", "Number4");
        Assessment sampleAssessment7 = new Assessment(8, 9, "Sample Assessment 7", "12/31/2023", "10/31/2023", "Objective Assessment", "Number5");
        Assessment sampleAssessment8 = new Assessment(9, 10, "Sample Assessment 8", "12/31/2023", "12/31/2023", "Performance Assessment", "Number5");


        repository.insert(adminAssessmentSample);
        repository.insert(sampleAssessment1);
        repository.insert(sampleAssessment2);
        repository.insert(sampleAssessment3);
        repository.insert(sampleAssessment4);
        repository.insert(sampleAssessment5);
        repository.insert(sampleAssessment6);
        repository.insert(sampleAssessment7);
        repository.insert(sampleAssessment8);
    }


}
