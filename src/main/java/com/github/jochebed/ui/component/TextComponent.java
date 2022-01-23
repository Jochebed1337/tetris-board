package com.github.jochebed.ui.component;

import com.github.jochebed.state.State;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

import static com.github.jochebed.ConstantsAndUtils.CANVAS;
import static com.github.jochebed.ConstantsAndUtils.FONT;

@Setter
@Getter
public final class TextComponent extends Component {
  private String text;
  private Color foregroundColor;

  public TextComponent(String text, Color foregroundColor) {
    this.setText(text);
    this.setForegroundColor(foregroundColor);
  }

  @Override
  public void tick(State state) {
    var fontMetrics = CANVAS.getFontMetrics(FONT);
    int centerX = (getParent().getComponentX() + getParent().getComponentWidth() / 2) - fontMetrics.stringWidth(this.getText()) / 2;
    int centerY = (getParent().getComponentY() + getParent().getComponentHeight() / 2) + fontMetrics.getHeight() / 2;
    this.setComponentX(centerX);
    this.setComponentY(centerY);
  }

  @Override
  public void render(Graphics graphics) {
    graphics.setFont(FONT);
    graphics.setColor(foregroundColor);
    graphics.drawString(text, getComponentX(), getComponentY());
  }
}
