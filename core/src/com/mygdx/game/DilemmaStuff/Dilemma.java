package com.mygdx.game.DilemmaStuff;

import java.util.List;
import java.io.Serializable;


public class Dilemma implements Serializable{
    private String question;
    private List<String> answers;
    private List<String> responses;
    private int correctAnswerIndex;

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

   public List<String> getResponses() { return responses; }

    public int getCorrectAnswerIndex() { return correctAnswerIndex; }


}
