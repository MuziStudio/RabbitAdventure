package com.muzi.rabbitadventure.Entity.Bullet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.muzi.rabbitadventure.AssetsManager;
import com.muzi.rabbitadventure.Entity.BaseEntity;
import com.muzi.rabbitadventure.Entity.EntityData;
import com.muzi.rabbitadventure.Entity.Status;

public class PlayerStone extends BaseEntity
{
    Sprite sprite;
    float PPX;
    public boolean clear = false;
    public PlayerStone(Body body,float PPX)
    {
        this.body = body;
        this.PPX = PPX;
        sprite = new Sprite(AssetsManager.particle.findRegion("particle_darkBrown"));
        sprite.setSize(1, 1);
    }
    @Override
    public void update()
    {
        sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, body.getPosition().y-sprite.getHeight()/2);
        sprite.setOriginCenter();
        sprite.setRotation((float) (body.getAngle()/Math.PI*180));
    }

    @Override
    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

}
