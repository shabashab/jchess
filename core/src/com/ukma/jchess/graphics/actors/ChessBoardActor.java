package com.ukma.jchess.graphics.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.ukma.jchess.engine.*;
import com.ukma.jchess.graphics.sprites.AvailableMoves;
import com.ukma.jchess.graphics.sprites.ChessBoardPieces;
import com.ukma.jchess.graphics.sprites.ChessBoardPlane;
import com.ukma.jchess.graphics.utils.ChessPositionVector2Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessBoardActor extends Actor {
  private final Sprite _chessBoardPlane;
  private final ChessBoardPieces _chessBoardPieces;
  private final AvailableMoves _availableMovesSprite;

  private List<ChessPiecePosition> _positions;

  private boolean _drawAvailableMoves;
  private String _availableMovesTarget;
  private List<Move> _availableMovesForTarget;
  private List<Move> _availableMoves;

  private ChessBoard _board;
  private ChessEngine _engine;

  private final ChessPositionVector2Converter _converter;

  public ChessBoardActor(ChessBoard board, ChessEngine engine) {
    _board = board;
    _engine = engine;

    _converter = new ChessPositionVector2Converter();

    _chessBoardPlane = new ChessBoardPlane();
    _chessBoardPieces = new ChessBoardPieces(_converter);

    _positions = board.getPositions();
    _chessBoardPieces.update(_positions);

    _drawAvailableMoves = false;

    _availableMoves = engine.getAvailableMoves(board);

    _availableMovesSprite = new AvailableMoves(_converter);
    _availableMovesSprite.update(_availableMoves);

    this.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        onChessBoardClick(x, y);
        return true;
      }
    });
  }

  private void makeMove(Move move) {
    _board.makeMove(move);
    _drawAvailableMoves = false;
    _positions = _board.getPositions();
    _chessBoardPieces.update(_positions);
    _availableMoves = _engine.getAvailableMoves(_board);
    _availableMovesSprite.update(_availableMoves);
  }

  public void onChessBoardClick(float x, float y) {
    Vector2 position = new Vector2(x, y);
    String chessPosition = _converter.convertToChessPosition(position);

    if(_drawAvailableMoves && _availableMovesForTarget.stream().anyMatch(move -> move.getTo().equals(chessPosition))) {
      this.makeMove(new Move(_availableMovesTarget, chessPosition));
      this.makeMove(_engine.getBestMove(_board));

      return;
    }

    if(_availableMovesTarget != null && _availableMovesTarget.equals(chessPosition) && _drawAvailableMoves) {
      _drawAvailableMoves = false;
      return;
    }

    if(_positions.stream().noneMatch(chessPiecePosition -> chessPiecePosition.getPosition().equals(chessPosition))) {
      _drawAvailableMoves = false;
      return;
    }

    _availableMovesForTarget = _availableMoves.stream().filter(move -> move.getFrom().equals(chessPosition)).collect(Collectors.toList());
    _availableMovesSprite.update(_availableMovesForTarget);
    _drawAvailableMoves = true;
    _availableMovesTarget = chessPosition;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    _chessBoardPlane.draw(batch, parentAlpha);
    _chessBoardPieces.draw(batch, parentAlpha);

    if(_drawAvailableMoves)
      _availableMovesSprite.draw(batch, parentAlpha);
  }
}
