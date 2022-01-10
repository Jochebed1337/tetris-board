package com.github.jochebed.ui.component;

import com.github.jochebed.state.State;
import lombok.Data;

import java.awt.*;

@Data
public abstract class Component {
  private Component parent;

  private int componentX;
  private int componentY;

  private int componentWidth;
  private int componentHeight;

  public abstract void tick(State state);
  public abstract void render(Graphics graphics);
  protected final boolean intersects(Point point) {
    var rectangle = new Rectangle(componentX, componentY, componentWidth, componentHeight);
    return rectangle.contains(point.x, point.y);
  }
}
