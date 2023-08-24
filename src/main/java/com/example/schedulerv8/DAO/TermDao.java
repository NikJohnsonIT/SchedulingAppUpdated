package com.example.schedulerv8.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import com.example.schedulerv8.Entities.Term;


@Dao
public interface TermDao {
    @Insert
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM terms")
    LiveData<List<Term>> getAllTerms();

    @Query("DELETE FROM terms")
    void deleteAllTerms();

    @Query("SELECT * FROM terms WHERE term_id = :termId")
    LiveData<Term> getTermById(int termId);

    @Query("SELECT COUNT(*) FROM terms WHERE created_by = :userId")
    LiveData<Integer> countTermsCreatedByUser(String userId);

    @Query("SELECT * FROM terms WHERE created_by = :userId")
    LiveData<List<Term>> getTermsCreatedByUser(String userId);

    @Query("SELECT MAX(term_id) FROM terms")
    int getMaxTermId();

}