package com.nallanudi.app.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.nallanudi.app.R;
import com.nallanudi.app.data.model.Term;
import com.nallanudi.app.databinding.ItemTermBinding;

public class TermAdapter extends ListAdapter<Term, TermAdapter.TermViewHolder> {

    public interface OnSpeakClickListener { void onSpeak(Term term); }
    public interface OnSaveClickListener  { void onSave(Term term);  }

    private final OnSpeakClickListener speakListener;
    private final OnSaveClickListener  saveListener;

    public TermAdapter(OnSpeakClickListener speakListener, OnSaveClickListener saveListener) {
        super(DIFF_CALLBACK);
        this.speakListener = speakListener;
        this.saveListener  = saveListener;
    }

    private static final DiffUtil.ItemCallback<Term> DIFF_CALLBACK = new DiffUtil.ItemCallback<Term>() {
        @Override
        public boolean areItemsTheSame(@NonNull Term o, @NonNull Term n) {
            return o.getId() == n.getId();
        }
        @Override
        public boolean areContentsTheSame(@NonNull Term o, @NonNull Term n) {
            return o.getEnglishWord().equals(n.getEnglishWord()) && o.isSaved() == n.isSaved();
        }
    };

    @NonNull @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTermBinding b = ItemTermBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TermViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final ItemTermBinding b;

        TermViewHolder(ItemTermBinding binding) {
            super(binding.getRoot());
            this.b = binding;
        }

        void bind(Term term) {
            b.textEnglish.setText(term.getEnglishWord());
            b.textKannada.setText(term.getKannadaMeaning());
            b.textPronunciation.setText(term.getPronunciation());
            b.textSimpleMeaning.setText(term.getSimpleExample());
            b.textExample.setText("ಉದಾಹರಣೆ: " + term.getSimpleExample());
            b.textSubject.setText(term.getSubject());

            // Subject chip colour
            int colorRes;
            switch (term.getSubject()) {
                case "Science":  colorRes = R.color.color_science;  break;
                case "Math":     colorRes = R.color.color_math;     break;
                case "Commerce": colorRes = R.color.color_commerce; break;
                default:         colorRes = R.color.colorPrimary;   break;
            }
            b.textSubject.setBackgroundTintList(
                    b.getRoot().getContext().getColorStateList(colorRes));

            // Bookmark icon
            b.btnSave.setImageResource(
                    term.isSaved() ? R.drawable.ic_bookmark_filled : R.drawable.ic_bookmark_outline);

            b.btnSave.setOnClickListener(v  -> saveListener.onSave(term));
            b.btnSpeak.setOnClickListener(v -> speakListener.onSpeak(term));
        }
    }
}
