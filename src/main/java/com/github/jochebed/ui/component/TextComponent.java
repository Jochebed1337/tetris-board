package com.github.jochebed.ui.component;

import com.github.jochebed.state.State;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
public class TextComponent extends Component {
  private static final Canvas CANVAS = new Canvas();

  private String text;
  private Color foregroundColor;

  private final Font font;

  public TextComponent(String text, Color foregroundColor) {
    this.setText(text);
    this.setForegroundColor(foregroundColor);

    this.font = new Font("Press Start K", Font.PLAIN, 14);
  }

  @Override
  public void tick(State state) {
    var fontMetrics = CANVAS.getFontMetrics(this.getFont());
    int centerX = (getParent().getComponentX() + getParent().getComponentWidth() / 2) - fontMetrics.stringWidth(this.getText()) / 2;
    int centerY = (getParent().getComponentY() + getParent().getComponentHeight() / 2) + fontMetrics.getHeight() / 2;
    this.setComponentX(centerX);
    this.setComponentY(centerY);
  }

  @Override
  public void render(Graphics graphics) {
    graphics.setFont(font);
    graphics.setColor(foregroundColor);
    graphics.drawString(text, getComponentX(), getComponentY());
  }
}
