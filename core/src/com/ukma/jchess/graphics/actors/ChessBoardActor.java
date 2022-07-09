package com.ukma.jchess.graphics.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.ukma.jchess.engine.ChessPiece;
import com.ukma.jchess.engine.ChessPiecePosition;
import com.ukma.jchess.engine.Move;
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
  private List<Move> _availableMoves;

  private final ChessPositionVector2Converter _converter;

  public ChessBoardActor() {
    _converter = new ChessPositionVector2Converter();

    _chessBoardPlane = new ChessBoardPlane();
    _chessBoardPieces = new ChessBoardPieces(_converter);

    _positions = new ArrayList<>();
    _positions.add(new ChessPiecePosition("a2", ChessPiece.PAWN_WHITE));
    _positions.add(new ChessPiecePosition("b2", ChessPiece.PAWN_WHITE));
    _chessBoardPieces.update(_positions);

    _drawAvailableMoves = false;

    _availableMoves = new ArrayList<>();
    _availableMoves.add(new Move("a2", "a3"));
    _availableMoves.add(new Move("b2", "b3"));

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

  public void onChessBoardClick(float x, float y) {
    Vector2 position = new Vector2(x, y);
    String chessPosition = _converter.convertToChessPosition(position);

    if(_availableMovesTarget != null && _availableMovesTarget.equals(chessPosition) && _drawAvailableMoves) {
      _drawAvailableMoves = false;
      _availableMovesTarget = null;
      return;
    }

    if(_positions.stream().noneMatch(chessPiecePosition -> chessPiecePosition.getPosition().equals(chessPosition))) {
      _drawAvailableMoves = false;
      _availableMovesTarget = null;
      return;
    }

    _availableMovesSprite.update(_availableMoves.stream().filter(move -> move.getFrom().equals(chessPosition)).collect(Collectors.toList()));
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
