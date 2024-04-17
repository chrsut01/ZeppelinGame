package com.mygdx.game;

//import com.badlogic.gdx.scenes.scene2d.ui.List;
import java.util.List;

public class DilemmaManager {
    private List<Dilemma> dilemmas;

    public void loadDilemmas() {
        // Load dilemmas from external source (e.g., JSON files, databases)
    }

    public Dilemma getDilemma(int index) {
        return dilemmas.get(index);
    }
}
