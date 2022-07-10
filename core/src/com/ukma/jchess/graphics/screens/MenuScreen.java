package com.ukma.jchess.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ukma.jchess.engine.ChessEngine;
import com.ukma.jchess.engine.ChessSide;
import com.ukma.jchess.engine.Move;
import com.ukma.jchess.engine.stockfish.StockFishEngine;
import com.ukma.jchess.graphics.utils.GameMode;
import com.ukma.jchess.graphics.utils.MoveController;
import com.ukma.jchess.graphics.utils.StartGameListener;

public class MenuScreen implements Screen {
  private final Stage _uiStage;
  private final Table _uiTable;
  private final SelectBox<String> _difficultySelectBox;

  private StartGameListener _startGameListener;


  public MenuScreen() {
    _uiStage = new Stage(new ScreenViewport());
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    _uiTable = new Table();
    _uiTable.setSize(_uiStage.getWidth(), _uiStage.getHeight());
    _uiTable.align(Align.center);

    TextButton aiButton = new TextButton("Player vs AI", skin);
    aiButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        ChessEngine engine = new StockFishEngine();
        engine.setSkillLevel(_difficultySelectBox.getSelectedIndex() * 4);
        MoveController controller = new MoveController(GameMode.PLAYER_AI, ChessSide.WHITE, engine);
        if(_startGameListener != null)
          _startGameListener.startGame(controller);
      }
    });
    _uiTable.add(aiButton);

    _uiTable.row().pad(20);


    Label difficultyText = new Label("AI difficulty: ", skin);

    _uiTable.add(difficultyText);
    _uiTable.row();

    _difficultySelectBox = new SelectBox<>(skin);
    _difficultySelectBox.setItems("Super easy", "Easy", "Medium", "Hard", "SUPER EXTREMELY HARD");
    _uiTable.add(_difficultySelectBox);

    _uiTable.row().pad(20);

    TextButton twoPlayersButton = new TextButton("Player vs Player", skin);
    _uiTable.add(twoPlayersButton);

    twoPlayersButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        ChessEngine engine = new StockFishEngine();
        MoveController controller = new MoveController(GameMode.PLAYER_PLAYER, ChessSide.WHITE, engine);
        if(_startGameListener != null)
          _startGameListener.startGame(controller);
      }
    });

    _uiTable.row().pad(20);

    _uiStage.addActor(_uiTable);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(_uiStage);
  }

  public void setStartGameListener(StartGameListener listener) {
    _startGameListener = listener;
  }

  @Override
  public void render(float delta) {
    _uiStage.act(delta);

    _uiStage.draw();
  }

  @Override
  public void resize(int width, int height) {
    _uiTable.setSize(width, height);
    _uiStage.getViewport().update(width, height);
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
