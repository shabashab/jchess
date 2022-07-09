package com.ukma.jchess.engine;

import java.util.List;

public interface ChessEngine {
  ChessBoard createBoard();

  List<Move> getAvailableMoves(ChessBoard board);
  Move getBestMove(ChessBoard board);

  void setSkillLevel(int level);
}
