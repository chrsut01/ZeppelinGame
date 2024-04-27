package com.mygdx.game;

import java.util.List;
import java.io.Serializable;


public class Dilemma implements Serializable{
    private String question;
    private List<String> answers;
    private List<String> responses;
    private String imagePath;
    private int correctAnswerIndex;

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

   public List<String> getResponses() { return responses; }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) { this.correctAnswerIndex = correctAnswerIndex;
    }
}
