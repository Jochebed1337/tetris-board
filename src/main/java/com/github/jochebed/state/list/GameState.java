package com.github.jochebed.state.list;

import com.github.jochebed.data.ScoreSave;
import com.github.jochebed.graphics.logic.Piece;
import com.github.jochebed.input.Input;
import com.github.jochebed.state.State;
import lombok.Getter;

import java.awt.*;

import static com.github.jochebed.ConstantsAndUtils.*;

public class GameState extends State {
  private static final Color[][] TETRIS_MATRIX = new Color[COLUMNS][ROWS];
  @Getter private final ScoreSave scoreSave;
  private long lastFallTime;
  @Getter private Piece currentPiece;
  private int score;

  public GameState(Input input) {
    super(input);
    this.scoreSave = new ScoreSave();
    this.lastFallTime = System.currentTimeMillis();
    this.currentPiece = new Piece(this);
    this.score = 0;
  }

  @Override
  public void tick() {
    if(System.currentTimeMillis() - lastFallTime >= COOL_DOWN_TIME) {
      currentPiece.accelerate();
      lastFallTime = System.currentTimeMillis();
    }
    currentPiece.tick();
  }

  @Override
  public void render(Graphics graphics) {
    renderBackground(graphics);
    currentPiece.render(graphics);
    renderMatrix(graphics);
  }

  private void renderBackground(Graphics graphics) {
    graphics.setColor(Color.DARK_GRAY);
    graphics.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
  }

  private void renderMatrix(Graphics graphics) {
    for(int c = 0; c < COLUMNS; c++) {
      for(int r = 0; r < ROWS; r++) {
        // non-null block in grid
        var block = TETRIS_MATRIX[c][r];
        // draw block
        if(block != null) {
          graphics.setColor(block);
          graphics.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
        // draw grid
        graphics.setColor(Color.BLACK);
        graphics.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
      }
    }
  }

  private void clearTable() {
    for(int col = 0; col < COLUMNS; col++)
      for(int row = 0; row < ROWS; row++)
        TETRIS_MATRIX[col][row] = null;
  }

  public void fill(int x, int y, Color color) {
    TETRIS_MATRIX[x][y] = color;
  }

  public Color get(int x, int y) {
    return TETRIS_MATRIX[x][y];
  }

  public void spawnPiece() {
    currentPiece = new Piece(this);
    if(!currentPiece.canMove(0, 0)) {
      clearTable();
      this.setNextState(new GameOverState(this.getInput()));
    }
  }

  public void addScore(int score) {
    this.score += score;
  }
}
