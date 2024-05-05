package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.SideScrollers.SideScrollerScreen;

import static com.mygdx.game.Constants.PPM;

public class TileMapHelper {
    private SideScrollerScreen gameScreen;
    private GameLevel gameLevel;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SideScrollerScreen sideScrollerScreen;

    public TileMapHelper(SideScrollerScreen sideScrollerScreen) {
       // this.gameScreen = gameScreen;
        this.sideScrollerScreen = sideScrollerScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load(sideScrollerScreen.getTilemapFileName());

        System.out.println("TileMapHelper: setupMap method called: tilemapFileName is: " + sideScrollerScreen.getTilemapFileName());

    /*    parseMapObjects(tiledMap.getLayers().get("Foreground").getObjects());
        parseMapObjects(tiledMap.getLayers().get("Midground").getObjects());
        parseMapObjects(tiledMap.getLayers().get("Background").getObjects());*/
        //parseMapObjects(tiledMap.getLayers().get("Tile Layer 1").getObjects());

        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        return mapRenderer;
    }

    private void parseMapObjects(MapObjects mapObjects) {
        System.out.println("TileMapHelper parseMapObjects method called");
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        System.out.println("TileMapHelper createStaticBody method called");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
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

    public void renderMap(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}