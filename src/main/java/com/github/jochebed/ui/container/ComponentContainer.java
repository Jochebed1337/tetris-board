package com.github.jochebed.ui.container;

import com.github.jochebed.state.State;
import com.github.jochebed.ui.component.Component;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
public class ComponentContainer extends Component {
  public enum Alignment {
    VERTICAL,
    HORIZONTAL
  }

  private final List<Component> components = new ArrayList<>();
  private Alignment alignment;

  private Image backgroundImage;
  private Color color;

  public ComponentContainer(Image backgroundImage, int componentWidth, int componentHeight, Alignment alignment) {
    this.setBackgroundImage(backgroundImage);
    this.constructContainer(0, 0, componentWidth, componentHeight, alignment);
  }

  public ComponentContainer(Image backgroundImage, int componentX, int componentY, int componentWidth, int componentHeight, Alignment alignment) {
    this.setBackgroundImage(backgroundImage);
    this.constructContainer(componentX, componentY, componentWidth, componentHeight, alignment);
  }

  public ComponentContainer(Color color, int componentWidth, int componentHeight, Alignment alignment) {
    this.setColor(color);
    this.constructContainer(0, 0, componentWidth, componentHeight, alignment);
  }

  public ComponentContainer(Color color, int componentX, int componentY, int componentWidth, int componentHeight, Alignment alignment) {
    this.setColor(color);
    this.constructContainer(componentX, componentY, componentWidth, componentHeight, alignment);
  }

  private void constructContainer(int componentX, int componentY, int componentWidth, int componentHeight, Alignment alignment) {
    this.setComponentX(componentX);
    this.setComponentY(componentY);
    this.setComponentWidth(componentWidth);
    this.setComponentHeight(componentHeight);
    this.setAlignment(alignment);
  }

  public void addComponent(Component component) {
    this.components.add(component);
    component.setParent(this);
  }

  public void tick(State state) {
    this.components.forEach(component -> component.tick(state));
    realign(alignment);
  }

  public void render(Graphics graphics) {
    if(backgroundImage == null) {
      graphics.setColor(color);
      graphics.fillRect(getComponentX(), getComponentY(), getComponentWidth(), getComponentHeight());
    } else graphics.drawImage(backgroundImage, getComponentX(), getComponentY(), getComponentWidth(), getComponentHeight(), null);
    this.components.forEach(component -> component.render(graphics));
  }

  private void realign(Alignment alignment) {
    var atomicInt = new AtomicInteger(alignment == Alignment.HORIZONTAL ? this.getComponentX() : this.getComponentY());
    switch (alignment) {
      case HORIZONTAL -> components.forEach(component -> {
        component.setComponentY(component.getComponentY());
        component.setComponentX(atomicInt.get());
        component.setComponentWidth(this.getComponentWidth() / components.size());
        component.setComponentHeight(this.getComponentHeight());
        atomicInt.addAndGet(component.getComponentWidth());
      });

      case VERTICAL -> components.forEach(component -> {
        component.setComponentX(this.getComponentX());
        component.setComponentY(atomicInt.get());
        component.setComponentWidth(this.getComponentWidth());
        component.setComponentHeight(this.getComponentHeight() / components.size());
        atomicInt.addAndGet(component.getComponentHeight());
      });
    }
  }
}
