package com.github.jochebed.state.list;

import com.github.jochebed.input.Input;
import com.github.jochebed.state.State;
import com.github.jochebed.ui.component.focusable.ButtonComponent;
import com.github.jochebed.ui.container.ComponentContainer;

import java.awt.*;

import static com.github.jochebed.ConstantsAndUtils.BOARD_HEIGHT;
import static com.github.jochebed.ConstantsAndUtils.BOARD_WIDTH;

public class MenuState extends State {
  private ComponentContainer container;

  public MenuState(Input input) {
    super(input);
    var highScoreContainer = new ComponentContainer(Color.DARK_GRAY, BOARD_WIDTH, BOARD_HEIGHT, ComponentContainer.Alignment.VERTICAL);

    container = new ComponentContainer(Color.DARK_GRAY, BOARD_WIDTH, BOARD_HEIGHT, ComponentContainer.Alignment.VERTICAL);
    container.addComponent(new ButtonComponent("Play", state -> state.setNextState(new GameState(input))));
    container.addComponent(new ButtonComponent("High score", state -> container = highScoreContainer));
    container.addComponent(new ButtonComponent("Quit", state -> System.exit(1)));
  }

  @Override
  public void tick() {
    input.mouseInput().tick(this);
    container.tick(this);
  }

  @Override
  public void render(Graphics graphics) {
    container.render(graphics);
  }
}
