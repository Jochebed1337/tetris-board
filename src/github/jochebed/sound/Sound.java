package github.jochebed.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public final class Sound {
  public static final Sound BACKGROUND_MUSIC = new Sound("resources/tetris_theme.wav", true); // referenced from tetris dot com
  ///// MORE COMING SOON /////

  private Clip clip;
  private AudioInputStream audioInputStream;
  private float volume;
  private boolean loop;

  private Sound(String filePath, float volume, boolean loop) {
    this(filePath, loop);
    this.setVolume(volume);
  }

  private Sound(String filePath, boolean loop) {
    try {
      this.audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
      this.loop = loop;
      this.createClip();
    } catch (UnsupportedAudioFileException | IOException e) {
      System.out.println("Couldn't load sound.");
    }
  }

  private void setVolume(float volume) {
    FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    floatControl.setValue(Math.max(floatControl.getMinimum(), Math.min(volume, floatControl.getMaximum())));
  }

  private void createClip() {
    try {
      this.clip = AudioSystem.getClip();
      clip.open(audioInputStream);
    } catch (LineUnavailableException | IOException e) {
      e.printStackTrace();
    }
  }

  public void play() {
    clip.setFramePosition(0);
    clip.start();
    if(loop)
      clip.loop(Clip.LOOP_CONTINUOUSLY);
  }
}
