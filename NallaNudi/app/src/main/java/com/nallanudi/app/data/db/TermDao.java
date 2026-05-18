package com.nallanudi.app.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nallanudi.app.data.model.Term;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Term> terms);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Query("SELECT * FROM terms WHERE (:query = '' OR englishWord LIKE '%' || :query || '%' OR kannadaMeaning LIKE '%' || :query || '%') ORDER BY englishWord ASC")
    LiveData<List<Term>> searchTerms(String query);

    @Query("SELECT * FROM terms WHERE subject = :subject AND (:query = '' OR englishWord LIKE '%' || :query || '%' OR kannadaMeaning LIKE '%' || :query || '%') ORDER BY englishWord ASC")
    LiveData<List<Term>> searchTermsBySubject(String query, String subject);

    @Query("SELECT * FROM terms WHERE isSaved = 1 ORDER BY englishWord ASC")
    LiveData<List<Term>> getSavedTerms();

    @Query("SELECT * FROM terms ORDER BY RANDOM() LIMIT 1")
    LiveData<Term> getWordOfDay();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();
}
