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
    private SideScrollerScreen gameScreen;
    private GameLevel gameLevel;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private final SideScrollerScreen sideScrollerScreen;

    public TileMapHelper(SideScrollerScreen sideScrollerScreen) {
        //this.gameScreen = gameScreen;
        this.sideScrollerScreen = sideScrollerScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load(sideScrollerScreen.getTilemapFileName());

        System.out.println("TileMapHelper: setupMap method called: tilemapFileName is: " + sideScrollerScreen.getTilemapFileName());

       // parseMapObjects(tiledMap.getLayers().get("Object Layer").getObjects());
      //  parseMapObjects(tiledMap.getLayers().get("Collision").getObjects());
       // parseMapObjects(tiledMap.getLayers().get("Background").getObjects());
        //parseMapObjects(tiledMap.getLayers().get("Tile Layer 1").getObjects());
     //   parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
    //    return new OrthogonalTiledMapRenderer(tiledMap);


        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        return mapRenderer;
    }

/*    private void parseMapObjects(MapObjects mapObjects) {
        System.out.println("TileMapHelper parseMapObjects method called");
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
       // System.out.println("TileMapHelper createStaticBody method called");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = sideScrollerScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }*/

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
      //  System.out.println("TileMapHelper createPolygonShape method called");
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
        Rectangle zeppelinBounds = new Rectangle(zeppelin.getX(), zeppelin.getY(), zeppelin.getWidth(), zeppelin.getHeight());
        boolean overlaps = polygonBounds.overlaps(zeppelinBounds);
        polygonShape.dispose();
        return overlaps;
    }

    public void renderMap(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public PolygonMapObject[] getStaticBody() {
      //  System.out.println("TileMapHelper getStaticBody method called");
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
}