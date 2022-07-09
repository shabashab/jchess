package com.ukma.jchess.engine.stockfish;

import com.ukma.jchess.engine.Move;

public class StockfishTest {
  public static void main(String[] args) {

    Stockfish stockfish = new Stockfish();
    stockfish.startEngine();




    StockFishEngine stockFishEngine = new StockFishEngine();
    StockfishBoard stockfishBoard = (StockfishBoard) stockFishEngine.createBoard();
    /*stockfish.drawBoard(stockfishBoard.getFEN());
    stockfishBoard.makeMove(new Move("e1", "g1"));
    stockfish.drawBoard(stockfishBoard.getFEN());*/

    stockFishEngine.setSkillLevel(0);

    stockfish.sendCommand("uci");


    while(true) {
      stockFishEngine.getAvailableMoves(stockfishBoard);
      stockfishBoard.makeMove(stockFishEngine.getBestMove(stockfishBoard));
      stockfish.drawBoard(stockfishBoard.getFEN());
      System.out.println("\n\n");
    }



    /*Stockfish client = new Stockfish();
    String FEN = "rnbqkb1r/pppp2pp/4pn2/5p2/2PP4/5NP1/PP2PP1P/RNBQKB1R b";


    // initialize and connect to engine
    if (client.startEngine()) {
      System.out.println("Engine has started..");
    } else {
      System.out.println("Oops! Something went wrong..");
    }

    StockfishBoard stockfishBoard = new StockfishBoard();

    FEN = stockfishBoard.getFEN();

    client.drawBoard(FEN);

    stockfishBoard.makeMove(new Move("d2", "d4"));
    stockfishBoard.makeMove(new Move("d7", "d5"));
    stockfishBoard.makeMove(new Move("g1", "g3"));
    stockfishBoard.makeMove(new Move("g8", "f6"));
    stockfishBoard.makeMove(new Move("d1", "d2"));
    stockfishBoard.makeMove(new Move("c8", "h3"));
    stockfishBoard.makeMove(new Move("g2", "h3"));


    FEN = stockfishBoard.getFEN();
    System.out.println(FEN);
    client.drawBoard(FEN);

    //System.out.println(client.getBestMove(FEN, 2000));

    System.out.println("Stopping engine..");
    client.stopEngine();
*/

  }
}
