package com.muzi.rabbitadventure.Entity.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.muzi.rabbitadventure.AssetsManager;
import com.muzi.rabbitadventure.Entity.BaseEntity;
import com.muzi.rabbitadventure.Entity.Bullet.PlayerStone;
import com.muzi.rabbitadventure.Entity.EntityData;
import com.muzi.rabbitadventure.Entity.Status;
import com.muzi.rabbitadventure.GameScreen.LevelTool;

public class Player1 extends BaseEntity
{
    public Animation<TextureRegion> wait,run_right,run_left,jump,dead;
    private TextureRegion[] frames;
    private TextureAtlas playerAtlas;
    TextureRegion currectRegion;
    public float maxSpeed = 8f;
    public float shootTime = 0f,deadTime = 0f;
    public Array<PlayerStone> stones;
    public World world;
    public int jumpNum = 1;
    public int stoneNum = 10;
    public int lastSpeed = 1;
    LevelTool tool;
    private float PPX;
    public Player1(World world,float PPX)
    {
        playerAtlas = AssetsManager.playerAtlas;
        frames = new TextureRegion[]{playerAtlas.findRegion("bunny1_stand"),playerAtlas.findRegion("bunny1_ready")
                ,playerAtlas.findRegion("bunny1_walk1"),playerAtlas.findRegion("bunny1_walk2"),
                playerAtlas.findRegion("bunny1_jump"),playerAtlas.findRegion("bunny1_hurt")};
        this.world = world;
        tool = new LevelTool();
        stones = new Array<>();
        this.body = tool.createBoxBody(6,5,0.9f,1,0,world,
                BodyDef.BodyType.DynamicBody,0f,0f,true,new EntityData("player", this));
        this.body.setGravityScale(2f);
        status = Status.WAIT;
        this.PPX = PPX;
        wait = new Animation<>(0.2f,frames[0],frames[1]);
        wait.setPlayMode(Animation.PlayMode.LOOP);
        TextureAtlas right_atlas = new TextureAtlas(Gdx.files.internal("texture/player/player.atlas"));
        TextureRegion right_walk1 = right_atlas.findRegion("bunny1_walk1");
        TextureRegion right_walk2 = right_atlas.findRegion("bunny1_walk2");
        run_right = new Animation<>(0.2f,right_walk1,right_walk2);
        run_right.setPlayMode(Animation.PlayMode.LOOP);
        run_left = new Animation<>(0.2f,frames[2],frames[3]);
        run_left.setPlayMode(Animation.PlayMode.LOOP);
        jump = new Animation<>(0.2f,frames[1],frames[4]);
        jump.setPlayMode(Animation.PlayMode.NORMAL);
        dead = new Animation<>(0.2f,frames[0],frames[5]);
        dead.setPlayMode(Animation.PlayMode.NORMAL);
    }
    @Override
    public void update()
    {
        time += Gdx.graphics.getDeltaTime();
        shootTime += Gdx.graphics.getDeltaTime();
        deadTime += Gdx.graphics.getDeltaTime();
        for (PlayerStone stone:stones)
        {
            stone.update();
            if(stone.clear)
            {
                world.destroyBody(stone.body);
                stones.removeValue(stone, true);
            }
        }
        if(body.getPosition().y<0)
        {
            birth();
        }
        if(Math.abs(body.getLinearVelocity().x) > 5)
        {
            status = Status.RUN;
        }
    }

    public void birth()
    {
        if(deadTime > 1)
        {
            world.destroyBody(body);
            this.body = tool.createBoxBody(12.6f,10,0.9f,1,0,world,
                    BodyDef.BodyType.DynamicBody,0f,0f,true,new EntityData("player",this));
            body.setGravityScale(2f);
            status = Status.WAIT;
        }
    }
    public void run(String direction)
    {
        if(status != Status.DEAD)
        {
            status = Status.RUN;
            if(direction.equals("left"))
            {
                body.setLinearVelocity(-maxSpeed, body.getLinearVelocity().y);
            }
            else if(direction.equals("right"))
            {
                body.setLinearVelocity(maxSpeed, body.getLinearVelocity().y);
            }
        }
    }
    public void stop()
    {
        if(status != Status.DEAD)
        {
            status = Status.WAIT;
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        }
    }
    public void jump()
    {
        if(status != Status.DEAD && jumpNum > 0)
        {
            status = Status.JUMPING;
            AssetsManager.jump.play();
            body.setLinearVelocity(body.getLinearVelocity().x,21);
            jumpNum -= 1;
        }
    }
    public void shoot()
    {
        if(stoneNum>0 && shootTime > 0.5f && status != Status.DEAD)
        {
            Body stoneBody = tool.createBoxBody(body.getPosition().x+lastSpeed*2,body.getPosition().y+1,0.4f,0.4f,0,world, BodyDef.BodyType.DynamicBody
                    , 0.2f, 0.6f, false, null);
            stoneBody.setLinearVelocity(lastSpeed*20, 0);
            stoneBody.applyAngularImpulse(-2,true);
            PlayerStone stone = new PlayerStone(stoneBody,PPX);
            stoneBody.setUserData(new EntityData("playerstone", stone));
            stones.add(stone);
            stoneNum -= 1;
            shootTime = 0;
        }
    }
    @Override
    public void render(SpriteBatch batch)
    {
        for (PlayerStone stone:stones)
        {
            stone.render(batch);
        }
        switch (status) {
            case RUN:
                if (body.getLinearVelocity().x < 0) {
                    drawFitRegion(batch, run_left);
                } else {
                    drawFitRegion(batch, run_right);
                }
                break;
            case DEAD:
                drawFitRegion(batch, dead);
                break;
            case WAIT:
                drawFitRegion(batch, wait);
                break;
            case JUMPING:
                drawFitRegion(batch, jump);
                break;
        }
    }

    public void drawFitRegion(SpriteBatch batch,Animation<TextureRegion> animation)
    {
        currectRegion = animation.getKeyFrame(time);
        batch.draw(currectRegion,body.getPosition().x-currectRegion.getRegionWidth()/PPX/2, body.getPosition().y-currectRegion.getRegionHeight()/PPX/2.5f,
                currectRegion.getRegionWidth()/PPX,currectRegion.getRegionHeight()/PPX);
    }
}
