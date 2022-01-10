package com.github.jochebed.data;

import java.io.*;
import java.util.Collections;

public class ScoreSave {
  private static final File SCORE_FILE = new File(System.getProperty("user.home") + File.pathSeparator + "tetris" + File.pathSeparator + "scores.txt");

  public void saveScore(int score) {
    try(var writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
      writer.write("");
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getLastScores() {
    try(var reader = new BufferedReader(new FileReader(SCORE_FILE))) {
      return Collections.max(reader.lines().map(s -> Integer.parseInt(s.split(":")[1])).toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return 0;
  }
}
