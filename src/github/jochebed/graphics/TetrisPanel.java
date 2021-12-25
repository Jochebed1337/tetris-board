package github.jochebed.graphics;

import github.jochebed.graphics.logic.Piece;
import github.jochebed.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.KeyPair;

import static github.jochebed.ConstantsAndUtils.*;

public class TetrisPanel extends JPanel {
    // each block in the grid has a color
    private static final Color[][] BLOCK_GRID = new Color[COLUMNS][ROWS];
    // the tetromino that is currently falling down and that is controllable
    private Piece currentFallingPiece;
    private long lastFallTime = System.currentTimeMillis();

    public TetrisPanel() {
        this.currentFallingPiece = new Piece(this);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                currentFallingPiece.respondToKey(e.getKeyCode());
            }
        });
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Copyright issue. Can't upload it to GitHub, unfortunately.
        // Sound.BACKGROUND_MUSIC.play();
        var timer = new Timer(10, e -> {
            if (System.currentTimeMillis() - lastFallTime >= COOL_DOWN_TIME) {
                currentFallingPiece.accelerate();
                lastFallTime = System.currentTimeMillis();
            }
            currentFallingPiece.tick();
            repaint();
        });
        timer.setRepeats(true);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        renderBackground(g);
        currentFallingPiece.render(g);
        renderGrid(g);
    }

    private void renderBackground(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void renderGrid(Graphics graphics) {
        for (int c = 0; c < BLOCK_GRID.length; c++) {
            for (int r = 0; r < BLOCK_GRID[c].length; r++) {
                var block = BLOCK_GRID[c][r];
                if (block != null) {
                    graphics.setColor(block);
                    graphics.fillRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                graphics.setColor(Color.BLACK);
                graphics.drawRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public void spawnPiece() {
        currentFallingPiece = new Piece(this);
        if (!currentFallingPiece.canMove(0, 0)) clearAllLines();
    }

    private void clearAllLines() {
        for (int c = 0; c < COLUMNS; c++)
            for (int r = 0; r < ROWS; r++) BLOCK_GRID[c][r] = null;
    }

    public void fill(int x, int y, Color color) {
        TetrisPanel.BLOCK_GRID[x][y] = color;
    }

    public Color get(int x, int y) {
        return TetrisPanel.BLOCK_GRID[x][y];
    }
}
