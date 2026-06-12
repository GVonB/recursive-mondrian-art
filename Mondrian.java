// Gunnar Von Bergen
// 08/06/2025
// CSE 123
// C2: Mondrian Art
// TA: Trien
import java.util.*;
import java.awt.*;

/**
 * Generates a Mondrian-style piece of abstract art by recursively dividing a canvas
 * into smaller regions, coloring the regions randomly based on a selected mode.
 * In the basic mode, colors are randomly chosen based on the defined color pallete.
 * In the location-based mode, colors are tied to the positon of the region on the
 * canvas, with slight randomness. Regions are separated by a thin border.
 */
public class Mondrian {

    /**
     * Constant to determine how large borders should be between regions
     * and the edge of the picture.
     */
    private static final int BORDER = 1;

    /**
     * Constant to determine the smallest allowed size of a region after
     * a split in pixels.
     */
    private static final int MIN_REGION = 10;

    /**
     * Constant array of colors to use for Mondrian art style
     */
    private static final Color[] COLORS = {
        Color.RED, Color.YELLOW, Color.CYAN, Color.WHITE
    };

    /**
     * Creates a basic Mondrian-style image using random colors from the class color palette
     * for each region. The canvas is recursively divided into smaller regions, each filled with
     * one of these colors, with a thin border on each side.
     * 
     * @param pixels a 2d array of color objects representing pixels
     * @throws IllegalArgumentException if pixels array is null, or if
     *         pixels array width or height is less than 300.
     * @throws IllegalStateException if the color class consatnt array is empty.
     */
    public void paintBasicMondrian(Color[][] pixels) {
        validatePixels(pixels);
        boolean useLocationColor = false;
        divideRegion(pixels, 0, pixels[0].length, 0, pixels.length, useLocationColor);
    }

    /**
     * Creates a complex Mondrian-style image using location-based colors for each
     * region. The canvas is recursively divided into smaller regions, each filled with
     * a color derived from its position and separated with a thin border on each side.
     * 
     * @param pixels a 2d array of color objects representing pixels
     * @throws IllegalArgumentException if pixels array is null, or if
     *         pixels array width or height is less than 300.
     * @throws IllegalStateException if the color class consatnt array is empty.
     */
    public void paintComplexMondrian(Color[][] pixels) {
        validatePixels(pixels);
        boolean useLocationColor = true;
        divideRegion(pixels, 0, pixels[0].length, 0, pixels.length, useLocationColor);
    }

    /**
     * Divides a region of the canvas into smaller regions and fills them with
     * color based on the provided mode (defined colors, or location-based).
     * 
     * @param pixels a 2d array of color objects representing pixels
     * @param startX left bound of the current region (inclusive)
     * @param endX right bound of the current region (exclusive)
     * @param startY top bound of the current region (inclusive)
     * @param endY bottom bound of the current region (exclusive)
     * @param useLocationColor a boolean representing whether location-based color should be used
     * @throws IllegalStateException if the color class consatnt array is empty.
     */
    private void divideRegion(Color[][] pixels, int startX, int endX, 
                              int startY, int endY, boolean useLocationColor) {
        int fullWidth = pixels[0].length;
        int fullHeight = pixels.length;

        int regionWidth = endX - startX;
        int regionHeight = endY - startY;

        Random rand = new Random();

        boolean canVertDiv = regionWidth >= fullWidth / 4;
        boolean canHorizDiv = regionHeight >= fullHeight / 4;

        int splitX = startX;
        if (canVertDiv) {
            splitX = rand.nextInt(regionWidth - 2 * MIN_REGION) + startX + MIN_REGION;
        }

        int splitY = startY;
        if (canHorizDiv) {
            splitY = rand.nextInt(regionHeight - 2 * MIN_REGION) + startY + MIN_REGION;
        }

        // Base case (too small to divide), fill region
        if (!canVertDiv && !canHorizDiv) {
            // Add border to left/top only if against the edge
            int fillStartX = (startX == 0) ? startX + BORDER : startX;
            int fillStartY = (startY == 0) ? startY + BORDER : startY;
            // Always add border to bottom/right so no overlap/double borders
            int fillEndX = endX - BORDER;
            int fillEndY = endY - BORDER;

            fill(pixels, fillStartX, fillEndX, fillStartY, fillEndY, useLocationColor);
        } else if (canVertDiv && canHorizDiv) {
            divideRegion(pixels, startX, splitX, startY, splitY, useLocationColor);
            divideRegion(pixels, splitX, endX, startY, splitY, useLocationColor);
            divideRegion(pixels, startX, splitX, splitY, endY, useLocationColor);
            divideRegion(pixels, splitX, endX, splitY, endY, useLocationColor);
        } else if (canVertDiv) {
            divideRegion(pixels, startX, splitX, startY, endY, useLocationColor);
            divideRegion(pixels, splitX, endX, startY, endY, useLocationColor);
        } else {
            divideRegion(pixels, startX, endX, startY, splitY, useLocationColor);
            divideRegion(pixels, startX, endX, splitY, endY, useLocationColor);
        }
    }

    /**
     * Fills a selected region (startX, endX], (startY, endY] with a random color
     * from the class constant array of colors.
     * 
     * @param pixels a 2d array of color objects that will be modified
     * @param startX the starting x coordinate to be filled in the image (inclusive)
     * @param endX the ending x coordinate to be filled in the image (exclusive)
     * @param startY the starting y coordinate to be filled in the image (inclusive)
     * @param endY the ending y coordinate to be fill2ed in the image (exclusive)
     * @throws IllegalStateException if no colors are set in the class constant.
     */
    private static void fill(Color[][] pixels, int startX, int endX,
                            int startY, int endY, boolean useLocationColor) {
        Color randColor = getRandomColor();
        if (useLocationColor) {
            // Get a float representation of how far the region is in both directions
            float ratioX = (float) (startX + endX) / 2 / pixels[0].length;
            float ratioY = (float) (startY + endY) / 2 / pixels.length;
            // Getting the blue from the already random color keeps a touch of randomness
            float blue = randColor.getBlue() / 255f;
            randColor = new Color(ratioX, ratioY, blue);
        }

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                pixels[y][x] = randColor;
            }
        }
    }

    /**
     * Returns a random color from those specified in the class constant
     * array of colors.
     * 
     * @return a color object with the randomly generated color from the options
     *         available inside the class constant array of colors.
     * @throws IllegalStateException if no colors are set in the class constant.
     */
    private static Color getRandomColor() {
        if (COLORS.length < 1) {
            throw new IllegalStateException("No colors are set in class constant");
        }
        Random rand = new Random();
        return COLORS[rand.nextInt(COLORS.length)];
    }

    /**
     * Ensures a 2d array of colors is valid not null and has both dimensions
     * greater than or equal to 300 pixels.
     * 
     * @param pixels a 2d array of color objects representing pixels
     * @throws IllegalArgumentException if pixels array is null, or if
     *         pixels array width or height is less than 300. 
     */
    private void validatePixels(Color[][] pixels) {
        if (pixels == null) {
            throw new IllegalArgumentException("Pixel array is null");
        }
        if (pixels.length < 300 || pixels[0].length < 300) {
            throw new IllegalArgumentException("Pixel dimensions are too small");
        }
    }
}
