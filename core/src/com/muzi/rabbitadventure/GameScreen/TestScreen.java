package com.muzi.rabbitadventure.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class TestScreen implements Screen, InputProcessor
{
    World world;
    Box2DDebugRenderer box2DDebugRenderer;
    OrthographicCamera camera;
    LevelTool tool;
    float PPX = 32f;
    @Override
    public void show()
    {
        world = new World(new Vector2(0,-20f), false);
        camera = new OrthographicCamera();
        tool = new LevelTool();
        camera.setToOrtho(false, 800/PPX, 480/PPX);
        box2DDebugRenderer = new Box2DDebugRenderer();
        tool.createBoxBody(1,1,30,1,0,world, BodyDef.BodyType.StaticBody, 0.5f, 0.5f, false, "ll");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0,0,0,1);
        world.step(1/60f, 6, 4);
        camera.update();
        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height)
    {

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

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 tp = new Vector3(screenX,screenY,0);
        Vector3 jj = camera.unproject(tp);
        tool.createBoxBody(jj.x,jj.y,2,2,0.5f,world, BodyDef.BodyType.DynamicBody,0.5f,0.5f,false, "kk");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
