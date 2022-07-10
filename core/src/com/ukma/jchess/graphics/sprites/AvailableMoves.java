package com.ukma.jchess.graphics.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.ukma.jchess.engine.Move;
import com.ukma.jchess.graphics.utils.ChessPositionVector2Converter;
import com.ukma.jchess.graphics.utils.ChessSidePositionConverter;
import com.ukma.jchess.graphics.utils.MoveController;

import java.util.List;

public class AvailableMoves extends Sprite {
  private List<Move> _availableMovesTo;
  private final ShapeRenderer _shapeRenderer;

  private final MoveController _controller;

  public AvailableMoves(MoveController controller) {
    _controller = controller;
    _shapeRenderer = new ShapeRenderer();
  }

  public void update(List<Move> availableMoves) {
    _availableMovesTo = availableMoves;
  }

  @Override
  public void draw(Batch batch, float alphaModulation) {
    if(_availableMovesTo == null)
      return;

    batch.end();

    _shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    _shapeRenderer.setColor(new Color(0f, 0.3f, 0f, 0.1f));
    for(Move move: _availableMovesTo) {
      Vector2 pos = ChessSidePositionConverter.chessToScreenCoordinates(ChessPositionVector2Converter.convertToVector2(move.getTo()), _controller.getRenderSide());
      _shapeRenderer.circle(pos.x + 0.5f, pos.y + 0.5f, 0.2f, 50);
    }

    _shapeRenderer.end();

    batch.begin();
  }
}
