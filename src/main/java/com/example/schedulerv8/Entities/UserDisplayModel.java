package com.example.schedulerv8.Entities;

public class UserDisplayModel {
    private String userId;
    private int termsCount;
    private int coursesCount;

    public UserDisplayModel(String userId, int termsCount, int coursesCount) {
        this.userId = userId;
        this.termsCount = termsCount;
        this.coursesCount = coursesCount;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getTermsCount() {
        return termsCount;
    }
    public void setTermsCount(int termsCount) {
        this.termsCount = termsCount;
    }
    public int getCoursesCount() {
        return coursesCount;
    }
    public void setCoursesCount(int coursesCount) {
        this.coursesCount = coursesCount;
    }
}
