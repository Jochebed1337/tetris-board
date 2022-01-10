package com.github.jochebed.graphics;

import com.github.jochebed.input.Input;
import com.github.jochebed.sound.Sound;
import com.github.jochebed.state.State;
import com.github.jochebed.state.list.MenuState;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

import static com.github.jochebed.ConstantsAndUtils.BOARD_HEIGHT;
import static com.github.jochebed.ConstantsAndUtils.BOARD_WIDTH;

@Getter
@Setter
public final class Display extends JPanel {
  // current state being displayed
  private State state;

  public Display(Input input) {
    this.setState(new MenuState(input));
    this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    input.keyboardInput().setDisplay(this);
    playMusic();
    createTimer();
  }

  private void createTimer() {
    var timer = new Timer(0, e -> {
      state.tick();
      switchState();
      repaint();
    });

    timer.setRepeats(true);
    timer.start();
  }

  private void playMusic() {
    Sound.BACKGROUND_MUSIC.play();
  }

  private void switchState() {
    if(state.getNextState() != null)
      this.setState(state.getNextState());
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);
    state.render(graphics);
  }
}
