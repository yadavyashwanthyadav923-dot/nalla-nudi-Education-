package com.nallanudi.app.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.nallanudi.app.data.model.Term;
import com.nallanudi.app.data.repository.TermRepository;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private final TermRepository repository;

    // Both query and subject are held separately
    private final MutableLiveData<String> searchQuery    = new MutableLiveData<>("");
    private final MutableLiveData<String> selectedSubject = new MutableLiveData<>("All");

    // MediatorLiveData combines both into one trigger pair
    private final MediatorLiveData<String[]> trigger = new MediatorLiveData<>();

    public final LiveData<List<Term>> searchResults;
    public final LiveData<List<Term>> savedTerms;
    public final LiveData<Term>       wordOfDay;

    public TermViewModel(Application application) {
        super(application);
        repository = new TermRepository(application);

        // Fire whenever either query or subject changes
        trigger.addSource(searchQuery,     q -> trigger.setValue(new String[]{q, selectedSubject.getValue()}));
        trigger.addSource(selectedSubject, s -> trigger.setValue(new String[]{searchQuery.getValue(), s}));

        searchResults = Transformations.switchMap(trigger, pair -> {
            String q = pair[0] != null ? pair[0] : "";
            String s = pair[1] != null ? pair[1] : "All";
            return s.equals("All")
                    ? repository.searchTerms(q)
                    : repository.searchTermsBySubject(q, s);
        });

        savedTerms = repository.getSavedTerms();
        wordOfDay  = repository.getWordOfDay();
    }

    public void setSearchQuery(String query)   { searchQuery.setValue(query); }
    public void setSubjectFilter(String subject) { selectedSubject.setValue(subject); }
    public String getSelectedSubject()          { return selectedSubject.getValue(); }

    public void refreshWordOfDay() { repository.refreshWordOfDay(); }

    public void toggleSave(Term term) {
        term.setSaved(!term.isSaved());
        repository.updateTerm(term);
    }
}
