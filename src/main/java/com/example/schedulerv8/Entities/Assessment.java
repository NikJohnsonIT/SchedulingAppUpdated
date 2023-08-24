package com.example.schedulerv8.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int assessmentId;
    @ColumnInfo(name = "assessment_course_id")
    private int courseId;
    @ColumnInfo(name = "assessment_title")
    private String assessmentTitle;
    @ColumnInfo(name = "assessment_start_date")
    private String assessmentStartDate;
    @ColumnInfo(name = "assessment_end_date")
    private String assessmentEndDate;
    @ColumnInfo(name = "assessment_type")
    private String assessmentType;
    @ColumnInfo(name = "created_by")
    private String createdBy;

    public Assessment(String assessmentTitle, int courseId, String assessmentStartDate, String assessmentEndDate, String assessmentType){
        this.assessmentTitle = assessmentTitle;
        this.courseId = courseId;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
    }
    public Assessment(int assessmentId, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentType){
        this.assessmentId = assessmentId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
    }
    public Assessment(int assessmentId, int courseId, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentType){
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
    }
    public Assessment(int assessmentId, int courseId, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentType, String createdBy){
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
        this.createdBy = createdBy;
    }

    public Assessment(){}

    public void setAssessmentId(int assessmentId){
        this.assessmentId = assessmentId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public String getAssessmentStartDate(){
        return assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }
    public String getAssessmentType(){
        return assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }
    public String getCreatedBy(){return createdBy;}

    public enum AssessmentType{
        OBJECTIVE,
        PERFORMANCE
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


}
