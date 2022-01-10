package com.github.jochebed;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Edit constants to your likings.
 */
@UtilityClass
public final class ConstantsAndUtils {
  public static final int COLUMNS = 10;
  public static final int ROWS = 20;
  public static final int CELL_SIZE = 40;
  public static final long COOL_DOWN_TIME = 100L;

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
}
