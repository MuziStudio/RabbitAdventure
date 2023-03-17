package com.muzi.rabbitadventure.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.muzi.rabbitadventure.AssetsManager;
import com.muzi.rabbitadventure.Entity.Enemy.FlyEnemy;
import com.muzi.rabbitadventure.Entity.Player.Player1;
import com.muzi.rabbitadventure.Entity.PlayerContactListener;
import com.muzi.rabbitadventure.Entity.Status;
import com.muzi.rabbitadventure.MainGame;
import com.muzi.rabbitadventure.MenuScreen;

public class GameScreen2 implements Screen
{
    World world;
    OrthographicCamera camera;
    FitViewport fitViewport;
    SpriteBatch batch,backgroundBatch;
    Box2DDebugRenderer debugRenderer;
    Stage uiStage;
    Button leftButton,rightButton,jumpButton,shootButton;
    TiledMap map;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    public LevelTool tool;
    Player1 player;
    PlayerContactListener contactListener;
    Array<FlyEnemy> flyEnemies;
    BitmapFont deadText;
    TextureRegion stone;
    boolean iswin = false;
    float PPX = 32f;
    public MainGame game;
    float wintime;
    public GameScreen2(MainGame game)
    {
        this.game = game;
    }
    @Override
    public void show()
    {
        world = new World(new Vector2(0,-20f), true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800/PPX, 480/PPX);
        fitViewport = new FitViewport(800, 480);
        uiStage = new Stage(fitViewport);
        batch = new SpriteBatch();
        backgroundBatch = new SpriteBatch();
        backgroundBatch.setProjectionMatrix(camera.combined);
        map = new TmxMapLoader().load("map/map2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1/64f);
        tiledMapRenderer.setView(camera);
        flyEnemies = new Array<>();
        tool = new LevelTool();
        stone = AssetsManager.particle.findRegion("particle_darkBrown");
        debugRenderer = new Box2DDebugRenderer();
        tool.parseMapWall(map, world,64f);
        tool.parseMapFlyEnemy(map, world, flyEnemies, 64f);
        player = new Player1(world,74f);
        contactListener = new PlayerContactListener(player);
        world.setContactListener(contactListener);
        leftButton = tool.createButton("flatDark23", "flatLight22",20,20,100,100);
        rightButton = tool.createButton("flatDark24", "flatLight23",140,20,100,100);
        shootButton = tool.createButton("flatDark48", "flatLight47",680,20,100,100);
        jumpButton = tool.createButton("flatDark44", "flatLight43",560,20,100,100);
        uiStage.addActor(leftButton);
        uiStage.addActor(rightButton);
        uiStage.addActor(shootButton);
        uiStage.addActor(jumpButton);
        buttonListener();
        deadText = new BitmapFont(Gdx.files.internal("fnt/uifont.fnt"));
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0,0,0,1);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        backgroundBatch.begin();
        backgroundBatch.draw(AssetsManager.backgroundForest, 0, 0, 800/PPX, 480/PPX);
        backgroundBatch.end();
        world.step(1/60f, 6, 4);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        player.update();
        batch.begin();
        enemyLogic();
        player.render(batch);
        batch.end();
        //debugRenderer.render(world, camera.combined);
        backgroundBatch.begin();
        if(player.status == Status.DEAD)
        {
            deadText.setColor(Color.RED);
            deadText.getData().setScale(0.02f);
            deadText.draw(backgroundBatch, "Dead",8.3f,9);
        }
        else if(player.body.getPosition().x >= 142)
        {
            if(!iswin)
            {
                wintime = player.time;
                AssetsManager.win.play();
            }
            iswin = true;
            deadText.setColor(Color.RED);
            deadText.getData().setScale(0.02f);
            deadText.draw(backgroundBatch, "Win",9f,9);
            player.stop();
            player.jump();
        }
        if(iswin && player.time-wintime>3)
        {
            game.setScreen(game.menu);
        }
        deadText.setColor(Color.BLACK);
        deadText.getData().setScale(0.01f);
        deadText.draw(backgroundBatch, player.stoneNum+"", 2.5f, 15.2f);
        backgroundBatch.draw(stone, 0.5f, 13.2f, 1.3f, 1.3f);
        backgroundBatch.end();
        uiStage.act(delta);
        uiStage.draw();
        cameraLogic();
    }

    public void buttonListener()
    {
        leftButton.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.run("left");
                player.lastSpeed = -1;
                if(player.status == Status.DEAD)
                {
                    player.birth();
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.stop();
            }
        });

        rightButton.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.run("right");
                player.lastSpeed = 1;
                if(player.status == Status.DEAD)
                {
                    player.birth();
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.stop();
            }
        });
        jumpButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.jump();
                if(player.status == Status.DEAD)
                {
                    player.birth();
                }
                return true;
            }
        });
        shootButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player.shoot();
                if(player.status == Status.DEAD)
                {
                    player.birth();
                }
                return true;
            }
        });
    }
    public void enemyLogic()
    {
        for(FlyEnemy enemy:flyEnemies)
        {
            if(enemy.status == Status.DEAD)
            {
                world.destroyBody(enemy.body);
                flyEnemies.removeValue(enemy, true);
            }
            else
            {
                enemy.update();
                enemy.render(batch);
            }
        }
    }
    public void cameraLogic()
    {
        if(player.body.getPosition().x >= 800/PPX/2 && player.body.getPosition().x <= 137)
        {
            camera.position.set(player.body.getPosition().x,camera.position.y,0);
        }
        if(player.body.getPosition().y >= 480/PPX/2)
        {
            camera.position.set(camera.position.x,player.body.getPosition().y,0);
        }
    }
    @Override
    public void resize(int width, int height)
    {
        camera.setToOrtho(false, 800/PPX, 480/PPX);
        fitViewport = new FitViewport(800, 480);
        uiStage = new Stage(fitViewport);
        backgroundBatch.setProjectionMatrix(camera.combined);
        map = new TmxMapLoader().load("map/map2.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1/64f);
        tiledMapRenderer.setView(camera);
        uiStage.addActor(leftButton);
        uiStage.addActor(rightButton);
        uiStage.addActor(shootButton);
        uiStage.addActor(jumpButton);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
