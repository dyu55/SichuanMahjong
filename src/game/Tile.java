/**
 * Tile.java
 *
 * Function:
 *   - Represents a single Mahjong tile.
 *   - Contains the tile's suit and rank.
 *   - Provides a method to draw the tile.
 *
 * Contains:
 *   - Fields for suit (an enum) and rank (number).
 *   - Overridden equals(), hashCode(), and toString() methods.
 *   - A draw() method that currently draws a simple shape (square, triangle, or circle).
 *
 * Note:
 *   - In the future, you will replace the drawn shapes with actual images.
 *     Place the images in a folder called "pictures" with filenames: tiao1-9, wan1-9, bing1-9.
 */
package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Tile.java
 * Loads its image from /pictures/<suit><rank>.png (e.g. wan1.png, tiao5.png, tong9.png)
 */
public class Tile {
    public final Suit suit;
    public final int rank;
    private static final int SIZE = 50;
    private static final Map<String, BufferedImage> IMAGES = new HashMap<>();

    public Tile(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        loadImage();
    }

    private void loadImage() {
        String key = suit.name().toLowerCase() + rank;
        if (!IMAGES.containsKey(key)) {
            try {
                BufferedImage img = ImageIO.read(getClass().getResource("/pictures/" + key + ".png"));
                IMAGES.put(key, img);
            } catch (IOException|NullPointerException e) {
                System.err.println("Failed to load image: " + key + ".png");
            }
        }
    }

    public void draw(Graphics g, int x, int y) {
        BufferedImage img = IMAGES.get(suit.name().toLowerCase() + rank);
        if (img != null) {
            g.drawImage(img, x, y, SIZE, SIZE, null);
        } else {
            // fallback: draw border if image missing
            g.setColor(Color.RED);
            g.drawRect(x, y, SIZE, SIZE);
            g.drawString(rank + "", x + SIZE/3, y + SIZE/2);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile other = (Tile) o;
        return this.rank == other.rank && this.suit == other.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
    @Override public String toString() { return suit + " " + rank; }
}