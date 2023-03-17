package com.muzi.rabbitadventure;

import com.badlogic.gdx.Game;
import com.muzi.rabbitadventure.GameScreen.TestScreen;

public class MainGame extends Game
{
	public MenuScreen menu;
	TestScreen screen;
	@Override
	public void create ()
	{
		menu = new MenuScreen(this);
		screen = new TestScreen();
		AssetsManager.load();
		AssetsManager.playerAtlas.findRegion("bunny1_walk1").flip(true, false);
		AssetsManager.playerAtlas.findRegion("bunny1_walk2").flip(true, false);
		this.setScreen(menu);
	}
	@Override
	public void dispose ()
	{

	}
}
