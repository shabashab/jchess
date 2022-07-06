package com.ukma.jchess.engine;

import java.util.List;

public interface ChessBoard {
  String getFEN();

  void makeMove(Move move);
  ChessSide getCurrentSide();
  List<ChessPiecePosition> getPositions();
}
