package com.github.jochebed.graphics;

import com.github.jochebed.input.Input;
import com.github.jochebed.input.KeyboardInput;
import com.github.jochebed.input.MouseInput;

import javax.swing.*;

public class Window extends JFrame {
  public Window(String windowTitle) {
    this.setTitle(windowTitle);

    var input = new Input(new KeyboardInput(), new MouseInput());

    JPanel panel = new Display(input);
    panel.addMouseListener(input.mouseInput());
    panel.addMouseMotionListener(input.mouseInput());

    this.add(panel);
    this.addKeyListener(input.keyboardInput());
    this.pack();

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setResizable(false);
  }
}
