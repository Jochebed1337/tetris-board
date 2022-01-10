package com.github.jochebed.state.list;

import com.github.jochebed.input.Input;
import com.github.jochebed.sound.Sound;
import com.github.jochebed.state.State;
import com.github.jochebed.ui.component.TextComponent;
import com.github.jochebed.ui.container.ComponentContainer;

import java.awt.*;

import static com.github.jochebed.ConstantsAndUtils.BOARD_HEIGHT;
import static com.github.jochebed.ConstantsAndUtils.BOARD_WIDTH;

public class GameOverState extends State {
  private final ComponentContainer container;

  public GameOverState(Input input) {
    super(input);
    this.container = new ComponentContainer(Color.BLACK, BOARD_WIDTH, BOARD_HEIGHT, ComponentContainer.Alignment.VERTICAL);

    var gameOverText = new TextComponent("GAME OVER!", Color.WHITE);
    this.container.addComponent(gameOverText);
    Sound.BACKGROUND_MUSIC.stop();
  }

  @Override
  public void tick() {
    container.tick(this);
  }

  @Override
  public void render(Graphics graphics) {
    container.render(graphics);
  }
}
