package com.github.jochebed.state.list;

import com.github.jochebed.input.Input;
import com.github.jochebed.sound.Sound;
import com.github.jochebed.state.State;
import com.github.jochebed.ui.component.focusable.ButtonComponent;
import com.github.jochebed.ui.container.ComponentContainer;

import java.awt.*;

import static com.github.jochebed.ConstantsAndUtils.BOARD_HEIGHT;
import static com.github.jochebed.ConstantsAndUtils.BOARD_WIDTH;

public class GameOverState extends State {
  private final ComponentContainer container;

  public GameOverState(Input input) {
    super(input);
    this.container = new ComponentContainer(Color.BLACK, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, ComponentContainer.Alignment.VERTICAL);
    this.onGameOver();
  }

  private void onGameOver() {
    var button = new ButtonComponent("Game Over! Play again?", state -> this.setNextState(new MenuState(input)));
    this.container.addComponent(button);
    Sound.BACKGROUND_MUSIC.stop();
  }

  @Override
  public void tick() {
    container.tick(this);
    input.mouseInput().tick(this);
  }

  @Override
  public void render(Graphics graphics) {
    container.render(graphics);
  }
}
