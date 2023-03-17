package com.muzi.rabbitadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.muzi.rabbitadventure.GameScreen.GameScreen1;
import com.muzi.rabbitadventure.GameScreen.GameScreen2;


public class MenuScreen implements Screen
{
    MainGame game;
    Stage uiStage;
    SpriteBatch batch;
    FitViewport viewport;
    TextButton startButton,exitButton,level1,level2,backButton;
    BitmapFont title;
    boolean ismoving = false,isbackmoving = false;
    public MenuScreen(MainGame game)
    {
        this.game = game;
    }
    @Override
    public void show()
    {
        batch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        uiStage = new Stage(viewport,batch);
        title = new BitmapFont(Gdx.files.internal("fnt/uifont.fnt"));
        title.setColor(Color.SALMON);
        title.getData().setScale(0.40f);
        startButton = createTextButton("Start", "blue_button02", "blue_button03", 0.3f, 220, 210,350,80);
        exitButton = createTextButton("Exit", "green_button02","green_button03",0.3f, 220, 80, 350, 80);
        level1 = createTextButton("stage1", "blue_button09", "blue_button10", 0.3f, -49*5, 80, 49*5, 45*5);
        level2 = createTextButton("stage2", "green_button09", "green_button10", 0.3f, 800, 80, 49*5, 45*5);
        backButton = createTextButton("B", "blue_button09", "blue_button10", 0.3f, -49*5-90, 400, 49*1.5f, 45*1.5f);
        uiStage.addActor(startButton);
        uiStage.addActor(exitButton);
        uiStage.addActor(level1);
        uiStage.addActor(level2);
        uiStage.addActor(backButton);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta)








    {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();
        batch.draw(AssetsManager.backgroundForest, 0, 0, 800, 480);
        title.draw(batch, "Rabbit Adventure", 90, 380);
        batch.end();
        if(startButton.isPressed())
        {
            if(!ismoving)
                AssetsManager.uiSound.play();
            ismoving = true;
        }
        if(backButton.isPressed())
        {
            if(!isbackmoving)
                AssetsManager.uiSound.play();
            isbackmoving = true;
        }
        if(level1.isPressed())
        {
            game.setScreen(new GameScreen1(game));
            AssetsManager.uiSound.play();
        }
        if(level2.isPressed())
        {
            game.setScreen(new GameScreen2(game));
            AssetsManager.uiSound.play();
        }
        if(exitButton.isPressed())
        {
            AssetsManager.uiSound.play();
            Gdx.app.exit();
        }
        showLevel(800);
        uiStage.act(delta);
        uiStage.draw();
    }

    public void showLevel(int speed)
    {
            if(ismoving) {
                startButton.setTouchable(Touchable.disabled);
                exitButton.setTouchable(Touchable.disabled);
                level1.setTouchable(Touchable.disabled);
                level2.setTouchable(Touchable.disabled);
                backButton.setTouchable(Touchable.disabled);
                startButton.setPosition(startButton.getX(), startButton.getY() + Gdx.graphics.getDeltaTime() * speed);
                exitButton.setPosition(exitButton.getX(), exitButton.getY() - Gdx.graphics.getDeltaTime() * speed);
                level1.setPosition(level1.getX() + Gdx.graphics.getDeltaTime() * speed, level1.getY());
                level2.setPosition(level2.getX() - Gdx.graphics.getDeltaTime() * speed, level2.getY());
                backButton.setPosition(backButton.getX() + Gdx.graphics.getDeltaTime() * speed, backButton.getY());
                if (level1.getX() >= 100) {
                    startButton.setTouchable(Touchable.enabled);
                    exitButton.setTouchable(Touchable.enabled);
                    level1.setTouchable(Touchable.enabled);
                    level2.setTouchable(Touchable.enabled);
                    backButton.setTouchable(Touchable.enabled);
                    ismoving = false;
                }
            }
            if(isbackmoving) {
                startButton.setTouchable(Touchable.disabled);
                exitButton.setTouchable(Touchable.disabled);
                level1.setTouchable(Touchable.disabled);
                level2.setTouchable(Touchable.disabled);
                backButton.setTouchable(Touchable.disabled);
                startButton.setPosition(startButton.getX(), startButton.getY() - Gdx.graphics.getDeltaTime() * speed);
                exitButton.setPosition(exitButton.getX(), exitButton.getY() + Gdx.graphics.getDeltaTime() * speed);
                level1.setPosition(level1.getX() - Gdx.graphics.getDeltaTime() * speed, level1.getY());
                level2.setPosition(level2.getX() + Gdx.graphics.getDeltaTime() * speed, level2.getY());
                backButton.setPosition(backButton.getX() - Gdx.graphics.getDeltaTime() * speed, backButton.getY());
                if (startButton.getY() <= 210) {
                    startButton.setTouchable(Touchable.enabled);
                    exitButton.setTouchable(Touchable.enabled);
                    level1.setTouchable(Touchable.enabled);
                    level2.setTouchable(Touchable.enabled);
                    backButton.setTouchable(Touchable.enabled);
                    isbackmoving = false;
                }
            }
    }
    public TextButton createTextButton(String text,String up,String down,float scale,int x,int y,float width,float height)
    {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(
                new TextureRegionDrawable(AssetsManager.uiAtlas.findRegion(up)),
                new TextureRegionDrawable(AssetsManager.uiAtlas.findRegion(down)),
                new TextureRegionDrawable(AssetsManager.uiAtlas.findRegion(up)),
                new BitmapFont(Gdx.files.internal("fnt/uifont.fnt")));
        TextButton textButton = new TextButton(text, style);
        textButton.getLabel().setFontScale(scale);
        textButton.setSize(width,height);
        textButton.setPosition(x,y);
        return textButton;
    }
    @Override
    public void resize(int width, int height)
    {
        viewport = new FitViewport(800, 480);
        uiStage = new Stage(viewport,batch);
        uiStage.addActor(startButton);
        uiStage.addActor(exitButton);
        uiStage.addActor(level1);
        uiStage.addActor(level2);
        uiStage.addActor(backButton);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        game.dispose();
        uiStage.dispose();
        batch.dispose();
        title.dispose();
    }
}
