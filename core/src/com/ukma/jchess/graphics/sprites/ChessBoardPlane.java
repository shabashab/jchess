package com.ukma.jchess.graphics.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ChessBoardPlane extends Sprite {
  private final ShapeRenderer _shapeRenderer;

  public ChessBoardPlane() {
    _shapeRenderer = new ShapeRenderer();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    _shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    for(int x = 0; x < 8; x++) {
      for(int y = 0; y < 8; y++) {
        boolean isBlack = ((x + y) % 2) == 1;

        if(isBlack)
          _shapeRenderer.setColor(Color.BLACK);
        else _shapeRenderer.setColor(Color.WHITE);

        _shapeRenderer.rect((float)x, (float)y, 1f, 1f);
      }
    }

    _shapeRenderer.end();
  }
}
