package github.jochebed;

import github.jochebed.graphics.TetrisPanel;

import javax.swing.*;
import java.awt.*;

import static github.jochebed.ConstantsAndUtils.WINDOW_HEIGHT;
import static github.jochebed.ConstantsAndUtils.WINDOW_WIDTH;

public class GameWindow extends JFrame {
  private final JPanel panel;

  public GameWindow(String title, JPanel panel) {
    super(title);
    this.panel = panel;
    this.load();
  }

  private void load() {
    this.add(panel);
    this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.addKeyListener(panel.getKeyListeners()[0]);
  }

  public static void main(String[] args) {
    new GameWindow("Tetris", new TetrisPanel());
  }
}
