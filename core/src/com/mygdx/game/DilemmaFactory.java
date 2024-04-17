package com.mygdx.game;

//import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import java.util.List;

public class DilemmaFactory {

    public static Dilemma createDilemma(String question, List<String> answers, String imagePath, String consequence) {
        Dilemma dilemma = new Dilemma();
        // Set attributes based on parameters
        dilemma.setQuestion(question);
        dilemma.setAnswers(answers);
        dilemma.setImagePath(imagePath);
        dilemma.setConsequence(consequence);
        return dilemma;
    }
    private List<Dilemma> dilemmas;

    public List<Dilemma> loadDilemmasFromJson(String jsonFilePath) {
        // Load dilemmas from JSON file
        Json json = new Json();
        List<Dilemma> dilemmas = json.fromJson(List.class, Dilemma.class, Gdx.files.internal(jsonFilePath));
        return dilemmas;
    }

    public Dilemma getDilemma(int index) {
        return dilemmas.get(index);
    }
}
