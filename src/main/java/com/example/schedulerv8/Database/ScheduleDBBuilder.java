package com.example.schedulerv8.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.schedulerv8.DAO.AssessmentDao;
import com.example.schedulerv8.DAO.CourseDao;
import com.example.schedulerv8.DAO.TermDao;
import com.example.schedulerv8.DAO.UserDao;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.Entities.User;

@Database(entities = {Term.class, Course.class, Course.SynchronousCourse.class, Course.AsynchronousCourse.class, Assessment.class, User.class}, version=69, exportSchema = false)
public abstract class ScheduleDBBuilder extends RoomDatabase {
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    public abstract UserDao userDao();

    private static volatile ScheduleDBBuilder INSTANCE;

    static ScheduleDBBuilder getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (ScheduleDBBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleDBBuilder.class, "SchedulerDB.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
