package com.nallanudi.app.ui.home;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.nallanudi.app.R;
import com.nallanudi.app.data.model.Term;
import com.nallanudi.app.databinding.FragmentHomeBinding;
import com.nallanudi.app.ui.TermViewModel;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TermViewModel viewModel;
    private TextToSpeech tts;
    private Term currentTerm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
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

        viewModel.wordOfDay.observe(getViewLifecycleOwner(), term -> {
            if (term != null) {
                currentTerm = term;
                binding.textWodEnglish.setText(term.getEnglishWord());
                binding.textWodKannada.setText(term.getKannadaMeaning());
                binding.textWodPronunciation.setText(term.getPronunciation());
                binding.textWodExample.setText(term.getSimpleExample());

                // Subject chip color
                int colorRes;
                switch (term.getSubject()) {
                    case "Science":  colorRes = R.color.color_science;  break;
                    case "Math":     colorRes = R.color.color_math;     break;
                    case "Commerce": colorRes = R.color.color_commerce; break;
                    default:         colorRes = R.color.colorPrimary;   break;
                }
                binding.textWodSubject.setText(term.getSubject());
                binding.textWodSubject.setBackgroundTintList(
                        requireContext().getColorStateList(colorRes));

                // Update save button
                updateSaveButton(term);

                binding.btnWodSpeak.setOnClickListener(v ->
                        tts.speak(term.getEnglishWord(), TextToSpeech.QUEUE_FLUSH, null, null));
            }
        });

        binding.btnWodRefresh.setOnClickListener(v -> viewModel.refreshWordOfDay());

        binding.btnWodSave.setOnClickListener(v -> {
            if (currentTerm != null) {
                viewModel.toggleSave(currentTerm);
                boolean nowSaved = !currentTerm.isSaved();
                String msg = nowSaved ? currentTerm.getEnglishWord() + " ಉಳಿಸಲಾಗಿದೆ!"
                        : currentTerm.getEnglishWord() + " ತೆಗೆದುಹಾಕಲಾಗಿದೆ";
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnGoSearch.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.navigation_search));

        binding.btnGoMylist.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.navigation_mylist));
    }

    private void updateSaveButton(Term term) {
        if (term.isSaved()) {
            binding.btnWodSave.setText("✅ ಉಳಿಸಲಾಗಿದೆ");
        } else {
            binding.btnWodSave.setText("📌 ಉಳಿಸಿ");
        }
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
