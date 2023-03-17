package com.muzi.rabbitadventure.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.muzi.rabbitadventure.AssetsManager;
import com.muzi.rabbitadventure.Entity.Enemy.FlyEnemy;
import com.muzi.rabbitadventure.Entity.EntityData;

public class LevelTool
{
    public Body createBoxBody(float x, float y, float width,float height,float angle,World world, BodyDef.BodyType type,float rest,float friction,boolean fixRotation,Object userdata)
    {
        BodyDef def = new BodyDef();
        def.position.set(x, y);
        def.type = type;
        def.fixedRotation = fixRotation;
        def.angle = angle;
        Body body = world.createBody(def);
        body.setUserData(userdata);
        FixtureDef fix = new FixtureDef();
        fix.restitution = rest;
        fix.density = 2f;
        fix.friction = friction;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        fix.shape = shape;
        body.createFixture(fix);
        return body;
    }
    public void parseMapWall(TiledMap map,World world,float PPX)
    {
        MapObjects wall = map.getLayers().get("wall").getObjects();
        for(MapObject object:wall)
        {
            if(object instanceof RectangleMapObject)
            {
                RectangleMapObject rectangleMapObject = (RectangleMapObject)object;
                Rectangle rect = rectangleMapObject.getRectangle();
                createBoxBody(rect.x/PPX+rect.width/PPX/2, rect.y/PPX+rect.height/PPX/2, rect.width/PPX/2,
                        rect.height/PPX/2,0, world, BodyDef.BodyType.StaticBody, 0, 0.8f,
                        true,new EntityData("wall",null));
            }
        }
    }
    public void parseMapFlyEnemy(TiledMap map, World world, Array<FlyEnemy> enemies,float PPX)
    {
        MapObjects flyEnemy = map.getLayers().get("flyenemy").getObjects();
        for (MapObject object:flyEnemy)
        {
            if(object instanceof RectangleMapObject)
            {
                RectangleMapObject rectangleMapObject = (RectangleMapObject)object;
                Rectangle rect = rectangleMapObject.getRectangle();
                FlyEnemy flyEnemy1 = new FlyEnemy(createBoxBody(rect.x/PPX+rect.width/PPX/2, rect.y/PPX+rect.height/PPX/2, 0.8f,
                        0.8f,0, world, BodyDef.BodyType.StaticBody, 0, 0.9f,
                        true,null));
                flyEnemy1.body.setGravityScale(0);
                flyEnemy1.body.setUserData(new EntityData("enemy",flyEnemy1));
                enemies.add(flyEnemy1);
            }
        }
    }
    public Button createButton(String up,String down,int x,int y,int width,int height)
    {
        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(AssetsManager.screenUI.findRegion(up)));
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(AssetsManager.screenUI.findRegion(down)));
        Button button = new Button(upDrawable,downDrawable);
        button.setPosition(x, y);
        button.setSize(width,height);
        return button;
    }
}
