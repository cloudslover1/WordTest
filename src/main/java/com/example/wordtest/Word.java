package com.example.wordtest;

public class Word {
    private String word;
    private String translate;

    public Word(String word, String translate){
        this.word = word;
        this.translate = translate;
    }

    public String getWord(){return word;}

    public void setWord(String word) {this.word = word;}

    public String getTranslate(){return translate;}

    public void setTranslate(String translate){this.translate = translate;}
}