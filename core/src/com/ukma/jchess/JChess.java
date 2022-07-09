package com.ukma.jchess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.ukma.jchess.graphics.screens.GameScreen;

public class JChess extends Game {
  private Screen _gameScreen;

  @Override
  public void create() {
    _gameScreen = new GameScreen();

    setScreen(_gameScreen);
  }
}
