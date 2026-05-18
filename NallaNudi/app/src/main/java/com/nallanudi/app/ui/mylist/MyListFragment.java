package com.nallanudi.app.ui.mylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nallanudi.app.databinding.FragmentMylistBinding;
import com.nallanudi.app.ui.TermViewModel;
import com.nallanudi.app.ui.search.TermAdapter;

public class MyListFragment extends Fragment {

    private FragmentMylistBinding binding;
    private TermViewModel viewModel;
    private TermAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMylistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(TermViewModel.class);

        adapter = new TermAdapter(
                term -> Toast.makeText(requireContext(), "Speak from Search screen", Toast.LENGTH_SHORT).show(),
                term -> {
                    viewModel.toggleSave(term);
                    Toast.makeText(requireContext(), term.getEnglishWord() + " ತೆಗೆದುಹಾಕಲಾಗಿದೆ", Toast.LENGTH_SHORT).show();
                }
        );

        binding.recyclerSaved.setAdapter(adapter);

        viewModel.savedTerms.observe(getViewLifecycleOwner(), terms -> {
            adapter.submitList(terms);
            binding.textEmptyList.setVisibility(terms.isEmpty() ? View.VISIBLE : View.GONE);
            binding.textCount.setText(terms.size() + " ಪದಗಳು ಉಳಿಸಲಾಗಿದೆ");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
