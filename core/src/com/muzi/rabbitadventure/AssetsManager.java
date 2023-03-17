package com.muzi.rabbitadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsManager
{
    public static TextureAtlas playerAtlas;
    public static TextureAtlas uiAtlas;
    public static TextureAtlas screenUI;
    public static TextureAtlas particle;
    public static TextureAtlas enemy;
    public static TextureRegion backgroundForest;
    public static Sound uiSound;
    public static Sound error;
    public static Sound getstone;
    public static Sound hit;
    public static Sound jump;
    public static Sound win;
    public static void load()
    {
        playerAtlas = new TextureAtlas(Gdx.files.internal("texture/player/player.atlas"));
        uiAtlas = new TextureAtlas(Gdx.files.internal("ui/ui.atlas"));
        backgroundForest = new TextureRegion(new Texture("texture/background/backgroundForest.png"));
        uiSound = Gdx.audio.newSound(Gdx.files.internal("sound/switch2.ogg"));
        screenUI = new TextureAtlas(Gdx.files.internal("ui/ScreenUI.atlas"));
        particle = new TextureAtlas(Gdx.files.internal("texture/particle/particle.atlas"));
        enemy = new TextureAtlas(Gdx.files.internal("texture/enemy/enemy.atlas"));
        error = Gdx.audio.newSound(Gdx.files.internal("sound/error.ogg"));
        getstone = Gdx.audio.newSound(Gdx.files.internal("sound/getstone.ogg"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sound/hit.ogg"));
        jump = Gdx.audio.newSound(Gdx.files.internal("sound/jump.ogg"));
        win = Gdx.audio.newSound(Gdx.files.internal("sound/win.ogg"));
    }
}
