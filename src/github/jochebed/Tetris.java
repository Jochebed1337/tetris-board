package github.jochebed;

import github.jochebed.graphics.TetrisPanel;

public class Tetris {
    private static GameWindow window;
    private boolean running;

    public static void main(String[] args) {
        new Tetris().run();
    }

    public void run() {
        running = true;
        window = new GameWindow("Tetris", new TetrisPanel());
    }
}
