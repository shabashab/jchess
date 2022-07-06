package com.ukma.jchess.engine;

public class Move {
  private final String _from;
  private final String _to;

  public String getFrom() {
    return _from;
  }

  public String getTo() {
    return _to;
  }

  public Move(String from, String to) {
    _from = from;
    _to = to;
  }
}
