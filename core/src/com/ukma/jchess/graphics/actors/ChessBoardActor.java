package com.ukma.jchess.graphics.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ukma.jchess.engine.ChessPiece;
import com.ukma.jchess.engine.ChessPiecePosition;
import com.ukma.jchess.graphics.sprites.ChessBoardPlane;
import com.ukma.jchess.graphics.utils.ChessPositionVector2Converter;
import com.ukma.jchess.graphics.utils.PieceTextureManager;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardActor extends Actor {
  private final PieceTextureManager _pieceTextureManager;
  private final ChessPositionVector2Converter _positionConverter;

  private final Sprite _chessBoardPlane;

  private List<ChessPiecePosition> _positions;

  public ChessBoardActor() {
    _chessBoardPlane = new ChessBoardPlane();

    _pieceTextureManager = new PieceTextureManager();
    _pieceTextureManager.loadTextures(Gdx.files.internal("assets/ChessPieces.png"));

    _positionConverter = new ChessPositionVector2Converter();

    _positions = new ArrayList<>();
    _positions.add(new ChessPiecePosition("c2", ChessPiece.KING_WHITE));
    _positions.add(new ChessPiecePosition("a2", ChessPiece.PAWN_WHITE));
    _positions.add(new ChessPiecePosition("b2", ChessPiece.PAWN_WHITE));
    _positions.add(new ChessPiecePosition("h8", ChessPiece.KING_BLACK));
  }

  private void drawPieces(Batch batch, float parentAlpha) {
    for (ChessPiecePosition position : _positions) {
      Vector2 positionCoordinates = _positionConverter.convertToVector2(position.getPosition());
      batch.draw(
        _pieceTextureManager.getTextureRegionByPiece(position.getPiece()),
        positionCoordinates.x,
        positionCoordinates.y,
        1f, 1f);
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    _chessBoardPlane.draw(batch, parentAlpha);
    drawPieces(batch, parentAlpha);
    batch.flush();
  }
}
