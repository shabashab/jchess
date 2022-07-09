package com.ukma.jchess.graphics.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ukma.jchess.graphics.sprites.ChessBoardPlane;

public class ChessBoardActor extends Actor {
  private final Sprite _chessBoardPlane;

  public ChessBoardActor() {
    _chessBoardPlane = new ChessBoardPlane();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    _chessBoardPlane.draw(batch, parentAlpha);
  }
}
