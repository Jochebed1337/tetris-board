package com.github.jochebed.input;

import com.github.jochebed.graphics.Display;
import com.github.jochebed.state.list.GameState;
import lombok.Setter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Setter
public class KeyboardInput extends KeyAdapter {
  private Display display;

  @Override
  public void keyPressed(KeyEvent e) {
    assert display != null;
    if(display.getState() instanceof GameState gameState)
      gameState.getCurrentPiece().respondToKey(e.getKeyCode());
  }
}
