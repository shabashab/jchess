package com.ukma.jchess.graphics.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class ChessPositionVector2Converter {
  public Vector2 convertToVector2(String chessPosition) {
    if(chessPosition.length() != 2)
      throw new IllegalArgumentException("Chess position must be length of 2");

    char[] charArray = chessPosition.toCharArray();

    int x = charArray[0] - 97;
    int y = charArray[1] - 49;

    return new Vector2(x, y);
  }

  public String convertToChessPosition(Vector2 position) {
    char[] result = new char[2];

    result[0] = (char)(((int)position.x) + 97);
    result[1] = (char)(((int)position.y) + 49);

    return Arrays.toString(result);
  }
}
