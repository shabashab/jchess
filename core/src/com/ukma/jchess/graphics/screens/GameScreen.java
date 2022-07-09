package com.ukma.jchess.graphics.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ukma.jchess.engine.ChessBoard;
import com.ukma.jchess.engine.stockfish.StockFishEngine;
import com.ukma.jchess.graphics.actors.ChessBoardActor;

public class GameScreen implements Screen {
  private final Viewport _viewport;
  private final Camera _camera;
  private final Stage _stage;

  public GameScreen() {
    _camera = new OrthographicCamera(8, 8);
    _viewport = new FitViewport(8, 8, _camera);
    _stage = new Stage(_viewport);

    StockFishEngine engine = new StockFishEngine();
    ChessBoard board = engine.createBoard();

    Actor chessBoardActor = new ChessBoardActor(board, engine);
    chessBoardActor.setBounds(0, 0, 8f, 8f);
    _stage.addActor(chessBoardActor);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(_stage);
  }

  @Override
  public void render(float delta) {
    _stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    _viewport.update(width, height);
    _camera.update();
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
