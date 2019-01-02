package controller;
import javax.swing.JFrame;

/**
 * Main class of the game. Starts a new game by creating a new JFrame.
 * 
 * @author junlin.yi
 *
 */
public class Game {

	/**
	 * Creates a new JFrame to start the game.
	 * 
	 * @param args Takes in command-line arguments (not yet implemented).
	 */
	public static void main(String[] args) {
		JFrame window = new Frame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
