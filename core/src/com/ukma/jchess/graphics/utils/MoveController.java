package com.ukma.jchess.graphics.utils;

import com.ukma.jchess.engine.ChessBoard;
import com.ukma.jchess.engine.ChessEngine;
import com.ukma.jchess.engine.ChessSide;
import com.ukma.jchess.engine.Move;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveController {
  private ChessBoard _board;
  private ChessEngine _engine;

  private GameMode _gameMode;

  private List<Move> _availableMoves;
  private List<Move> _movesHistory;

  private boolean _isBoardLocked = false;
  private ChessSide _playerSide;

  private final List<Runnable> _subscribedToUpdate;

  public MoveController(GameMode gameMode, ChessSide playerSide, ChessEngine engine) {
    _subscribedToUpdate = new ArrayList<>();
    _movesHistory = new LinkedList<>();

    _engine = engine;
    reset(gameMode, playerSide);
  }

  public void reset(GameMode mode, ChessSide playerSide) {
    _gameMode = mode;
    _board = _engine.createBoard();

    _playerSide = playerSide;

    if(mode == GameMode.PLAYER_AI && playerSide == ChessSide.BLACK) {
      makeAIMove();
    } else {
      _availableMoves = _engine.getAvailableMoves(_board);
      invokeChessBoardUpdate();
    }
  }

  public List<Move> getAvailableMovesForPosition(String position) {
    return _availableMoves.stream().filter(move -> move.getFrom().equals(position)).collect(Collectors.toList());
  }

  public void makeMove(Move move) {
    _board.makeMove(move);
    _movesHistory.add(move);

    if(_gameMode == GameMode.PLAYER_AI) {
      makeAIMove();
    } else {
      _availableMoves = _engine.getAvailableMoves(_board);
      invokeChessBoardUpdate();
    }
  }

  private void makeAIMove() {
    _isBoardLocked = true;
    invokeChessBoardUpdate();
    Thread thread = new Thread(() -> {
      Move move = _engine.getBestMove(_board);
      _board.makeMove(move);
      _movesHistory.add(move);
      _availableMoves = _engine.getAvailableMoves(_board);
      _isBoardLocked = false;
      invokeChessBoardUpdate();
    });
    thread.start();
  }

  public ChessBoard getBoard() {
    return _board;
  }

  public ChessSide getRenderSide() {
    if(_gameMode == GameMode.PLAYER_PLAYER)
      return _board.getCurrentSide();
    return _playerSide;
  }

  public boolean getIsBoardLocked() {
    return _isBoardLocked;
  }

  public void subscribeOnChessBoardUpdate(Runnable run) {
    _subscribedToUpdate.add(run);
  }

  public void invokeChessBoardUpdate() {
    for(Runnable runnable: _subscribedToUpdate)
      runnable.run();
  }
}
