package com.nallanudi.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String englishWord;
    public String kannadaMeaning;
    public String simpleExample;
    public String subject; // Science, Math, Commerce
    public String pronunciation;
    public boolean isSaved;

    public Term(String englishWord, String kannadaMeaning, String simpleExample,
                String subject, String pronunciation) {
        this.englishWord = englishWord;
        this.kannadaMeaning = kannadaMeaning;
        this.simpleExample = simpleExample;
        this.subject = subject;
        this.pronunciation = pronunciation;
        this.isSaved = false;
    }

    public int getId() { return id; }
    public String getEnglishWord() { return englishWord; }
    public String getKannadaMeaning() { return kannadaMeaning; }
    public String getSimpleExample() { return simpleExample; }
    public String getSubject() { return subject; }
    public String getPronunciation() { return pronunciation; }
    public boolean isSaved() { return isSaved; }
    public void setSaved(boolean saved) { isSaved = saved; }
}
