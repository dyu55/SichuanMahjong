package game; /**
 * Game.java
 *
 * Function:
 *   - Implements the core game logic for Sichuan Mahjong.
 *   - Initializes the wall (deck) of tiles, shuffles, deals to players, and manages the game loop.
 *   - Handles drawing tiles, discarding, and processing actions like Peng, Gang, and Hu.
 *   - Sets up one human player and three AI players.
 *
 * Contains:
 *   - Methods to initialize the wall and players.
 *   - The main game loop that continues until the wall is empty.
 */
import java.util.*;
public class Game {
    private final List<Tile> wall = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();
    private int currentPlayer;

    public Game() {
        initWall();
        initPlayers();
        shuffleAndDeal();
        currentPlayer = 0;
    }

    private void initWall() {
        for (Suit suit : Suit.values()) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 0; j < 4; j++) {
                    wall.add(new Tile(suit, i));
                }
            }
        }
        Collections.shuffle(wall);
    }

    /**
     * Initializes players.
     * The first player is human (isAI = false) and the remaining three players are AI (isAI = true).
     */
    private void initPlayers() {
        players.add(new Player("Player1", false)); // Human player
        players.add(new Player("Player2", true));  // AI player
        players.add(new Player("Player3", true));  // AI player
        players.add(new Player("Player4", true));  // AI player
    }

    private void shuffleAndDeal() {
        // Each player gets 13 tiles at the start.
        for (Player p : players) {
            for (int i = 0; i < 13; i++) {
                p.draw(wall.remove(0));
            }
        }
    }

    public void play() {
        // Game loop continues until the wall is empty.
        while (!wall.isEmpty()) {
            Player p = players.get(currentPlayer);
            p.draw(wall.remove(0));
            if (p.checkHu()) {
                System.out.println(p.getName() + " Hu!");
            }
            Tile discarded = p.discard();
            // Check other players for possible actions (Peng, Gang) on the discarded tile.
            for (int i = 1; i < 4; i++) {
                Player next = players.get((currentPlayer + i) % 4);
                if (next.checkPeng(discarded)) { 
                    currentPlayer = players.indexOf(next); 
                    break; 
                }
                if (next.checkGang(discarded)) { 
                    currentPlayer = players.indexOf(next); 
                    break; 
                }
            }
            currentPlayer = (currentPlayer + 1) % 4;
        }
        System.out.println("Game ended in draw");
    }
}