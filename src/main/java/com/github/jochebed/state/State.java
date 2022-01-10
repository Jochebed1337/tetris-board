package com.github.jochebed.state;

import com.github.jochebed.input.Input;
import lombok.Data;

import java.awt.*;

@Data
public abstract class State {
  protected final Input input;
  private State nextState;

  public State(Input input) {
    this.input = input;
  }

  public abstract void tick();
  public abstract void render(Graphics graphics);
}

