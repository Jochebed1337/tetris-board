package github.jochebed;

import javax.swing.*;
import java.awt.*;

import static github.jochebed.ConstantsAndUtils.*;

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
}
