package com.mygdx.game;

import com.mygdx.game.DilemmaStuff.Dilemma;
import com.mygdx.game.DilemmaStuff.DilemmaFactory;
import com.mygdx.game.SideScrollerStuff.SideScrollerScreen;

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

    public void setSideScroller(SideScrollerScreen sideScrollerScreen) {
        this.sideScrollerScreen = sideScrollerScreen;
    }

    public Dilemma getNextDilemma() {
        System.out.println("GameLevel getNextDilemma method called");
        if (currentDilemmaIndex < dilemmas.size()) {

            System.out.println("Current dilemma index: " + currentDilemmaIndex);
            System.out.println("Dilemmas size: " + dilemmas.size());

            Dilemma nextDilemma = dilemmas.get(currentDilemmaIndex);
            currentDilemmaIndex++;
            System.out.println("Current dilemma index: " + currentDilemmaIndex);
            System.out.println("Next dilemma: first question: " + nextDilemma.getQuestion());
            return nextDilemma;
        } else {
            return null;
        }
    }

    public SideScrollerScreen getSideScroller() {
        System.out.println("GameLevel getSideScroller method called");
        if (currentDilemmaIndex >= dilemmas.size()) {
            System.out.println("GameLevel getSideScroller method called: currentDilemmaIndex >= dilemmas.size()");
                return sideScrollerScreen;
        } else {
            return null;
        }
    }

    public void setNextDilemma() {
        System.out.println("GameLevel setNextDilemma method called");
        if (currentDilemmaIndex < dilemmas.size()) {
            currentDilemmaIndex++;
        }
    }

    // Other methods...
}
