package com.example.schedulerv8.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public abstract class Course {
    @ColumnInfo(name = "course_term_id")
    protected int termId;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    protected int courseId;
    @NonNull
    @ColumnInfo(name = "course_title")
    protected String courseTitle;

    @ColumnInfo(name = "course_start_date")
    protected String courseStartDate;
    @ColumnInfo(name = "course_end_date")
    protected String courseEndDate;
    @ColumnInfo(name = "status")
    protected String status;
    @ColumnInfo(name = "instructor_name")
    protected String courseInstructorName;
    @ColumnInfo(name = "instructor_phone")
    protected String courseInstructorPhone;
    @ColumnInfo(name = "instructor_email")
    protected String courseInstructorEmail;
    @ColumnInfo(name = "course_note")
    protected String courseNote;
    @ColumnInfo(name = "created_by")
    protected String createdBy;
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @ColumnInfo(name = "course_type")
    protected String courseType;

    public abstract String getCourseTypeDescription();
    public String getCourseType() {return courseType;}
    public void setCourseType(String courseType) {this.courseType = courseType;}

    @Entity(tableName = "synchronous_courses")
    public static class SynchronousCourse extends Course {
        @ColumnInfo(name = "meeting_days_times")
        private String meetingDaysTimes;
        public String getMeetingDaysTimes() {return meetingDaysTimes;}
        public void setMeetingDaysTimes(String meetingDaysTimes) {this.meetingDaysTimes = meetingDaysTimes;}
        @Override
        public String getCourseTypeDescription() {
            return "Synchronous Course";
        }

        public SynchronousCourse(int termId, int courseId, String courseTitle, String courseStartDate, String courseEndDate, String status, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNote, String createdBy, String meetingDaysTimes){
            this.termId = termId;
            this.courseId = courseId;
            this.courseTitle = courseTitle;
            this.courseStartDate = courseStartDate;
            this.courseEndDate = courseEndDate;
            this.status = status;
            this.courseInstructorName = courseInstructorName;
            this.courseInstructorPhone = courseInstructorPhone;
            this.courseInstructorEmail = courseInstructorEmail;
            this.courseNote = courseNote;
            this.createdBy = createdBy;
            this.meetingDaysTimes = meetingDaysTimes;
            this.courseType = "SYNCHRONOUS";
        }
    }

    @Entity(tableName = "asynchronous_courses")
    public static class AsynchronousCourse extends Course {
        @ColumnInfo(name = "live_support_available")
        private String liveSupportAvailable;

        @ColumnInfo(name = "support_days_times")
        private String supportDaysTimes;

        public String getLiveSupportAvailable() {return  liveSupportAvailable;}
        public void setLiveSupportAvailable(String liveSupportAvailable) { this.liveSupportAvailable = liveSupportAvailable;}
        public String getSupportDaysTimes(){return supportDaysTimes;}
        public void setSupportDaysTimes(String supportDaysTimes) {this.supportDaysTimes = supportDaysTimes;}

        @Override
        public String getCourseTypeDescription(){
            return "Asynchronous Course";
        }
        public AsynchronousCourse(int termId, int courseId, String courseTitle, String courseStartDate, String courseEndDate, String status, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNote, String createdBy, String liveSupportAvailable, String supportDaysTimes){
            this.termId = termId;
            this.courseId = courseId;
            this.courseTitle = courseTitle;
            this.courseStartDate = courseStartDate;
            this.courseEndDate = courseEndDate;
            this.status = status;
            this.courseInstructorName = courseInstructorName;
            this.courseInstructorPhone = courseInstructorPhone;
            this.courseInstructorEmail = courseInstructorEmail;
            this.courseNote = courseNote;
            this.createdBy = createdBy;
            this.liveSupportAvailable = liveSupportAvailable;
            this.supportDaysTimes = supportDaysTimes;
            this.courseType = "ASYNCHRONOUS";
        }
    }
//    @ColumnInfo(name = "live_support_available")
//    private String liveSupportAvailable; //only for Asynchronous
//
//    @ColumnInfo(name = "support_days_times")
//    private String supportDaysTimes; //only for Asynchronous
//
//    @ColumnInfo(name = "meeting_days_times")
//    private String meetingDaysTimes; //only for Synchronous





    public Course(int termId, int courseId, String courseTitle, String courseStartDate, String courseEndDate, String status, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNote, String createdBy, String courseType) {
        this.termId = termId;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.status = status;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.courseNote = courseNote;
        this.createdBy = createdBy;
        this.courseType = courseType;
    }

    public Course(){

    }

    public Course(@NonNull String courseTitle, String courseStartDate, String courseEndDate, @NonNull String status, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNote) {
        this.courseTitle=courseTitle;
        this.courseStartDate=courseStartDate;
        this.courseEndDate=courseEndDate;
        this.status=status;
        this.courseInstructorName=courseInstructorName;
        this.courseInstructorPhone=courseInstructorPhone;
        this.courseInstructorEmail=courseInstructorEmail;
    }

    public Course(int termId, int courseId, String courseTitle, String courseStartDate, String courseEndDate, String status, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, String courseNote, String createdBy) {
       this.termId = termId;
       this.courseId = courseId;
       this.courseTitle = courseTitle;
       this.courseStartDate = courseStartDate;
       this.courseEndDate = courseEndDate;
       this.status = status;
       this.courseInstructorName = courseInstructorName;
       this.courseInstructorPhone = courseInstructorPhone;
       this.courseInstructorEmail = courseInstructorEmail;
       this.courseNote = courseNote;
       this.createdBy = createdBy;
    }

    public int getTermId(){return termId;}
    public void setTermId(int termId) {this.termId=termId;}
    public int getCourseId(){
        return courseId;
    }

    public void setCourseId(int courseId){
        this.courseId=courseId;
    }

    public String getCourseTitle(){
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle){
        this.courseTitle=courseTitle;
    }


    public String getCourseStartDate(){
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate){
        this.courseStartDate=courseStartDate;
    }

    public String getCourseEndDate(){
        return courseEndDate;
    }
    public void setCourseEndDate(String courseEndDate){
        this.courseEndDate=courseEndDate;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getCourseInstructorName(){
        return courseInstructorName;
    }

    public void setCourseInstructorName(String courseInstructorName){
        this.courseInstructorName=courseInstructorName;
    }

    public String getCourseInstructorPhone(){
        return courseInstructorPhone;
    }

    public void setCourseInstructorPhone(String courseInstructorPhone){
        this.courseInstructorPhone=courseInstructorPhone;
    }

    public String getCourseInstructorEmail(){
        return courseInstructorEmail;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail){
        this.courseInstructorEmail=courseInstructorEmail;
    }

    public String getCourseNote(){return courseNote;}

    public void setCourseNote(String courseNote){
        this.courseNote=courseNote;
    }

    @Override
    public String toString() {
        return this.courseTitle;
    }

}