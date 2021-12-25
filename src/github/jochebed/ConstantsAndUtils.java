package github.jochebed;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Edit constants to your likings.
 */
public final class ConstantsAndUtils {
    private ConstantsAndUtils() {
    }

    public static final int COLUMNS = 10;
    public static final int ROWS = 20;
    public static final int CELL_SIZE = 40;
    public static final long COOL_DOWN_TIME = 300L;

    public static final int WINDOW_WIDTH = COLUMNS * CELL_SIZE;
    public static final int WINDOW_HEIGHT = ROWS * CELL_SIZE;

    public static Color generateRandomColor() {
        var random = ThreadLocalRandom.current();
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();

        return new Color(red, green, blue);
    }

    public static Color generateCertainColor() {
        return Arrays.asList(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN).get(ThreadLocalRandom.current().nextInt(6));
    }
}
