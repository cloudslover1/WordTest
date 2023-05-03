package com.example.wordtest;

public class Word {
    private String word;
    private String translate;

    private String wordCategory;

    public Word(String word, String translate, String wordCategory){
        this.word = word;
        this.translate = translate;
        this.wordCategory = wordCategory;
    }

    public String getWord(){return word;}

    public void setWord(String word) {this.word = word;}

    public String getTranslate(){return translate;}

    public void setTranslate(String translate){this.translate = translate;}

    public String getWordCategory() {return wordCategory; }
    public void setWordCategory() {this.wordCategory = wordCategory;}
}
