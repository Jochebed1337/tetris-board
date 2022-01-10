package com.github.jochebed.ui.component.focusable;

import com.github.jochebed.state.State;
import com.github.jochebed.ui.action.MouseInputAction;
import com.github.jochebed.ui.component.Component;
import com.github.jochebed.ui.component.TextComponent;
import com.github.jochebed.ui.input.MouseInputSubscriber;

import java.awt.*;

public final class ButtonComponent extends Component implements MouseInputSubscriber {
  private final TextComponent textComponent;
  private final MouseInputAction action;

  private Color buttonColor;

  public ButtonComponent(String text, MouseInputAction action) {
    this.textComponent = new TextComponent(text, Color.BLACK);
    this.textComponent.setParent(this);
    this.action = action;
  }

  @Override
  public void onClick(State state) {
    if(onFocus(state))
      action.run(state);
  }

  @Override
  public void tick(State state) {
    if(onFocus(state))
      state.getInput().mouseInput().setMouseInputSubscriber(this);

    textComponent.tick(state);
  }

  @Override
  public void render(Graphics graphics) {
    graphics.setColor(buttonColor);
    graphics.fillRect(getComponentX(), getComponentY(), getComponentWidth(), getComponentHeight());

    textComponent.render(graphics);
  }

  @Override
  public boolean onFocus(State state) {
    if(this.intersects(state.getInput().mouseInput().getMousePosition())) {
      buttonColor = Color.GRAY;
      return true;
    }
    buttonColor = Color.RED;
    return false;
  }
}
