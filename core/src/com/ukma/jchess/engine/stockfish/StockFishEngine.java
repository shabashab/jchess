package com.ukma.jchess.engine.stockfish;

import com.ukma.jchess.engine.ChessBoard;
import com.ukma.jchess.engine.ChessEngine;
import com.ukma.jchess.engine.Move;

import java.util.ArrayList;
import java.util.List;

public class StockFishEngine implements ChessEngine {
  Stockfish stockfish = new Stockfish();

  @Override
  public ChessBoard createBoard() {
    return new StockfishBoard();
  }

  @Override
  public List<Move> getAvailableMoves(ChessBoard board) {
    String input = stockfish.getLegalMoves(board.getFEN());

    String[] rows = input.split("\n");

    List<Move> outputList = new ArrayList<>();

    for (String fullRow : rows) {
      String row = fullRow.split(": ")[0];

      if (row.length() != 4) {
        continue;
      }

      String from = row.substring(0, 2);
      String to = row.substring(2, 4);
      outputList.add(new Move(from, to));
    }

    return outputList;
  }

  @Override
  public Move getBestMove(ChessBoard board) {
    String fen = board.getFEN();
    int waitTime = 2000;
    String stringMove = stockfish.getBestMove(fen, waitTime);
    while (true){
      if (stringMove != null && stringMove.length() == 4) {
        String from = stringMove.substring(0, 2);
        String to = stringMove.substring(2, 4);

        return new Move(from, to);
      } else {
        waitTime = waitTime * 2;
        stringMove = stockfish.getBestMove(fen, waitTime);
      }
    }
  }

  @Override
  public void setSkillLevel(int level) {
    stockfish.sendCommand("setoption name Skill Level value " + level);
  }
}
