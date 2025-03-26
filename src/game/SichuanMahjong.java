package game;
/**
 * SichuanMahjong.java
 *
 * Function:
 *   - This file is the entry point for the Sichuan Mahjong project.
 *   - It contains the main() method which starts the game logic.
 *   - You can choose to run either the console version or the GUI version.
 *
 * Contains:
 *   - The main() method that initializes the game.
 *   - A possible menu to select the console or GUI version.
 */


public class SichuanMahjong {
    public static void main(String[] args) {
        // For example, to start the console version:
        Game game = new Game();
        game.play();
        
        // Or to start the GUI version, uncomment the following line:
        // SwingUtilities.invokeLater(() -> new gui.SichuanMahjongGUI());
    }
}