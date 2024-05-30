package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Rectangles.Zeppelin;
import com.mygdx.game.SideScrollers.SideScrollerScreen;

import static com.mygdx.game.Constants.PPM;

public class TileMapHelper {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final SideScrollerScreen sideScrollerScreen;

    public TileMapHelper(SideScrollerScreen sideScrollerScreen) {
        this.sideScrollerScreen = sideScrollerScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load(sideScrollerScreen.getTilemapFileName());

        System.out.println("TileMapHelper: setupMap method called: tilemapFileName is: " + sideScrollerScreen.getTilemapFileName());

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        return mapRenderer;
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

     public boolean overlapsPolygon(PolygonMapObject polygonMapObject, Zeppelin zeppelin) {
        Shape polygonShape = createPolygonShape(polygonMapObject);
        Polygon polygon = polygonMapObject.getPolygon();

        float[] vertices = polygon.getTransformedVertices();
        float minX = vertices[0];
        float minY = vertices[1];
        float maxX = vertices[0];
        float maxY = vertices[1];

        for (int i = 2; i < vertices.length; i += 2) {
            minX = Math.min(minX, vertices[i]);
            minY = Math.min(minY, vertices[i + 1]);
            maxX = Math.max(maxX, vertices[i]);
            maxY = Math.max(maxY, vertices[i + 1]);
        }

        Rectangle polygonBounds = new Rectangle(minX, minY, maxX - minX, maxY - minY);
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX() + 15, zeppelin.getY() + 10, zeppelin.getWidth() - 32, zeppelin.getHeight() - 20);
        boolean overlaps = polygonBounds.overlaps(zeppelinBounds);
        polygonShape.dispose();
        return overlaps;
    }

    public void renderMap(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public PolygonMapObject[] getStaticBody() {
        MapObjects mapObjects = tiledMap.getLayers().get("Collision").getObjects();
        PolygonMapObject[] staticBodies = new PolygonMapObject[mapObjects.getCount()];
        int i = 0;
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                staticBodies[i] = (PolygonMapObject) mapObject;
                i++;
            }
        }
        return staticBodies;
    }
    public void dispose() {

    }
}