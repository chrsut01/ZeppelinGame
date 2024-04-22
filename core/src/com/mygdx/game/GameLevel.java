package com.mygdx.game;

import com.mygdx.game.Screens.SideScrollerScreen;

import java.util.List;

public class GameLevel {
    private SideScrollerScreen sideScrollerScreen;
    private List<Dilemma> dilemmas;
    private int currentDilemmaIndex;

    public GameLevel(SideScrollerScreen sideScrollerScreen, List<Dilemma> dilemmas) {
        this.sideScrollerScreen = sideScrollerScreen;
        this.dilemmas = dilemmas;
        this.currentDilemmaIndex = 0; // Start with the first dilemma
    }

    public SideScrollerScreen getSideScroller() {
        return sideScrollerScreen;
    }

    public Dilemma getNextDilemma() {
        if (currentDilemmaIndex < dilemmas.size()) {
            Dilemma nextDilemma = dilemmas.get(currentDilemmaIndex);
            currentDilemmaIndex++;
            return nextDilemma;
        } else {
            return null; // No more dilemmas
        }
    }

    // Other methods...
}
