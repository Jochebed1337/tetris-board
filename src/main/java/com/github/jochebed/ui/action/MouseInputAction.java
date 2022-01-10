package com.github.jochebed.ui.action;

import com.github.jochebed.state.State;

@FunctionalInterface
public interface MouseInputAction {
  void run(State state);
}
