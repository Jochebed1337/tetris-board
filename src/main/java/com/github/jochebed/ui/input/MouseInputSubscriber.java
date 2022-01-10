package com.github.jochebed.ui.input;

import com.github.jochebed.state.State;
import com.github.jochebed.ui.component.focusable.Focusable;

public interface MouseInputSubscriber extends Focusable {
  void onClick(State state);
}
