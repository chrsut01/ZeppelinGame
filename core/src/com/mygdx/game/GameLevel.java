package com.mygdx.game;

import java.util.List;

public class GameLevel {
    private SideScroller sideScroller;
    private List<Dilemma> dilemmas;

    public GameLevel(SideScroller sideScroller, List<Dilemma> dilemmas) {
        this.sideScroller = sideScroller;
        this.dilemmas = dilemmas;
    }

    // Methods to interact with the side-scroller and dilemmas
    // (e.g., startSideScroller, showNextDilemma, etc.)
}
