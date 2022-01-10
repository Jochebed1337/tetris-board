package com.github.jochebed.graphics.logic;

import com.github.jochebed.state.list.GameState;
import com.github.jochebed.state.list.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.jochebed.ConstantsAndUtils.*;

public class Piece {
  private static final int[][][] SHAPES = {
          {{0, 0}, {0, 1}, {1, 1}, {1, 2}},
          {{1, 0}, {1, 1}, {0, 1}, {0, 2}},
          {{0, 0}, {0, 1}, {0, 2}, {1, 2}},
          {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
          {{0, 0}, {0, 1}, {0, 2}, {1, 1}},
          {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
          {{1, 0}, {1, 1}, {1, 2}, {0, 2}}
  };

  private int velocityX;
  private int velocityY;
  private int lines;

  private final GameState gameState;
  private final Color color;
  private final Block[] blocks;

  /**
   * This only gets called when a new piece needs to be added.
   */
  public Piece(GameState gameState) {
    // generates a random color and assigns it to this piece
    this.color = generateRandomColor();
    // each piece consists of four blocks
    this.blocks = new Block[4];
    this.gameState = gameState;
    this.construct();
  }

  /**
   * Picks a random shape and assigns them to the piece.
   */
  private void construct() {
    // random choice
    int choice = ThreadLocalRandom.current().nextInt(SHAPES.length);
    // modifier to set random spawn
    int modifier = ThreadLocalRandom.current().nextInt(1, COLUMNS - 1);
    // assigning every block to the piece
    for (int i = 0; i < 4; i++) {
      int positionX = SHAPES[choice][i][0] + modifier;
      int positionY = SHAPES[choice][i][1];
      blocks[i] = new Block(positionX, positionY);
    }
  }

  public void tick() {
    this.fillGrid();
    this.checkBoard();

    // piece movement
    if (canMove(velocityX, velocityY)) {
      for (var block : blocks) {
        block.blockX += velocityX;
        block.blockY += velocityY;
      }
    }
    velocityX = 0;
    velocityY = 0;
  }

  public void respondToKey(int key) {
    switch (key) {
      case KeyEvent.VK_LEFT -> velocityX = -1;
      case KeyEvent.VK_RIGHT -> velocityX = 1;
      case KeyEvent.VK_DOWN -> velocityY = 1;
      case KeyEvent.VK_UP -> this.rotate();
      case KeyEvent.VK_SPACE -> this.moveDownInstant();
      case KeyEvent.VK_ESCAPE -> gameState.setNextState(new MenuState(gameState.getInput()));
    }
  }

  private void moveDownInstant() {
    while(this.canMove(0, 1)) {
      for(var block : blocks) block.blockY++;
    }
  }

  private void rotate() {
    var centerBlock = blocks[1];
    if (!canRotate(centerBlock)) return;
    for (var block : blocks) {
      int pivotX = block.blockX - centerBlock.blockX;
      int pivotY = block.blockY - centerBlock.blockY;
      block.blockX = centerBlock.blockX - pivotY;
      block.blockY = centerBlock.blockY + pivotX;
    }
  }

  private void checkBoard() {
    // each tick the board gets checked for full lines
    for (int row = ROWS - 1; row > 0; row--)
      checkRow(row);
    switch (lines) {
      case 1 -> gameState.addScore(40);
      case 2 -> gameState.addScore(100);
      case 3 -> gameState.addScore(300);
      case 4 -> gameState.addScore(1200);
    }
    lines = 0;
  }

  public boolean canMove(int destX, int destY) {
    for (var block : blocks) {
      int x = destX + block.blockX;
      int y = destY + block.blockY;

      if (x < 0 || x >= COLUMNS || y < 0 || y >= ROWS || gameState.get(x, y) != null)
        return false;
    }
    return true;
  }


  private void fillGrid() {
    // checks if one of the blocks touches the ground or another block of a piece.
    if (!canMove(0, velocityY)) {
      // if yes we'll pass each block into the grid by its cell coordinate
      for (var block : blocks)
        gameState.fill(block.blockX, block.blockY, color);
      // of course after that a new piece will appear
      gameState.spawnPiece();
    }
  }

  /**
   * Calculates transformed location of each block and checks
   * if one of them is colliding with the side or is already in the grid
   * if yes we won't rotate
   */
  private boolean canRotate(Block centerBlock) {
    for (var block : blocks) {
      int pivotX = block.blockX - centerBlock.blockX;
      int pivotY = block.blockY - centerBlock.blockY;
      int nextX = centerBlock.blockX - pivotY;
      int nextY = centerBlock.blockY + pivotX;
      if (nextY >= ROWS || nextX < 0 || nextX >= COLUMNS || gameState.get(nextX, nextY) != null)
        return false;
    }
    return true;
  }

  private void checkRow(int line) {
    // check if line is full
    for (int c = 0; c < COLUMNS; c++) {
      var block = gameState.get(c, line);
      if (block == null) return;
    }
    // if yes then the line count will be increased.
    // it will be used later to determine the score @checkBoard()V
    lines++;

    // clear line if line is full
    for (int i = 0; i < COLUMNS; i++)
      gameState.fill(i, line, null);
    // reposition lines after one being cleared
    repositionBlocks(line);
  }

  public void accelerate() {
    velocityY = 1;
  }

  private void repositionBlocks(int line) {
    for (int c = 0; c < COLUMNS; c++)
      for (int r = line; r > 0; r--)
        gameState.fill(c, r, gameState.get(c, r - 1));
  }

  public void render(Graphics graphics) {
    graphics.setColor(color);
    for (var block : blocks)
      graphics.fillRect(block.blockX * CELL_SIZE, block.blockY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
  }

  private static final class Block {
    private int blockX;
    private int blockY;

    private Block(int blockX, int blockY) {
      this.blockX = blockX;
      this.blockY = blockY;
    }
  }
}
