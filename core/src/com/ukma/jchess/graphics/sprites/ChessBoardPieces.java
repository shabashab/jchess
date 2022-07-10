package com.ukma.jchess.graphics.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.ukma.jchess.engine.ChessPiecePosition;
import com.ukma.jchess.graphics.utils.ChessPositionVector2Converter;
import com.ukma.jchess.graphics.utils.ChessSidePositionConverter;
import com.ukma.jchess.graphics.utils.MoveController;
import com.ukma.jchess.graphics.utils.PieceTextureManager;

import java.util.List;

public class ChessBoardPieces extends Sprite {
  private MoveController _controller;

  private List<ChessPiecePosition> _positions;
  private final PieceTextureManager _pieceTextureManager;


  public ChessBoardPieces(MoveController controller) {
    _controller = controller;

    controller.subscribeOnChessBoardUpdate(this::onChessBoardUpdate);
    onChessBoardUpdate();

    _pieceTextureManager = new PieceTextureManager();
    _pieceTextureManager.loadTextures(Gdx.files.internal("assets/ChessPieces.png"));
  }

  private void onChessBoardUpdate() {
    update(_controller.getBoard().getPositions());
  }

  private void update(List<ChessPiecePosition> positions) {
    _positions = positions;
  }

  @Override
  public void draw(Batch batch, float alphaModulation) {
    if(_positions == null)
      return;

    for (ChessPiecePosition position : _positions) {
      Vector2 positionCoordinates = ChessSidePositionConverter.chessToScreenCoordinates(ChessPositionVector2Converter.convertToVector2(position.getPosition()), _controller.getRenderSide());

      batch.draw(
        _pieceTextureManager.getTextureRegionByPiece(position.getPiece()),
        positionCoordinates.x,
        positionCoordinates.y,
        1f, 1f);
    }
  }
}
