package com.nallanudi.app.ui.search;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.nallanudi.app.databinding.FragmentSearchBinding;
import com.nallanudi.app.ui.TermViewModel;

import java.util.Locale;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private TermViewModel viewModel;
    private TermAdapter adapter;
    private TextToSpeech tts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TermViewModel.class);

        tts = new TextToSpeech(requireContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });

        // RecyclerView + adapter
        adapter = new TermAdapter(
                term -> {
                    if (tts != null) {
                        tts.speak(term.getEnglishWord(), TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                },
                term -> {
                    viewModel.toggleSave(term);
                    String msg = term.isSaved()
                            ? term.getEnglishWord() + " ತೆಗೆದುಹಾಕಲಾಗಿದೆ"
                            : term.getEnglishWord() + " ಉಳಿಸಲಾಗಿದೆ!";
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
                }
        );

        binding.recyclerTerms.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerTerms.setAdapter(adapter);

        // Search box watcher
        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Chip filters
        binding.chipAll.setOnClickListener(v      -> selectChip("All"));
        binding.chipScience.setOnClickListener(v  -> selectChip("Science"));
        binding.chipMath.setOnClickListener(v     -> selectChip("Math"));
        binding.chipCommerce.setOnClickListener(v -> selectChip("Commerce"));

        // Observe results
        viewModel.searchResults.observe(getViewLifecycleOwner(), terms -> {
            adapter.submitList(terms);
            binding.textEmpty.setVisibility(terms.isEmpty() ? View.VISIBLE : View.GONE);
        });

        // Trigger initial load
        viewModel.setSearchQuery("");
    }

    private void selectChip(String subject) {
        binding.chipAll.setChecked(subject.equals("All"));
        binding.chipScience.setChecked(subject.equals("Science"));
        binding.chipMath.setChecked(subject.equals("Math"));
        binding.chipCommerce.setChecked(subject.equals("Commerce"));
        viewModel.setSubjectFilter(subject);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        binding = null;
    }
}
