package com.mygdx.game;

//import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class DilemmaFactory {
    private AssetManager assetManager;
    private JsonValue dilemmaData;

    public DilemmaFactory() {
        assetManager = new AssetManager();
    }

    public Dilemma createDilemma(String question, List<String> answers, String imagePath, String consequence) {
        Dilemma dilemma = new Dilemma();
        dilemma.setQuestion(question);
        dilemma.setAnswers(answers);
        dilemma.setImagePath(imagePath);
        dilemma.setConsequence(consequence);
        return dilemma;
    }
    private List<Dilemma> dilemmas;

    public List<Dilemma> loadDilemmasFromJson(String jsonFileName) {
        List<Dilemma> dilemmas = new ArrayList<>();
        assetManager.load(jsonFileName, JsonValue.class);
        assetManager.finishLoading();
        JsonValue jsonValue = assetManager.get(jsonFileName, JsonValue.class);

        for (JsonValue dilemmaData : jsonValue) {
            String question = dilemmaData.getString("question");
            List<String> answers = new ArrayList<>();
            for (JsonValue answerData : dilemmaData.get("answers")) {
                answers.add(answerData.asString());
            }
            String imagePath = dilemmaData.getString("imagePath");
            String consequence = dilemmaData.getString("consequence");

            Dilemma dilemma = createDilemma(question, answers, imagePath, consequence);
            dilemmas.add(dilemma);
        }
        return dilemmas;
    }

  /*  public void loadDilemma(String dilemmaFileName) {
        assetManager.load("json/" + dilemmaFileName, JsonValue.class);
        assetManager.finishLoading();
        dilemmaData = assetManager.get("json/" + dilemmaFileName, JsonValue.class);
    }*/


    public Dilemma getDilemma(int index) {
        return dilemmas.get(index);
    }
}
