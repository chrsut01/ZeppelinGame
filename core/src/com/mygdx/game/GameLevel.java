package com.mygdx.game;

import com.mygdx.game.DilemmaStuff.Dilemma;
import com.mygdx.game.DilemmaStuff.DilemmaFactory;
import com.mygdx.game.DilemmaStuff.DilemmaScreen;
import com.mygdx.game.SideScrollers.SideScrollerScreen;

import java.util.List;

public class GameLevel {
    ZeppelinGame game;
    private SideScrollerScreen sideScrollerScreen;
    private Dilemma dilemma;
    public final List<Dilemma> dilemmas;
    private int currentDilemmaIndex;
    private DilemmaScreen dilemmaScreen;
    private DilemmaScreen currentDilemmaScreen;
    private DilemmaFactory dilemmaFactory;


    public GameLevel(SideScrollerScreen sideScrollerScreen, List<Dilemma> dilemmas) {
        System.out.println("GameLevel constructor called");
        game = ZeppelinGame.getInstance();
        this.sideScrollerScreen = sideScrollerScreen;
        this.dilemmas = dilemmas;
        this.currentDilemmaIndex = 0;
    }


    public Dilemma getNextDilemma() {
        if (currentDilemmaIndex < dilemmas.size()) {
            Dilemma nextDilemma = dilemmas.get(currentDilemmaIndex);
            System.out.println("GameLevel: getNextDilemma method called" + dilemmas.get(currentDilemmaIndex).toString());
           // currentDilemmaIndex++;
            return nextDilemma;
        } else {
            System.out.println("GameLevel: getNextDilemma method returning null (because currentDilemmaIndex is greater than dilemmas.size())");
            return null;
        }
    }


    public void incrementDilemmaIndex() {
        currentDilemmaIndex++;
    }

    public SideScrollerScreen getSideScroller() {
        System.out.println("GameLevel getSideScroller method called and returning: " + sideScrollerScreen);
        return this.sideScrollerScreen;
    }

    public int getCurrentDilemmaIndex() {
        return this.currentDilemmaIndex;
    }

    public void setCurrentDilemmaScreen(DilemmaScreen dilemmaScreen) {
        System.out.println("GameLevel setNextDilemma method called");
        this.currentDilemmaScreen = dilemmaScreen;
    }
    public DilemmaScreen getCurrentDilemmaScreen() {
        return currentDilemmaScreen;
    }

    public void setSideScroller(SideScrollerScreen sideScrollerScreen) {
        this.sideScrollerScreen = sideScrollerScreen;
    }

    public String getTilemapFileName() {
        return sideScrollerScreen.getTilemapFileName();
    }

    public void resetDilemmaIndex() {
        currentDilemmaIndex = 0;
    }
}
