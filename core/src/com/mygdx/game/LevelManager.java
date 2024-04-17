package com.mygdx.game;

import java.util.List;

public class LevelManager {
    private List<GameLevel> levels;

    public void loadLevels() {
        // Load game levels from external source (e.g., JSON files, databases)

    }
    public GameLevel getLevel(int index) {
        return levels.get(index);
    }

}
