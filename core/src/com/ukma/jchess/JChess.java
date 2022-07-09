package com.ukma.jchess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.ukma.jchess.graphics.screens.GameScreen;

public class JChess extends Game {
  private Screen _gameScreen;

  @Override
  public void create() {
    _gameScreen = new GameScreen();

    setScreen(_gameScreen);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    super.render();
  }
}
