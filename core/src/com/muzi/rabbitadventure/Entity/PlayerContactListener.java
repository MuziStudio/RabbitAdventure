package com.muzi.rabbitadventure.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.muzi.rabbitadventure.AssetsManager;
import com.muzi.rabbitadventure.Entity.Bullet.PlayerStone;
import com.muzi.rabbitadventure.Entity.Enemy.FlyEnemy;
import com.muzi.rabbitadventure.Entity.Player.Player1;

public class PlayerContactListener implements ContactListener
{
    Player1 player1;
    public PlayerContactListener(Player1 player)
    {
        this.player1 = player;
    }
    @Override
    public void beginContact(Contact contact)
    {
        playerAndWall(contact);
        bulletAndEnemy(contact);
        bulletAndWall(contact);
        playerWithEnemy(contact);
        playerAndStone(contact);
    }

    public void playerAndStone(Contact contact)
    {
        EntityData data1 = (EntityData) contact.getFixtureA().getBody().getUserData();
        EntityData data2 = (EntityData) contact.getFixtureB().getBody().getUserData();
        if(data1.name.equals("player")&&data2.name.equals("playerstone"))
        {
            player1.stoneNum++;
            PlayerStone stone = (PlayerStone) data2.entity;
            //player1.stones.removeValue(stone,true);
            AssetsManager.getstone.play();
            stone.clear = true;
        }
        else if((data1.name.equals("playerstone")&&data2.name.equals("player")))
        {
            player1.stoneNum++;
            PlayerStone stone = (PlayerStone) data1.entity;
            //player1.stones.removeValue(stone,true);
            AssetsManager.getstone.play();
            stone.clear = true;
        }
    }
    public void playerWithEnemy(Contact contact)
    {
        EntityData data1 = (EntityData) contact.getFixtureA().getBody().getUserData();
        EntityData data2 = (EntityData) contact.getFixtureB().getBody().getUserData();
        if(data1.name.equals("player")&&data2.name.equals("enemy"))
        {
            if(data1.entity.status != Status.DEAD)
            {
                data1.entity.status = Status.DEAD;
                AssetsManager.error.play();
                Player1 pl = (Player1) data1.entity;
                pl.deadTime = 0;
            }
        }
        else if((data1.name.equals("enemy")&&data2.name.equals("player")))
        {
            if(data2.entity.status != Status.DEAD)
            {
                data2.entity.status = Status.DEAD;
                AssetsManager.error.play();
                Player1 pl = (Player1) data2.entity;
                pl.deadTime = 0;
            }
        }
    }
    public void bulletAndWall(Contact contact)
    {
        EntityData data1 = (EntityData) contact.getFixtureA().getBody().getUserData();
        EntityData data2 = (EntityData) contact.getFixtureB().getBody().getUserData();
        if(data1.name.equals("playerstone")&&data2.name.equals("wall"))
        {
            if(data1.entity.status != Status.DEAD)
            {
                data1.entity.status = Status.DEAD;
            }
        }
        else if((data1.name.equals("wall")&&data2.name.equals("playerstone")))
        {
            if(data2.entity.status != Status.DEAD)
            {
                data2.entity.status = Status.DEAD;
            }
        }
    }
    public void bulletAndEnemy(Contact contact)
    {
        EntityData data1 = (EntityData) contact.getFixtureA().getBody().getUserData();
        EntityData data2 = (EntityData) contact.getFixtureB().getBody().getUserData();
        if(data1.name.equals("playerstone")&&data2.name.equals("enemy"))
        {
            if(data1.entity.status != Status.DEAD && data2.entity.status != Status.DEAD)
            {
                data1.entity.status = Status.DEAD;
                AssetsManager.hit.play();
                data2.entity.blood -= 5;
            }
        }
        else if((data1.name.equals("enemy")&&data2.name.equals("playerstone")))
        {
            if(data1.entity.status != Status.DEAD && data2.entity.status != Status.DEAD)
            {
                data2.entity.status = Status.DEAD;
                AssetsManager.hit.play();
                data1.entity.blood -= 5;
            }
        }
    }
    public void playerAndWall(Contact contact)
    {
        if(player1.status != Status.DEAD)
        {
            EntityData data1 = (EntityData) contact.getFixtureA().getBody().getUserData();
            EntityData data2 = (EntityData) contact.getFixtureB().getBody().getUserData();
            if((data1.name.equals("player")&&data2.equals("wall")))
            {
                if(contact.getFixtureB().testPoint(contact.getFixtureA().getBody().getPosition().add(-1.2f, -1.1f))||
                        contact.getFixtureB().testPoint(contact.getFixtureA().getBody().getPosition().add(1.2f, -1.1f)))
                {
                    player1.status = Status.WAIT;
                    player1.jumpNum = 1;
                }
            }
            else if((data1.name.equals("wall")&&data2.name.equals("player")))
            {
                if(contact.getFixtureA().testPoint(contact.getFixtureB().getBody().getPosition().add(-1.2f, -1.1f))||
                        contact.getFixtureA().testPoint(contact.getFixtureB().getBody().getPosition().add(1.2f, -1.1f)))
                {
                    player1.status = Status.WAIT;
                    player1.jumpNum = 1;
                }
            }
        }
    }
    @Override
    public void endContact(Contact contact)
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
