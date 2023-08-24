package com.example.schedulerv8.Entities;

public class CourseDTO {
    public int termId;
    public int courseId;
    public String courseTitle;
    public String courseStartDate;
    public String courseEndDate;
    public String status;
    public String courseInstructorName;
    public String courseInstructorPhone;
    public String courseInstructorEmail;
    public String courseNote;
    public String createdBy;
    public String courseType;
    public String course_specific_1;
    public String course_specific_2;

    @Override
    public String toString() {
        return courseTitle + " (" + status + ")";
    }

}
