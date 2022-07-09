package com.ukma.jchess.engine.stockfish;

import com.ukma.jchess.engine.*;

import java.util.ArrayList;
import java.util.List;

public class StockfishBoard implements ChessBoard {

  private ChessSide chessSideToMove = ChessSide.WHITE;

  boolean OO = true;
  boolean OOO = true;
  boolean oo = true;
  boolean ooo = true;

  String[] verticals = {
    "a", "b", "c", "d", "e", "f", "g", "h"
  };

  String[] horizontals = {
    "1", "2", "3", "4", "5", "6", "7", "8"
  };

  public ChessCell[] chessBoard = initBoard();


  private ChessCell[] initBoard() {
    ChessCell[] chessBoard = new ChessCell[64];
    int numberOfCell = 0;

    for (String h : horizontals) {
      for (String v : verticals) {
        chessBoard[numberOfCell] = new ChessCell(v + h);
        chessBoard[numberOfCell].chessPiece = null;
        numberOfCell++;
      }
    }

    for (int i = 8; i < 16; i++) {
      chessBoard[i].chessPiece = ChessPiece.PAWN_WHITE;
    }

    for (int i = 48; i < 56; i++) {
      chessBoard[i].chessPiece = ChessPiece.PAWN_BLACK;
    }

    chessBoard[0].chessPiece = ChessPiece.ROOK_WHITE;
    chessBoard[1].chessPiece = ChessPiece.KNIGHT_WHITE;
    chessBoard[2].chessPiece = ChessPiece.BISHOP_WHITE;
    chessBoard[3].chessPiece = ChessPiece.QUEEN_WHITE;
    chessBoard[4].chessPiece = ChessPiece.KING_WHITE;
    chessBoard[5].chessPiece = ChessPiece.BISHOP_WHITE;
    chessBoard[6].chessPiece = ChessPiece.KNIGHT_WHITE;
    chessBoard[7].chessPiece = ChessPiece.ROOK_WHITE;

    chessBoard[56].chessPiece = ChessPiece.ROOK_BLACK;
    chessBoard[57].chessPiece = ChessPiece.KNIGHT_BLACK;
    chessBoard[58].chessPiece = ChessPiece.BISHOP_BLACK;
    chessBoard[59].chessPiece = ChessPiece.QUEEN_BLACK;
    chessBoard[60].chessPiece = ChessPiece.KING_BLACK;
    chessBoard[61].chessPiece = ChessPiece.BISHOP_BLACK;
    chessBoard[62].chessPiece = ChessPiece.KNIGHT_BLACK;
    chessBoard[63].chessPiece = ChessPiece.ROOK_BLACK;


    return chessBoard;
  }

  @Override
  public String getFEN() {
    StringBuilder sb = new StringBuilder();
    int numberOfEmptyCells = 0;
    String[] rows = new String[8];

    int rowNumber = 0;

    for (int i = 0; i < 64; i++) {
      if (i % 8 == 0 && i != 0) {
        if (numberOfEmptyCells != 0) {
          sb.append(numberOfEmptyCells);
          numberOfEmptyCells = 0;
        }
        rows[rowNumber] = sb.toString();
        sb = new StringBuilder();
        rowNumber++;
      }
      ChessPiece chessPiece = chessBoard[i].chessPiece;
      if (chessPiece == null) {
        numberOfEmptyCells++;
      } else {
        if (numberOfEmptyCells != 0) {
          sb.append(numberOfEmptyCells);
          numberOfEmptyCells = 0;
        }
        switch (chessPiece) {
          case KING_BLACK:
            sb.append('k');
            break;
          case KING_WHITE:
            sb.append('K');
            break;
          case PAWN_BLACK:
            sb.append('p');
            break;
          case PAWN_WHITE:
            sb.append('P');
            break;
          case ROOK_BLACK:
            sb.append('r');
            break;
          case ROOK_WHITE:
            sb.append('R');
            break;
          case QUEEN_BLACK:
            sb.append('q');
            break;
          case QUEEN_WHITE:
            sb.append('Q');
            break;
          case BISHOP_BLACK:
            sb.append('b');
            break;
          case BISHOP_WHITE:
            sb.append('B');
            break;
          case KNIGHT_BLACK:
            sb.append('n');
            break;
          case KNIGHT_WHITE:
            sb.append('N');
            break;
        }
      }
    }

    rows[7] = sb.toString();

    String outputString;
    StringBuilder outputSB = new StringBuilder();

    for (int i = 7; i >= 0; i--) {
      outputSB.append(rows[i]);
      outputSB.append("/");
    }

    if (chessSideToMove == ChessSide.WHITE) {
      outputSB.append(" w ");
    } else {
      outputSB.append(" b ");
    }

    if (OO) {
      outputSB.append("K");
    }
    if (OOO) {
      outputSB.append("Q");
    }
    if (oo) {
      outputSB.append("k");
    }
    if (ooo) {
      outputSB.append("q");
    }

    outputString = outputSB.toString();

    return outputString;
  }

  @Override
  public void makeMove(Move move) {

    boolean isKing = false;

    ChessCell fromCell = new ChessCell(move.getFrom());
    ChessCell toCell = new ChessCell(move.getTo());

    ChessPiece currentChessPiece = chessBoard[fromCell._numberOfCell].chessPiece;

    if (currentChessPiece == ChessPiece.KING_BLACK) {
      oo = false;
      ooo = false;
      isKing = true;
    }

    if (currentChessPiece == ChessPiece.KING_WHITE) {
      OO = false;
      OOO = false;
      isKing = true;
    }

    if (currentChessPiece == ChessPiece.PAWN_BLACK && new ChessCell(move.getTo())._numberOfCell >= 0 && new ChessCell(move.getTo())._numberOfCell <= 7) {
      chessBoard[fromCell._numberOfCell].chessPiece = null;

      chessBoard[toCell._numberOfCell].chessPiece = ChessPiece.QUEEN_BLACK;
    } else if (currentChessPiece == ChessPiece.PAWN_WHITE && new ChessCell(move.getTo())._numberOfCell >= 56 && new ChessCell(move.getTo())._numberOfCell <= 63) {
      chessBoard[fromCell._numberOfCell].chessPiece = null;

      chessBoard[toCell._numberOfCell].chessPiece = ChessPiece.QUEEN_WHITE;
    } else if (isKing) {
      ChessPiece king;
      ChessPiece rook;

      if (move.getFrom().equals("e1") && move.getTo().equals("g1")) {
        king = chessBoard[new ChessCell("e1")._numberOfCell].chessPiece;
        rook = chessBoard[new ChessCell("h1")._numberOfCell].chessPiece;

        chessBoard[new ChessCell("e1")._numberOfCell].chessPiece = null;
        chessBoard[new ChessCell("h1")._numberOfCell].chessPiece = null;

        chessBoard[new ChessCell("g1")._numberOfCell].chessPiece = king;
        chessBoard[new ChessCell("f1")._numberOfCell].chessPiece = rook;
      } else if (move.getFrom().equals("e1") && move.getTo().equals("c1")) {
        king = chessBoard[new ChessCell("e1")._numberOfCell].chessPiece;
        rook = chessBoard[new ChessCell("a1")._numberOfCell].chessPiece;

        chessBoard[new ChessCell("e1")._numberOfCell].chessPiece = null;
        chessBoard[new ChessCell("a1")._numberOfCell].chessPiece = null;

        chessBoard[new ChessCell("c1")._numberOfCell].chessPiece = king;
        chessBoard[new ChessCell("d1")._numberOfCell].chessPiece = rook;
      } else if (move.getFrom().equals("e8") && move.getTo().equals("g8")) {
        king = chessBoard[new ChessCell("e8")._numberOfCell].chessPiece;
        rook = chessBoard[new ChessCell("h8")._numberOfCell].chessPiece;

        chessBoard[new ChessCell("e8")._numberOfCell].chessPiece = null;
        chessBoard[new ChessCell("h8")._numberOfCell].chessPiece = null;

        chessBoard[new ChessCell("g8")._numberOfCell].chessPiece = king;
        chessBoard[new ChessCell("f8")._numberOfCell].chessPiece = rook;
      } else if (move.getFrom().equals("e8") && move.getTo().equals("c8")) {
        king = chessBoard[new ChessCell("e8")._numberOfCell].chessPiece;
        rook = chessBoard[new ChessCell("a8")._numberOfCell].chessPiece;

        chessBoard[new ChessCell("e8")._numberOfCell].chessPiece = null;
        chessBoard[new ChessCell("a8")._numberOfCell].chessPiece = null;

        chessBoard[new ChessCell("c8")._numberOfCell].chessPiece = king;
        chessBoard[new ChessCell("d8")._numberOfCell].chessPiece = rook;
      } else {
        chessBoard[fromCell._numberOfCell].chessPiece = null;

        chessBoard[toCell._numberOfCell].chessPiece = currentChessPiece;
      }
    } else {
      chessBoard[fromCell._numberOfCell].chessPiece = null;

      chessBoard[toCell._numberOfCell].chessPiece = currentChessPiece;
    }

    if (chessSideToMove == ChessSide.BLACK) {
      chessSideToMove = ChessSide.WHITE;
    } else {
      chessSideToMove = ChessSide.BLACK;
    }
  }

  @Override
  public ChessSide getCurrentSide() {
    return chessSideToMove;
  }

  @Override
  public List<ChessPiecePosition> getPositions() {
    List<ChessPiecePosition> chessPiecePositions = new ArrayList<>();

    for (ChessCell chessCell : chessBoard) {
      if(chessCell.chessPiece == null)
        continue;

      chessPiecePositions.add(new ChessPiecePosition(chessCell.nameOfCell, chessCell.chessPiece));
    }

    return chessPiecePositions;
  }
}
