import java.awt.Color;

public class Client {
    public static void main(String[] args) {
        // 1. Create a new picture with size 400 x 400
        Picture p = new Picture(400, 400);
        // 2. Get the pixels out of the image
        Color[][] pixels = p.getPixels();
        // 3. Call fill, providing a specific region
        fill(pixels, 0, p.width(), 0, p.height());
        // 4. Set the pixels of the image
        p.setPixels(pixels);
        // 5. Save the image to display it
        p.save("fillregion.jpg");
    }

    // TODO: Implement fill below (this solution can be iterative)
    public static void fill (Color[][] pixels, int x1, int x2, int y1, int y2) {
        // x1/y1 - Inclusive    x2/y2 - Exlcusive
        for (int row = x1 + 1; row < x2 - 1; row++) {
            for (int col = y1 + 1; col < y2 - 1; col++) {
                pixels[row][col] = Color.WHITE;
            }
        }
    }
}
