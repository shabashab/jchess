package com.ukma.jchess.engine;

public class ChessPiecePosition {
  private final String _position;
  private final ChessPiece _piece;

  public String getPosition() {
    return _position;
  }

  public ChessPiece getPiece() {
    return _piece;
  }

  public ChessPiecePosition(String position, ChessPiece piece) {
    _position = position;
    _piece = piece;
  }
}
