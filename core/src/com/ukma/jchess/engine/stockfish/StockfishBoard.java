package com.ukma.jchess.engine.stockfish;

import com.ukma.jchess.engine.ChessBoard;
import com.ukma.jchess.engine.ChessPiecePosition;
import com.ukma.jchess.engine.ChessSide;
import com.ukma.jchess.engine.Move;

import java.util.List;

public class StockfishBoard implements ChessBoard {
    @Override
    public String getFEN() {
        return null;
    }

    @Override
    public void makeMove(Move move) {

    }

    @Override
    public ChessSide getCurrentSide() {
        return null;
    }

    @Override
    public List<ChessPiecePosition> getPositions() {
        return null;
    }
}
