package com.example.schedulerv8.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.CourseDTO;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM synchronous_courses WHERE course_id = :courseId")
    LiveData<Course.SynchronousCourse> getSyncCourseById(int courseId);
    @Query("SELECT * FROM asynchronous_courses WHERE course_id = :courseId")
    LiveData<Course.AsynchronousCourse> getAsyncCourseById(int courseId);

    @Query("SELECT * FROM synchronous_courses WHERE course_term_id = :termId")
    LiveData<List<Course.SynchronousCourse>> getSyncCoursesByTermId(int termId);

    @Query("SELECT * FROM asynchronous_courses WHERE course_term_id = :termId")
    LiveData<List<Course.AsynchronousCourse>> getAsyncCoursesByTermId(int termId);


    @Query("SELECT * FROM synchronous_courses WHERE created_by = :userId")
    LiveData<List<Course.SynchronousCourse>> getSyncCoursesByUser(String userId);
    @Query("SELECT * FROM asynchronous_courses WHERE created_by = :userId")
    LiveData<List<Course.AsynchronousCourse>> getAsyncCoursesByUser(String userId);

    @Query("SELECT COUNT(*) FROM synchronous_courses")
    LiveData<Integer> countSyncCourses();

    @Query("SELECT COUNT(*) FROM asynchronous_courses")
    LiveData<Integer> countAsyncCourses();

    @Query("SELECT COUNT(*) FROM synchronous_courses WHERE created_by = :userId")
    LiveData<Integer> countSyncCoursesByUser(String userId);

    @Query("SELECT COUNT(*) FROM asynchronous_courses WHERE created_by = :userId")
    LiveData<Integer> countAsyncCoursesByUser(String userId);


    @Query("SELECT course_term_id AS termId, " +
            "course_id AS courseId, " +
            "course_title AS courseTitle, " +
            "course_start_date AS courseStartDate, " +
            "course_end_date AS courseEndDate, " +
            "status, " +
            "instructor_name AS courseInstructorName, " +
            "instructor_phone AS courseInstructorPhone, " +
            "instructor_email AS courseInstructorEmail, " +
            "course_note AS courseNote, " +
            "created_by AS createedBy, " +
            "course_type AS courseType, " +
            "meeting_days_times AS course_specific_1, " +
            "NULL AS course_specific_2 " +
            "FROM synchronous_courses WHERE course_title LIKE :query" +
    " UNION " +
            "SELECT course_term_id AS termId, " +
            "course_id AS courseId, " +
            "course_title AS courseTitle, " +
            "course_start_date AS courseStartDate, " +
            "course_end_date AS courseEndDate, " +
            "status, " +
            "instructor_name AS courseInstructorName, " +
            "instructor_phone AS courseInstructorPhone, " +
            "instructor_email AS courseInstructorEmail, " +
            "course_note AS courseNote, " +
            "created_by AS createdBy, " +
            "course_type AS courseType, " +
            "live_support_available AS course_specific_1, " +
            "support_days_times AS course_specific_2 FROM asynchronous_courses WHERE course_title LIKE :query")
    LiveData<List<CourseDTO>> searchCoursesByName(String query);

    @Query("DELETE FROM synchronous_courses")
    void deleteAllSyncCourses();

    @Query("DELETE FROM asynchronous_courses")
    void deleteAllAsyncCourses();

    @Query("SELECT * FROM asynchronous_courses")
    LiveData<List<Course.AsynchronousCourse>> getAllAsyncCourses();

    @Query("SELECT * FROM synchronous_courses")
    LiveData<List<Course.SynchronousCourse>> getAllSyncCourses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAsyncCourse(Course.AsynchronousCourse course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSyncCourse(Course.SynchronousCourse course);

    @Update
    void updateAsyncCourse(Course.AsynchronousCourse course);

    @Update
    void updateSyncCourse(Course.SynchronousCourse course);

    @Delete
    void deleteAsyncCourse(Course.AsynchronousCourse course);

    @Delete
    void deleteSyncCourse(Course.SynchronousCourse course);
}
