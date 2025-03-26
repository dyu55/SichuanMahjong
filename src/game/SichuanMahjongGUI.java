/**
 * SichuanMahjongGUI.java
 *
 * Function:
 *   - Implements the graphical user interface (GUI) for Sichuan Mahjong using Java Swing.
 *   - Draws the player's hand using simple geometric shapes (square for "wan", triangle for "tiao", circle for "bing"),
 *     with the tile's rank displayed at the center.
 *   - Provides buttons for player actions (Peng, Gang, Hu) which show a prompt when clicked.
 *
 * Contains:
 *   - The main JFrame subclass SichuanMahjongGUI that sets up the window, game panel, and control buttons.
 *   - An inner class GamePanel (extends JPanel) which handles the drawing of tiles and processes button actions.
 *
 * Note:
 *   - The current tile drawing is simplified. In a future update, replace the drawing code with images from the "pictures" folder.
 *     Image file names should follow: tiao1-9, wan1-9, bing1-9.
 */
package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SichuanMahjongGUI extends JFrame {
    private GamePanel gamePanel;
    private JButton pengButton, gangButton, huButton;

    /**
     * Constructor: Sets up the main window, game panel, and control buttons.
     */
    public SichuanMahjongGUI() {
        setTitle("Sichuan Mahjong GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize and add the game panel
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // Initialize control panel and buttons
        JPanel control = new JPanel();
        pengButton = new JButton("Peng");
        gangButton = new JButton("Gang");
        huButton = new JButton("Hu");
        control.add(pengButton);
        control.add(gangButton);
        control.add(huButton);
        add(control, BorderLayout.SOUTH);

        // Add action listeners for the buttons
        pengButton.addActionListener(e -> gamePanel.handleAction("Peng"));
        gangButton.addActionListener(e -> gamePanel.handleAction("Gang"));
        huButton.addActionListener(e -> gamePanel.handleAction("Hu"));

        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main method: Application entry point. Launches the GUI in the event dispatch thread.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SichuanMahjongGUI::new);
    }
}

/**
 * GamePanel class
 *
 * Function:
 *   - Extends JPanel and is responsible for drawing the player's hand and other game elements.
 *   - Processes the Peng, Gang, and Hu button actions by showing prompt messages.
 *
 * Contains:
 *   - A list of Tile objects representing the player's hand.
 *   - A placeholder for the last discarded tile (if any).
 *   - Overridden paintComponent method to draw the tiles.
 */
class GamePanel extends JPanel {
    private java.util.List<Tile> hand = new ArrayList<>();
    private Tile lastDiscard;

    /**
     * Constructor: Sets the background color and initializes a sample hand with 13 tiles for demonstration.
     */
    public GamePanel() {
        setBackground(Color.WHITE);
        // Initialize sample hand (for demonstration, only using one suit)
        for (int i = 1; i <= 13; i++) {
            hand.add(new Tile(Suit.WAN, i));
        }
    }

    /**
     * Handles action events from the buttons by displaying a prompt.
     * @param action The player action (e.g., "Peng", "Gang", "Hu").
     */
    public void handleAction(String action) {
        JOptionPane.showMessageDialog(this, action + " action triggered");
    }

    /**
     * Overrides paintComponent to draw the player's hand and the last discarded tile (if any).
     * @param g Graphics object used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 20;
        for (Tile tile : hand) {
            tile.draw(g, x, 400);
            x += 60;
        }
        if (lastDiscard != null) {
            lastDiscard.draw(g, 400, 200);
        }
    }
}