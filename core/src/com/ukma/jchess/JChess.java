package com.ukma.jchess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ukma.jchess.graphics.screens.GameScreen;
import com.ukma.jchess.graphics.screens.MenuScreen;
import com.ukma.jchess.graphics.utils.MoveController;

import java.awt.*;

public class JChess extends Game {
  private Screen _gameScreen;
  private MenuScreen _menuScreen;

  @Override
  public void create() {
    _menuScreen = new MenuScreen();
    _menuScreen.setStartGameListener(this::startGame);

    setScreen(_menuScreen);
  }

  private void startGame(MoveController moveController) {
    _gameScreen = new GameScreen(moveController);
    setScreen(_gameScreen);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    super.render();
  }
}
