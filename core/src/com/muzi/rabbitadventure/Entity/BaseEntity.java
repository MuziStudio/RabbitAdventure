package com.muzi.rabbitadventure.Entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BaseEntity
{
    public Body body;
    public float time = 0;
    public int blood;
    public Status status;
    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
