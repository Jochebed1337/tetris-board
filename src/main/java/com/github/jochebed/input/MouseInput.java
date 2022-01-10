package com.github.jochebed.input;

import com.github.jochebed.state.State;
import com.github.jochebed.ui.input.MouseInputSubscriber;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
  @Setter
  private MouseInputSubscriber mouseInputSubscriber;

  @Getter
  private final Point mousePosition = new Point(-1, -1);
  private boolean mouseClicked;

  public void tick(State state) {
    if(mouseInputSubscriber != null && mouseClicked)
      mouseInputSubscriber.onClick(state);

    mouseClicked = false;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mouseClicked = true;
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    mousePosition.move(e.getX(), e.getY());
  }
}
