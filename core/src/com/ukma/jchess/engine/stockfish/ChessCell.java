package com.ukma.jchess.engine.stockfish;

import com.ukma.jchess.engine.ChessPiece;
import com.ukma.jchess.engine.ChessSide;

public class ChessCell {
  private int _x;
  private int _y;
  public int _numberOfCell;
  public String nameOfCell;
  public ChessPiece chessPiece;

  public ChessCell(String inputString) {
    char[] inputCharArray = inputString.toCharArray();

    _x = inputCharArray[0] - 97;
    _y = inputCharArray[1] - 49;

    _numberOfCell = (8 * _y) + _x;
    nameOfCell = inputString;
  }
}
