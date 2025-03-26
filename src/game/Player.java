/**
 * Player.java
 *
 * Function:
 *   - Represents a player in the Sichuan Mahjong game.
 *   - Manages the player's hand, including drawing and discarding tiles.
 *   - Implements methods for checking actions like Peng, Gang, and Hu.
 *   - Differentiates between human and AI players via an isAI flag.
 *
 * Contains:
 *   - Player name and hand (a list of Tile objects).
 *   - Boolean flag isAI indicating whether this is an AI-controlled player.
 *   - Methods: draw (to add a tile), discard (to remove a tile), checkHu (win check),
 *     checkPeng (for a Peng action), and checkGang (for a Gang action).
 */
import java.util.*;
public class Player {
    private final String name;
    private final boolean isAI;
    private final List<Tile> hand = new ArrayList<>();

    /**
     * Constructor for a Player.
     * @param name the player's name.
     * @param isAI set true if the player is controlled by AI.
     */
    public Player(String name, boolean isAI) {
        this.name = name;
        this.isAI = isAI;
    }

    public String getName() {
        return name;
    }
    
    public boolean isAI() {
        return isAI;
    }

    /**
     * Adds a tile to the player's hand.
     * @param tile the tile to draw.
     */
    public void draw(Tile tile) {
        hand.add(tile);
    }

    /**
     * Discards a tile from the player's hand.
     * AI players always discard the right-most tile.
     * For a human player, this method can later be modified to allow manual selection.
     * Currently, the human also defaults to discarding the right-most tile.
     * @return the discarded Tile.
     */
    public Tile discard() {
        // AI always discards the right-most tile.
        // For a human, integrate input via GUI/console later.
        return hand.remove(hand.size() - 1);
    }

    public boolean checkHu() {
        return HandEvaluator.isPingHu(hand);
    }

    public boolean checkPeng(Tile tile) {
        long count = hand.stream().filter(t -> t.equals(tile)).count();
        if (count >= 2) {
            // Remove two tiles for Peng
            for (int i = 0; i < 2; i++) {
                for (Iterator<Tile> it = hand.iterator(); it.hasNext();) {
                    Tile t = it.next();
                    if (t.equals(tile)) {
                        it.remove();
                        break;
                    }
                }
            }
            System.out.println(name + " Peng!");
            return true;
        }
        return false;
    }

    public boolean checkGang(Tile tile) {
        long count = hand.stream().filter(t -> t.equals(tile)).count();
        if (count >= 3) {
            // Remove three tiles for Gang
            for (int i = 0; i < 3; i++) {
                for (Iterator<Tile> it = hand.iterator(); it.hasNext();) {
                    Tile t = it.next();
                    if (t.equals(tile)) {
                        it.remove();
                        break;
                    }
                }
            }
            System.out.println(name + " Gang!");
            return true;
        }
        return false;
    }
}