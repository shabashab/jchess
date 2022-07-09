package com.ukma.jchess.graphics.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.ukma.jchess.engine.ChessPiecePosition;
import com.ukma.jchess.graphics.utils.ChessPositionVector2Converter;
import com.ukma.jchess.graphics.utils.PieceTextureManager;

import java.util.List;

public class ChessBoardPieces extends Sprite {
  private List<ChessPiecePosition> _positions;
  private final ChessPositionVector2Converter _converter;
  private final PieceTextureManager _pieceTextureManager;

  public ChessBoardPieces(ChessPositionVector2Converter converter) {
    _converter = converter;
    _pieceTextureManager = new PieceTextureManager();
    _pieceTextureManager.loadTextures(Gdx.files.internal("assets/ChessPieces.png"));
  }

  public void update(List<ChessPiecePosition> positions) {
    _positions = positions;
  }

  @Override
  public void draw(Batch batch, float alphaModulation) {
    if(_positions == null)
      return;

    for (ChessPiecePosition position : _positions) {
      Vector2 positionCoordinates = _converter.convertToVector2(position.getPosition());
      batch.draw(
        _pieceTextureManager.getTextureRegionByPiece(position.getPiece()),
        positionCoordinates.x,
        positionCoordinates.y,
        1f, 1f);
    }
  }
}
