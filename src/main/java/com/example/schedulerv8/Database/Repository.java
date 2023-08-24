package com.example.schedulerv8.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schedulerv8.DAO.AssessmentDao;
import com.example.schedulerv8.DAO.CourseDao;
import com.example.schedulerv8.DAO.TermDao;
import com.example.schedulerv8.DAO.UserDao;
import com.example.schedulerv8.Entities.Assessment;
import com.example.schedulerv8.Entities.Course;
import com.example.schedulerv8.Entities.CourseDTO;
import com.example.schedulerv8.Entities.Term;
import com.example.schedulerv8.Entities.User;
import com.example.schedulerv8.Entities.UserDisplayModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private AssessmentDao mAssessmentDao;
    private CourseDao mCourseDao;
    private TermDao mTermDao;
    private UserDao mUserDao;
    private List<User> mAllUsers;

    private int mCountUserTerms;

    private int mCountUserCourses;
    private List<Term> mAllTerms;
    private List<Term> mUserTerms;
    private List<Assessment> mAllAssessments;
    private List<Assessment> mUserAssessments;
    private List<Course> mAllCourses;
    private List<Course> mAllAvailCourses;

    private List<Course> mAssociatedCourses;
    private List<Assessment> mAssocAssess;
    private List<Course> mUserCourses;
    private int mTotalCoursesCount;
    private int mCoursesByTypeCount;
    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        ScheduleDBBuilder db = ScheduleDBBuilder.getDatabase(application);
        mTermDao = db.termDao();
        mAssessmentDao = db.assessmentDao();
        mCourseDao = db.courseDao();
        mUserDao = db.userDao();
    }

    public LiveData<Integer> getTermsCreatedByUserCount(String userId) {
        return mTermDao.countTermsCreatedByUser(userId);
    }

    public LiveData<List<String>> getAllUserIds() {
        return mUserDao.getAllUserIds();
    }

    public LiveData<List<Term>> getTermsCreatedByUser(String userId) {
        return mTermDao.getTermsCreatedByUser(userId);
    }

    public LiveData<List<Term>> getAllTerms() {
        return mTermDao.getAllTerms();
    }

    public void insert(Term term) {
        databaseExecutor.execute(() ->
                mTermDao.insert(term));
    }

    public void update(Term term) {
        databaseExecutor.execute(() ->
                mTermDao.update(term));
    }

    public void delete(Term term) {
        databaseExecutor.execute(() ->
                mTermDao.delete(term));
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return mAssessmentDao.getAllAssessments();
    }

    public LiveData<Assessment> getAssessmentById(int id) {
        return mAssessmentDao.getAssessmentById(id);
    }

    public LiveData<Course> getCourseById(int courseId) {
        MediatorLiveData<Course> combinedCourse = new MediatorLiveData<>();

        LiveData<Course.SynchronousCourse> syncCourse = mCourseDao.getSyncCourseById(courseId);
        LiveData<Course.AsynchronousCourse> asyncCourse = mCourseDao.getAsyncCourseById(courseId);

        combinedCourse.addSource(syncCourse, value -> {
            if (value != null) {
                combinedCourse.setValue(value);
            } else if (asyncCourse.getValue() != null) {
                combinedCourse.setValue(asyncCourse.getValue());
            }
        });

        combinedCourse.addSource(asyncCourse, value -> {
            if (value != null) {
                combinedCourse.setValue(value);
            } else if (syncCourse.getValue() != null) {
                combinedCourse.setValue(syncCourse.getValue());
            }
        });
        return combinedCourse;
    }

    public void insert(Assessment assessment) {
        databaseExecutor.execute(() ->
                mAssessmentDao.insertAssessment(assessment));
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() ->
                mAssessmentDao.updateAssessment(assessment));
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(() ->
                mAssessmentDao.deleteAssessment(assessment));
    }

//    public LiveData<List<Course>> getAllCourses() {
//        return mCourseDao.getAllCourses();
//    }
public LiveData<List<Course>> getAllCourses() {
    MediatorLiveData<List<Course>> combinedCourses = new MediatorLiveData<>();

    LiveData<List<Course.SynchronousCourse>> syncCourses = mCourseDao.getAllSyncCourses();
    LiveData<List<Course.AsynchronousCourse>> asyncCourses = mCourseDao.getAllAsyncCourses();

    combinedCourses.addSource(syncCourses, syncValue -> {
        List<Course> courses = new ArrayList<>();
        if (syncValue != null) {
            courses.addAll(syncValue);
        }
        if (asyncCourses.getValue() != null) {
            courses.addAll(asyncCourses.getValue());
        }
        combinedCourses.setValue(courses);
    });

    combinedCourses.addSource(asyncCourses, asyncValue -> {
        List<Course> courses = new ArrayList<>();
        if (asyncValue != null) {
            courses.addAll(asyncValue);
        }
        if (syncCourses.getValue() != null) {
            courses.addAll(syncCourses.getValue());
        }
        combinedCourses.setValue(courses);
    });
    return combinedCourses;
}

//    public LiveData<List<Course>> getUserCourses(String userId) {
//        return mCourseDao.getUserCourses(userId);
//    }
    public LiveData<List<Course>> getUserCourses(String userId) {
        MediatorLiveData<List<Course>> combinedCourses = new MediatorLiveData<>();

        LiveData<List<Course.SynchronousCourse>> syncCourses = mCourseDao.getSyncCoursesByUser(userId);
        LiveData<List<Course.AsynchronousCourse>> asyncCourses = mCourseDao.getAsyncCoursesByUser(userId);

        combinedCourses.addSource(syncCourses, syncValue -> {
            List<Course> courses = new ArrayList<>();
            if (syncValue != null) {
                courses.addAll(syncValue);
            }
            if (asyncCourses.getValue() != null) {
                courses.addAll(asyncCourses.getValue());
            }
            combinedCourses.setValue(courses);
        });

        combinedCourses.addSource(asyncCourses, asyncValue -> {
            List<Course> courses = new ArrayList<>();
            if (asyncValue != null) {
                courses.addAll(asyncValue);
            }
            if (syncCourses.getValue() != null) {
                courses.addAll(syncCourses.getValue());
            }
            combinedCourses.setValue(courses);
        });
        return combinedCourses;
    }

//    public LiveData<Integer> countTotalCoursesAllUsers() {
//        return mCourseDao.countTotalCourses();
//    }

    public LiveData<Integer> countTotalCourses() {
        MediatorLiveData<Integer> totalCourses = new MediatorLiveData<>();
        LiveData<Integer> syncCoursesCount = mCourseDao.countSyncCourses();
        LiveData<Integer> asyncCoursesCount = mCourseDao.countAsyncCourses();

        totalCourses.addSource(syncCoursesCount, count -> {
            Integer asyncCount = asyncCoursesCount.getValue();
            if (asyncCount != null) {
                totalCourses.setValue(count + asyncCount);
            }
        });

        totalCourses.addSource(asyncCoursesCount, count -> {
            Integer syncCount = syncCoursesCount.getValue();
            if (syncCount != null) {
                totalCourses.setValue(count + syncCount);
            }
        });
        return totalCourses;
    }
    public LiveData<Integer> countCoursesAsync() {
        return mCourseDao.countAsyncCourses();
    }

    public LiveData<Integer> countCoursesSync() {
        return mCourseDao.countSyncCourses();
    }

//    public LiveData<Integer> getCoursesCreatedByUserCount(String userId) {
//        return mCourseDao.coursesCreatedByUserCount(userId);
//    }

    public LiveData<Integer> coursesCreatedByUserCount(String userId) {
        MediatorLiveData<Integer> totalCoursesByUser = new MediatorLiveData<>();
        LiveData<Integer> syncCoursesByUserCount = mCourseDao.countSyncCoursesByUser(userId);
        LiveData<Integer> asyncCoursesByUserCount = mCourseDao.countAsyncCoursesByUser(userId);

        totalCoursesByUser.addSource(syncCoursesByUserCount, count -> {
            Integer asyncCount = asyncCoursesByUserCount.getValue();
            if (asyncCount != null) {
                totalCoursesByUser.setValue(count + asyncCount);
            }
        });

        totalCoursesByUser.addSource(asyncCoursesByUserCount, count -> {
            Integer syncCount = syncCoursesByUserCount.getValue();
            if (syncCount != null) {
                totalCoursesByUser.setValue(count + syncCount);
            }
        });
        return totalCoursesByUser;
    }

    public void insertAsyncCourse(Course.AsynchronousCourse asyncCourse) {
        databaseExecutor.execute(() -> mCourseDao.insertAsyncCourse(asyncCourse));
    }

    public void insertSyncCourse(Course.SynchronousCourse syncCourse) {
        databaseExecutor.execute(() -> mCourseDao.insertSyncCourse(syncCourse));
    }

    public void updateAsyncCourse(Course.AsynchronousCourse asyncCourse) {
        databaseExecutor.execute(() -> mCourseDao.updateAsyncCourse(asyncCourse));
    }

    public void updateSyncCourse(Course.SynchronousCourse syncCourse) {
        databaseExecutor.execute(() -> mCourseDao.updateSyncCourse(syncCourse));
    }

    public void deleteAsyncCourse(Course.AsynchronousCourse asyncCourse) {
        databaseExecutor.execute(() -> mCourseDao.deleteAsyncCourse(asyncCourse));
    }

    public void deleteSyncCourse(Course.SynchronousCourse syncCourse) {
        databaseExecutor.execute(() -> mCourseDao.deleteSyncCourse(syncCourse));
    }

//    public LiveData<List<Course>> getAssociatedCourses(int termId) {
//        return mCourseDao.getAssocCourses(termId);
//    }
    public LiveData<List<Course>> getAssocCourses(int termId) {
        MediatorLiveData<List<Course>> combinedCourses = new MediatorLiveData<>();

        LiveData<List<Course.SynchronousCourse>> syncCourses = mCourseDao.getSyncCoursesByTermId(termId);
        LiveData<List<Course.AsynchronousCourse>> asyncCourses = mCourseDao.getAsyncCoursesByTermId(termId);

        combinedCourses.addSource(syncCourses, syncValue -> {
            List<Course> courses = new ArrayList<>();
            if (syncValue != null) {
                courses.addAll(syncValue);
            }
            if (asyncCourses.getValue() != null) {
                courses.addAll(asyncCourses.getValue());
            }
            combinedCourses.setValue(courses);
        });

        combinedCourses.addSource(asyncCourses, asyncValue -> {
            List<Course> courses = new ArrayList<>();
            if (asyncValue != null) {
                courses.addAll(asyncValue);
            }
            if (syncCourses.getValue() != null) {
                courses.addAll(syncCourses.getValue());
            }
            combinedCourses.setValue(courses);
        });
        return combinedCourses;
    }

    public LiveData<List<Assessment>> getAssocAssess(int courseId) {
        return mAssessmentDao.getAssocAssess(courseId);
    }

    public void insert(User user) {
        databaseExecutor.execute(() ->
                mUserDao.insert(user));
    }

    public void update(User user) {
        databaseExecutor.execute(() ->
                mUserDao.update(user));
    }

    public void delete(User user) {
        databaseExecutor.execute(() ->
                mUserDao.delete(user));
    }

    public LiveData<List<User>> getAllUsers() {
        return mUserDao.getAllUsers();
    }

    public LiveData<User> findUserByIDandPW(String userId, String password) {
        return mUserDao.findUserByIDandPW(userId, password);
    }

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<List<CourseDTO>> searchCourses(String query) {
        return mCourseDao.searchCoursesByName("%" + query + "%");
    }

    public LiveData<List<Assessment>> getAssessmentsCreatedByUser(String userId) {
        return mAssessmentDao.getAssessmentsCreatedByUser(userId);
    }

    public LiveData<Term> getTermById(int termId) {
        return mTermDao.getTermById(termId);
    }
}
