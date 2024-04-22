package com.mygdx.game;

import com.mygdx.game.Screens.SideScrollerScreen;

import java.util.List;

public class SideScrollerManager {
    private List<SideScrollerScreen> sideScrollers;

    public void loadLevels() {
        // Load game levels from external source (e.g., JSON files, databases)

    }
    public SideScrollerScreen getLevel(int index) {
        return sideScrollers.get(index);
    }

}
