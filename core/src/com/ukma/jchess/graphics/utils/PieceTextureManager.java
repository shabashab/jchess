package com.ukma.jchess.graphics.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ukma.jchess.engine.ChessPiece;

import java.util.EnumMap;
import java.util.Map;

public class PieceTextureManager {
  private Map<ChessPiece, TextureRegion> _pieceTextureMap;
  private boolean _texturesLoaded;

  public PieceTextureManager() {
    _pieceTextureMap = new EnumMap<ChessPiece, TextureRegion>(ChessPiece.class);
    _texturesLoaded = false;
  }

  public void loadTextures(FileHandle textureFileHandle) {
    if(textureFileHandle.isDirectory())
      throw new IllegalArgumentException("Given handle is the handle of the directory. Must be a handle of a file");

    if(!textureFileHandle.exists())
      throw new IllegalArgumentException("File for given handle does not exist");

    final int SINGLE_TEXTURE_WIDTH = 60;
    final int SINGLE_TEXTURE_HEIGHT = 60;

    Texture piecesAtlas = new Texture(textureFileHandle);

    TextureRegion[] regions = new TextureRegion[12];

    for(int x = 0; x < 6; x++) {
      for(int y = 0; y < 2; y++) {
        int i = (y * 6) + x;

        int xPosition = SINGLE_TEXTURE_WIDTH * x;
        int yPosition = SINGLE_TEXTURE_HEIGHT * y;

        TextureRegion region = new TextureRegion(piecesAtlas, xPosition, yPosition, SINGLE_TEXTURE_WIDTH, SINGLE_TEXTURE_HEIGHT);
        regions[i] = region;
      }
    }

    //Black pieces
    _pieceTextureMap.put(ChessPiece.QUEEN_BLACK, regions[0]);
    _pieceTextureMap.put(ChessPiece.KING_BLACK, regions[1]);
    _pieceTextureMap.put(ChessPiece.ROOK_BLACK, regions[2]);
    _pieceTextureMap.put(ChessPiece.KNIGHT_BLACK, regions[3]);
    _pieceTextureMap.put(ChessPiece.BISHOP_BLACK, regions[4]);
    _pieceTextureMap.put(ChessPiece.PAWN_BLACK, regions[5]);


    //White pieces
    _pieceTextureMap.put(ChessPiece.QUEEN_WHITE, regions[6]);
    _pieceTextureMap.put(ChessPiece.KING_WHITE, regions[7]);
    _pieceTextureMap.put(ChessPiece.ROOK_WHITE, regions[8]);
    _pieceTextureMap.put(ChessPiece.KNIGHT_WHITE, regions[9]);
    _pieceTextureMap.put(ChessPiece.BISHOP_WHITE, regions[10]);
    _pieceTextureMap.put(ChessPiece.PAWN_WHITE, regions[11]);

    _texturesLoaded = true;
  }

  public TextureRegion getTextureRegionByPiece(ChessPiece piece) {
    if(!_texturesLoaded)
      throw new IllegalStateException("Textures hasn't been loaded yet. Please call loadTextures() to load textures");

    if(!_pieceTextureMap.containsKey(piece))
      throw new IllegalStateException("No texture has been found for given piece");

    return _pieceTextureMap.get(piece);
  }
}
