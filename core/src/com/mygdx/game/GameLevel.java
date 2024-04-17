package com.mygdx.game;

import java.util.List;
import com.mygdx.game.Entities.Plane;

public class GameLevel {
    private String backgroundImagePath;
    private String tilemapPath;
    private List<Plane> planes;

    public List<Plane> getPlanes() {
            return planes;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    // Constructor, getters, setters

}
