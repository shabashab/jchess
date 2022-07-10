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
import com.ukma.jchess.graphics.utils.ChessSidePositionConverter;
import com.ukma.jchess.graphics.utils.MoveController;

import java.util.List;

public class ChessBoardActor extends Actor {
  private final Sprite _chessBoardPlane;
  private final ChessBoardPieces _chessBoardPieces;
  private final AvailableMoves _availableMovesSprite;

  private boolean _drawAvailableMoves;
  private String _availableMovesTarget;
  private List<Move> _availableMovesForTarget;

  private final MoveController _controller;

  private boolean _canPlayerMakeMove;

  public ChessBoardActor(MoveController controller) {
    _controller = controller;

    _chessBoardPlane = new ChessBoardPlane(_controller);
    _chessBoardPieces = new ChessBoardPieces(_controller);
    _availableMovesSprite = new AvailableMoves(_controller);

    _drawAvailableMoves = false;

    this.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        onChessBoardClick(x, y);
        return true;
      }
    });
  }

  public void onChessBoardClick(float x, float y) {
    if(_controller.getIsBoardLocked())
      return;

    Vector2 position = new Vector2(x, y);
    String chessPosition = ChessPositionVector2Converter.convertToChessPosition(ChessSidePositionConverter.screenCoordinatesToChess(position, _controller.getRenderSide()));

    if(_drawAvailableMoves && _availableMovesForTarget.stream().anyMatch(move -> move.getTo().equals(chessPosition))) {
      _controller.makeMove(new Move(_availableMovesTarget, chessPosition));
      _drawAvailableMoves = false;
      return;
    }

    if(_availableMovesTarget != null && _availableMovesTarget.equals(chessPosition) && _drawAvailableMoves) {
      _drawAvailableMoves = false;
      return;
    }

    _availableMovesForTarget = _controller.getAvailableMovesForPosition(chessPosition);
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
