package github.jochebed.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class Sound {
  public static final Sound BACKGROUND_MUSIC = new Sound("src/resources/tetris_theme.wav"); // referenced from tetris dot com
  ///// MORE COMING SOON /////

  private final Set<Clip> clips = new HashSet<>();
  private Clip clip;
  private AudioFormat audioFormat;
  private byte[] audioBytes;
  private float volume;

  private Sound(String filePath, float volume) {
    this(filePath);
    this.setVolume(volume);
  }

  private Sound(String filePath) {
    try {
      var audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
      this.audioFormat = audioInputStream.getFormat();
      this.audioBytes = audioInputStream.readAllBytes();
      this.createClip();
    } catch (UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
  }

  private void setVolume(float volume) {
    FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    floatControl.setValue(Math.max(floatControl.getMinimum(), Math.min(volume, floatControl.getMaximum())));
  }

  private Clip createClip() {
    try {
      this.clip = AudioSystem.getClip();
      clip.open(audioFormat, audioBytes, 0, audioBytes.length);
      this.clips.add(clip);
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    }

    return clip;
  }

  public void play() {
    var clip = this.clips.stream()
            .filter(c -> c.getFramePosition() == 0 || c.getFramePosition() == c.getFrameLength())
            .findFirst()
            .orElseGet(this::createClip);
    clip.setFramePosition(0);
    clip.start();
  }

  public Clip getClip() {
    return clip;
  }
}
