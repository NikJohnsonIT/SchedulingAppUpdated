package com.example.schedulerv8.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_id")
    private int termId;
    @ColumnInfo(name = "term_title")
    private String termTitle;
    @ColumnInfo(name = "term_start_date")
    private String startDate;
    @ColumnInfo(name = "term_end_date")
    private String endDate;

    @ColumnInfo(name = "created_by")
    private String createdBy;

    public Term(String termTitle, String startDate, String endDate) {
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Term(){
    }

    public Term(int termId, String termTitle, String startDate, String endDate){
        this.termId=termId;
        this.termTitle=termTitle;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public Term(int termId, String termTitle, String startDate, String endDate, String createdBy){
        this.termId = termId;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate =endDate;
        this.createdBy = createdBy;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String toString() {

        return termId + " " + termTitle;
    }
}
