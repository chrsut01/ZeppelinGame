package com.mygdx.game;

import java.util.Dictionary;
import java.util.List;



public class Dilemma {
    private String question;
    private List<String> answers;
    private String imagePath;
    private String consequence;

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

// Constructor, getters, setters


}
