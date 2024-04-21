package com.mygdx.game;

import java.util.List;

public class SideScrollerManager {
    private List<SideScroller> levels;

    public void loadLevels() {
        // Load game levels from external source (e.g., JSON files, databases)

    }
    public SideScroller getLevel(int index) {
        return levels.get(index);
    }

}
