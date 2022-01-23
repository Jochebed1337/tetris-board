package com.github.jochebed;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Edit constants to your likings.
 */
@UtilityClass
public final class ConstantsAndUtils {
  public static final Font FONT
          = loadFont(
          Objects.requireNonNull(Launcher.class.getClassLoader().getResourceAsStream("font/retro_font.ttf")),
          Font.PLAIN,
          16);

  public static final Canvas CANVAS = new Canvas();

  public static final int COLUMNS = 10;
  public static final int ROWS = 20;
  public static final int CELL_SIZE = 40;
  public static final int ARC_SIZE = 10;
  public static final long COOL_DOWN_TIME = 300L;

  public static final int BOARD_WIDTH = COLUMNS * CELL_SIZE;
  public static final int BOARD_HEIGHT = ROWS * CELL_SIZE;

  public static Color generateRandomColor() {
    var random = ThreadLocalRandom.current();
    float red = random.nextFloat();
    float green = random.nextFloat();
    float blue = random.nextFloat();

    return new Color(red, green, blue);
  }

  public static Color generateCertainColor() {
    return List.of(
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.MAGENTA,
            Color.CYAN).get(ThreadLocalRandom.current().nextInt(6));
  }

  private static Font loadFont(InputStream resourceStream, int style, int size) {
    try {
      return Font.createFont(Font.PLAIN, resourceStream).deriveFont(style, size);
    } catch (FontFormatException | IOException e) {
      System.err.println("Couldn't format font!");
      return null;
    }
  }
}
