package com.mygdx.game;

import com.mygdx.game.Screens.SideScrollerScreen;
import com.mygdx.game.SideScrollers.SideScrollerBulg;
import com.mygdx.game.SideScrollers.SideScrollerEgypt;
import com.mygdx.game.SideScrollers.SideScrollerMed;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    private SideScrollerScreen sideScrollerScreen;
    private DilemmaFactory dilemmaFactory;
    private List<Dilemma> dilemmas;
    private int currentDilemmaIndex;

    public GameLevel(SideScrollerScreen sideScrollerScreen, List<Dilemma> dilemmas) {
        this.sideScrollerScreen = sideScrollerScreen;
        this.dilemmas = dilemmas;
        this.currentDilemmaIndex = 0;
    }

  /*  public List<GameLevel> getGameLevels() {
        return gameLevels;
    }*/
    public SideScrollerScreen getSideScroller() {
        return this.sideScrollerScreen;
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
