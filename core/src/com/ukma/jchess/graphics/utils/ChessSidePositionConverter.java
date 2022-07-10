package com.ukma.jchess.graphics.utils;

import com.badlogic.gdx.math.Vector2;
import com.ukma.jchess.engine.ChessSide;

public class ChessSidePositionConverter {
  public static Vector2 chessToScreenCoordinates(Vector2 position, ChessSide side) {
    float x = side == ChessSide.WHITE ? position.x : Math.abs(position.x - 7);
    float y = side == ChessSide.WHITE ? position.y : Math.abs(position.y - 7);

    return new Vector2(x, y);
  }

  public static Vector2 screenCoordinatesToChess(Vector2 chessCoordinates, ChessSide side) {
    return chessToScreenCoordinates(new Vector2((int)chessCoordinates.x, (int)chessCoordinates.y), side);
  }
}
