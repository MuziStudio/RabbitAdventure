package com.muzi.rabbitadventure.Entity.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.muzi.rabbitadventure.AssetsManager;
import com.muzi.rabbitadventure.Entity.BaseEntity;
import com.muzi.rabbitadventure.Entity.Status;
import com.muzi.rabbitadventure.GameScreen.LevelTool;

public class FlyEnemy extends BaseEntity
{
    private Animation<TextureRegion> fly;
    private TextureRegion[] animationTex;
    private TextureAtlas playerAtlas;
    private TextureRegion currectRegion;
    LevelTool tool;
    float PPX = 74f;
    public FlyEnemy(Body body)
    {
        playerAtlas = AssetsManager.enemy;
        animationTex = new TextureRegion[]{playerAtlas.findRegion("wingMan1"),playerAtlas.findRegion("wingMan2")
                ,playerAtlas.findRegion("wingMan3"),playerAtlas.findRegion("wingMan4"),
                playerAtlas.findRegion("wingMan5")};
        tool = new LevelTool();
        this.body = body;
        status = Status.RUN;
        blood = 10;
        fly = new Animation<>(0.2f, animationTex);
        fly.setPlayMode(Animation.PlayMode.LOOP);
    }
    @Override
    public void update()
    {
        time += Gdx.graphics.getDeltaTime();
        if(blood <= 0)
        {
            status = Status.DEAD;
        }
    }

    @Override
    public void render(SpriteBatch batch)
    {
        drawFitRegion(batch, fly);
    }


    public void drawFitRegion(SpriteBatch batch,Animation<TextureRegion> animation)
    {
        currectRegion = animation.getKeyFrame(time);
        batch.draw(currectRegion,body.getPosition().x-currectRegion.getRegionWidth()/PPX/2, body.getPosition().y-currectRegion.getRegionHeight()/PPX/2.5f,
                currectRegion.getRegionWidth()/PPX,currectRegion.getRegionHeight()/PPX);
    }
}
