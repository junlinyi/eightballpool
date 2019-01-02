package test;
import javax.swing.JFrame;

import controller.Frame;

/**
 * JUnit test class for creating a JFrame.
 * 
 * @author junlin.yi
 *
 */
public class Test {
	public static void main(String[] args) {
		JFrame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
