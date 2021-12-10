package github.jochebed.graphics.logic;

import github.jochebed.graphics.TetrisPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import static github.jochebed.ConstantsAndUtils.*;

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

  private final TetrisPanel tetrisPanel;
  private final Color color;
  private final Block[] blocks;

  /**
   * This only gets called when a new piece needs to be added.
   */
  public Piece(TetrisPanel tetrisPanel) {
    // generates a random color and assigns it to this piece
    this.color = generateRandomColor();
    // each piece consists of four blocks
    this.blocks = new Block[4];
    this.tetrisPanel = tetrisPanel;
    this.construct();
  }

  /**
   * Picks a random shape and assigns them to the piece.
   */
  private void construct() {
    // random choice
    int choice = ThreadLocalRandom.current().nextInt(SHAPES.length);
    // modifier to set random spawn
    int modifier = ThreadLocalRandom.current().nextInt(5);
    // assigning every block to the piece
    for (int i = 0; i < 4; i++) {
      int positionX = SHAPES[choice][i][0] + modifier;
      int positionY = SHAPES[choice][i][1];
      blocks[i] = new Block(positionX, positionY);
    }
  }

  public void tick() {
    checkSides();
    fillGrid();
    checkLines();

    // piece movement
    for (var block : blocks) {
      block.blockX += velocityX;
      block.blockY += velocityY;
    }
    velocityX = 0;
    velocityY = 0;
  }

  public void respondToKey(int key) {
    switch (key) {
      case KeyEvent.VK_UP -> this.rotate();
      case KeyEvent.VK_LEFT -> velocityX = -1;
      case KeyEvent.VK_RIGHT -> velocityX = 1;
      case KeyEvent.VK_DOWN -> velocityY = 1;
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

  private void checkLines() {
    // each tick the board gets checked for full lines
    for (int r = ROWS - 1; r > 0; r--)
      seeIfLineIsFullAndThenRemove(r);
  }

  private void checkSides() {
    // checks if one of the blocks touches the side
    // velocity needs to be != 0, so we can see if the player tries to go outside the table
    for (var block : blocks)
      if (block.blockX == 0 && velocityX < 0 || block.blockX == COLUMNS - 1 && velocityX > 0) {
        velocityX = 0;
        break;
      }
  }

  private void fillGrid() {
    // checks if one of the blocks touches the ground or another block of a piece.
    if (touchesBottom()) {
      // if yes we'll pass each block into the grid by its cell coordinate
      for (var block : blocks)
        tetrisPanel.fill(block.blockX, block.blockY, color);
      // of course after that a new piece will appear
      tetrisPanel.spawnPiece();
    }
  }

  private boolean touchesBottom() {
    for (var block : blocks)
      // checks if one block collides with ground or will collide with another
      if (block.blockY == ROWS - 1 || tetrisPanel.get(block.blockX, block.blockY + 1) != null)
        return true;
    return false;
  }

  /**
   * Calculates transformed location of each block and checks
   * if one of them is colliding with the side or is already in the grid
   * we won't rotate
   */
  private boolean canRotate(Block centerBlock) {
    for (var block : blocks) {
      int pivotX = block.blockX - centerBlock.blockX;
      int pivotY = block.blockY - centerBlock.blockY;
      int nextX = centerBlock.blockX - pivotY;
      int nextY = centerBlock.blockY + pivotX;
      if ((nextX < 0) || (nextX > COLUMNS - 1) || (tetrisPanel.get(nextX, nextY) != null))
        return false;
    }
    return true;
  }

  private void seeIfLineIsFullAndThenRemove(int line) {
    // check if line is full
    for (int c = 0; c < COLUMNS; c++) {
      var block = tetrisPanel.get(c, line);
      if (block == null)
        return;
    }

    // clear line if line is full
    for (int i = 0; i < COLUMNS; i++)
      tetrisPanel.fill(i, line, null);
    // reposition lines after one being cleared
    repositionBlocks(line);
  }

  /**
   * Looks stupid
   */
  public void accelerate() {
    velocityY = 1;
  }

  private void repositionBlocks(int line) {
    for (int c = 0; c < COLUMNS; c++)
      for (int r = line; r > 0; r--)
        tetrisPanel.fill(c, r, tetrisPanel.get(c, r - 1));
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
