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
    this.constructContainer(componentWidth, componentHeight, alignment);
  }

  public ComponentContainer(Color color,  int componentWidth, int componentHeight, Alignment alignment) {
    this.setColor(color);
    this.constructContainer(componentWidth, componentHeight, alignment);
  }

  private void constructContainer(int componentWidth, int componentHeight, Alignment alignment) {
    this.setComponentWidth(Math.round(componentWidth));
    this.setComponentHeight(Math.round(componentHeight));
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
      graphics.fillRect(0, 0, getComponentWidth(), getComponentHeight());
    } else graphics.drawImage(backgroundImage, 0, 0, getComponentWidth(), getComponentHeight(), null);
    this.components.forEach(component -> component.render(graphics));
  }

  public void realign(Alignment alignment) {
    var atomicInt = new AtomicInteger(0);
    switch (alignment) {
      case HORIZONTAL -> components.forEach(component -> {
        component.setComponentX(atomicInt.get());
        component.setComponentWidth(getComponentWidth() / components.size());
        atomicInt.addAndGet(component.getComponentWidth());
      });

      case VERTICAL -> components.forEach(component -> {
        component.setComponentY(atomicInt.get());
        component.setComponentWidth(getComponentWidth());
        component.setComponentHeight(getComponentHeight() / components.size());
        atomicInt.addAndGet(component.getComponentHeight());
      });
    }
  }
}
