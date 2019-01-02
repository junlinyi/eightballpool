package controller;
import javax.swing.JFrame;
import javax.swing.Timer;

import view.Panel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Frame class that is responsible for drawing and setting up various components
 * of the game, including the panels and a timer.
 * 
 * @author junlin.yi
 *
 */
public class Frame extends JFrame implements ActionListener {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 5838198657136087192L;

	private Panel content;
	private Overlay overlay;

	/**
	 * Constructor for the frame. Sets a title for the game and the size of the
	 * window.
	 */
	public Frame() {
		super();

		setTitle("Pool");
		setResizable(false);
		pack();

		setSize(Panel.WIDTH + getInsets().left + getInsets().right + 300,
				Panel.HEIGHT + getInsets().top + getInsets().bottom);

		content = new Panel();
		setContentPane(content);

		overlay = new Overlay();
		setGlassPane(overlay);
		getGlassPane().setVisible(true);

		Timer timer = new Timer(20, this);
		timer.start();
	}

	/**
	 * Repaints the frame after an action is performed.
	 * 
	 * @param e Action event generated.
	 */
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
