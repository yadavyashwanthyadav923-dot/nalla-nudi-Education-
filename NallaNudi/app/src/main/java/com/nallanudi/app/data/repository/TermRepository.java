package com.nallanudi.app.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.nallanudi.app.data.db.AppDatabase;
import com.nallanudi.app.data.db.TermDao;
import com.nallanudi.app.data.model.Term;

import java.util.List;
import java.util.concurrent.Executors;

public class TermRepository {

    private final TermDao termDao;
    // Trigger to force a new random word
    private final MutableLiveData<Integer> wodTrigger = new MutableLiveData<>(0);
    private int triggerCounter = 0;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        termDao = db.termDao();
    }

    public LiveData<List<Term>> searchTerms(String query) {
        return termDao.searchTerms(query);
    }

    public LiveData<List<Term>> searchTermsBySubject(String query, String subject) {
        return termDao.searchTermsBySubject(query, subject);
    }

    public LiveData<List<Term>> getSavedTerms() {
        return termDao.getSavedTerms();
    }

    public LiveData<Term> getWordOfDay() {
        return Transformations.switchMap(wodTrigger, trigger -> termDao.getWordOfDay());
    }

    public void refreshWordOfDay() {
        wodTrigger.setValue(++triggerCounter);
    }

    public void updateTerm(Term term) {
        Executors.newSingleThreadExecutor().execute(() -> termDao.update(term));
    }
}
