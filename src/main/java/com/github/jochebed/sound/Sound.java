package com.github.jochebed.sound;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public final class Sound {
  public static final Sound BACKGROUND_MUSIC = new Sound("tetris_theme.wav", true); // referenced from tetris dot com
  ///// MORE COMING SOON /////

  private Clip clip;
  private boolean loop;

  private Sound(String filePath, float volume, boolean loop) {
    this(filePath, loop);
    this.setVolume(volume);
  }

  private Sound(String filePath, boolean loop) {
    try {
      var inputStream = new BufferedInputStream(Objects.requireNonNull(Sound.class.getClassLoader().getResourceAsStream(filePath)));
      var audioInputStream = AudioSystem.getAudioInputStream(inputStream);
      this.loop = loop;
      this.createClip(audioInputStream);
    } catch (UnsupportedAudioFileException | IOException e) {
      System.out.println("Couldn't load sound. See stacktrace.");
      e.printStackTrace();
    }
  }

  private void setVolume(float volume) {
    var floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    floatControl.setValue(Math.max(floatControl.getMinimum(), Math.min(volume, floatControl.getMaximum())));
  }

  private void createClip(AudioInputStream audioInputStream) {
    try {
      this.clip = AudioSystem.getClip();
      clip.open(audioInputStream);
    } catch (LineUnavailableException | IOException e) {
      e.printStackTrace();
    }
  }

  public void play() {
    new Thread(() -> {
      clip.setFramePosition(0);
      clip.start();
      if(loop)
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }).start();
  }

  public void stop() {
    if(clip.isActive())
      clip.stop();
  }
}
