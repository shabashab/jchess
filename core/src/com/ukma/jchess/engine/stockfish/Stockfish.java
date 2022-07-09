package com.ukma.jchess.engine.stockfish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stockfish {

  private Process engineProcess;
  private BufferedReader processReader;
  private OutputStreamWriter processWriter;

  Stockfish() {
    startEngine();
  }

  private static final String PATH = "executable/stockfish.exe";

  public boolean startEngine() {
    try {
      engineProcess = Runtime.getRuntime().exec(PATH);
      processReader = new BufferedReader(new InputStreamReader(
        engineProcess.getInputStream()));
      processWriter = new OutputStreamWriter(
        engineProcess.getOutputStream());
    } catch (Exception e) {
      return false;
    }
    return true;
  }


  public void sendCommand(String command) {
    try {
      processWriter.write(command + "\n");
      processWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getOutput(int waitTime) {
    StringBuffer buffer = new StringBuffer();
    try {
      Thread.sleep(waitTime);
      sendCommand("isready");
      while (true) {
        String text = processReader.readLine();
        if (text.equals("readyok"))
          break;
        else
          buffer.append(text + "\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buffer.toString();
  }

  public String getBestMove(String fen, int waitTime) {
      sendCommand("position fen " + fen);
      sendCommand("go movetime " + waitTime);
      String output = getOutput(waitTime + 20);

      if (output.split("bestmove ").length > 1)
      return output.split("bestmove ")[1].split(" ")[0];
    return null;
  }


  public void stopEngine() {
    try {
      sendCommand("quit");
      processReader.close();
      processWriter.close();
    } catch (IOException e) {
    }
  }

  public String getLegalMoves(String fen) {
    sendCommand("position fen " + fen);
    sendCommand("go perft 1");
    String output = getOutput(3000);
    return output;
  }

  public void drawBoard(String fen) {
    sendCommand("position fen " + fen);
    sendCommand("d");

    String[] rows = getOutput(0).split("\n");

    for (int i = 1; i < 18; i++) {
      System.out.println(rows[i]);
    }
  }
}