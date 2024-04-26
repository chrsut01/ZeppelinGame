package com.mygdx.game;

//import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class DilemmaFactory {
    private static AssetManager assetManager;
    private JsonValue dilemmaData;

    public DilemmaFactory() {
        assetManager = new AssetManager();
    }

    public static Dilemma createDilemma(String question, List<String> answers, List<String> responses, String imagePath, int correctAnswerIndex) {
        Dilemma dilemma = new Dilemma();
        dilemma.setQuestion(question);
        dilemma.setAnswers(answers);
        dilemma.setResponses(responses);
        dilemma.setImagePath(imagePath);
        dilemma.setCorrectAnswerIndex(correctAnswerIndex);
        return dilemma;
    }
    private List<Dilemma> dilemmas;

    public static List<Dilemma> loadDilemmasFromJson(String jsonFileName) {
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
            List<String> responses = new ArrayList<>();
            for (JsonValue responseData : dilemmaData.get("responses")) {
                responses.add(responseData.asString());
            }
            String imagePath = dilemmaData.getString("imagePath");
            int correctAnswerIndex = dilemmaData.getInt("correctAnswerIndex");

            Dilemma dilemma = createDilemma(question, answers, responses, imagePath, correctAnswerIndex);
            dilemmas.add(dilemma);
        }
        return dilemmas;
    }

    public Dilemma getDilemma(int index) {
        return dilemmas.get(index);
    }
}
