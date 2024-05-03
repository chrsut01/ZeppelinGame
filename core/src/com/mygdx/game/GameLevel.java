package com.mygdx.game;

import com.mygdx.game.DilemmaStuff.Dilemma;
import com.mygdx.game.DilemmaStuff.DilemmaFactory;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.SideScrollers.SideScrollerScreen;

import java.util.List;

public class GameLevel {
    ZeppelinGame game;
    private SideScrollerScreen sideScrollerScreen;
    private DilemmaFactory dilemmaFactory;
    private List<Dilemma> dilemmas;
    private int currentDilemmaIndex;
    public Zeppelin zeppelin;

    public GameLevel(SideScrollerScreen sideScrollerScreen, List<Dilemma> dilemmas) {
        game = ZeppelinGame.getInstance();
        this.sideScrollerScreen = sideScrollerScreen;
        this.dilemmas = dilemmas;
        this.currentDilemmaIndex = 0;
    }


    public Dilemma getNextDilemma() {
      //  System.out.println("GameLevel getNextDilemma method called");
        System.out.println("Current dilemma index before increase: " + currentDilemmaIndex);

        if (currentDilemmaIndex < dilemmas.size()) {
            Dilemma nextDilemma = dilemmas.get(currentDilemmaIndex);
            currentDilemmaIndex++;
            System.out.println("Current dilemma index after increase: " + currentDilemmaIndex);
            System.out.println("GameLevel: " + game.getCurrentLevel() + "   Dilemmas size: " + dilemmas.size());
            return nextDilemma;
        } else {
            System.out.println("GameLevel: getNextDilemma method returning null (because currentDilemmaIndex is greater than dilemmas.size())");
            return null;
        }
    }

    public SideScrollerScreen getSideScroller() {
        System.out.println("GameLevel getSideScroller method called and returning: " + sideScrollerScreen);
        System.out.println("GameLevel: " + this.sideScrollerScreen.toString() + "'s TileMap is: " + sideScrollerScreen.getTilemapFileName());
        return sideScrollerScreen;
    }

    public void setNextDilemma() {
        System.out.println("GameLevel setNextDilemma method called");
        if (currentDilemmaIndex < dilemmas.size()) {
            currentDilemmaIndex++;
        }
    }

    public void setSideScroller(SideScrollerScreen sideScrollerScreen) {
        this.sideScrollerScreen = sideScrollerScreen;
    }

    public String getTilemapFileName() {
        return sideScrollerScreen.getTilemapFileName();
    }
    // Other methods...
}
