package view;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.swing.JPanel;

import model.Ball;
import speed.Speed;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Responsible for handling game mechanics such as play/pause, starting the
 * game, ending the game, as well as in-game interactions such as collisions.
 * 
 * @author junlin.yi
 *
 */
public class Panel extends JPanel {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = -8483422457690002318L;

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;

	public static final int BALLS = 16;
	public static ArrayList<Ball> ball = new ArrayList<Ball>();
	private static Map<String, Integer> playerData = new HashMap<String, Integer>();

	private double next;
	private Ball ball1;
	private Ball ball2;

	Stack<Double> xpos = new Stack<Double>();
	Stack<Double> ypos = new Stack<Double>();

	double radius = 14;
	double mass = 20;

	private static boolean scratch = false;
	public static boolean stripeTurn = true;
	private static boolean contTurn = false;
	public static boolean stripeDone = false;
	public static boolean solidDone = false;
	private static boolean paused = true;
	public static boolean started = false;
	public static boolean gameOver = false;
	private static boolean update = false;

	/**
	 * Constructor for the Panel class. Sets up the look/appearance of the game
	 * window and adds the balls to the ArrayList {@code_ball}.
	 */
	public Panel() {
		super();

		setOpaque(true);
		setBackground(new Color(255, 255, 255));

		int row1 = 0, row2 = 0, row3 = 0, row4 = 0;
		for (int i = 0; i < 16; i++) {
			if (i < 1) {
				xpos.push((double) (3 * WIDTH / 4));
				ypos.push((double) (HEIGHT / 2));
			} else if (i < 2) {
				xpos.push(WIDTH / 8 + 9 * radius);
				ypos.push((double) 300);
			} else if (i < 4) {
				xpos.push(WIDTH / 8 + 7 * radius);
				ypos.push(284.5 + row1 * 31);
				row1++;
			} else if (i < 7) {
				xpos.push(WIDTH / 8 + 5 * radius);
				ypos.push((double) (269 + row2 * 31));
				row2++;
			} else if (i < 11) {
				xpos.push(WIDTH / 8 + 3 * radius);
				ypos.push(253.5 + row3 * 31);
				row3++;
			} else {
				xpos.push(WIDTH / 8 + radius);
				ypos.push((double) (238 + row4 * 31));
				row4++;
			}
		}

		for (int i = 0; i < 16; i++) {
			if (i == 15)
				ball.add(new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0), "cueball"));
			else if (i == 10)
				ball.add(
						new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0), "eightball"));
			else if (i % 2 == 0)
				ball.add(new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0), "stripe"));
			else
				ball.add(new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0), "solid"));
		}

		// Subtract the value when a ball is pocketed.
		// If value isn't 0 and 8ball goes in then player loses.
		playerData.put("solid", 7);
		playerData.put("stripe", 7);

	}

	/**
	 * Paints the game window with each update depending on whether the game is
	 * paused or playing.
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);

		super.paintComponent(g);

		if (gameOver) {

			int row1 = 0, row2 = 0, row3 = 0, row4 = 0;
			for (int i = 0; i < 16; i++) {
				if (i < 1) {
					xpos.push((double) (3 * WIDTH / 4));
					ypos.push((double) (HEIGHT / 2));
				} else if (i < 2) {
					xpos.push(WIDTH / 8 + 9 * radius);
					ypos.push((double) 300);
				} else if (i < 4) {
					xpos.push(WIDTH / 8 + 7 * radius);
					ypos.push(284.5 + row1 * 31);
					row1++;
				} else if (i < 7) {
					xpos.push(WIDTH / 8 + 5 * radius);
					ypos.push((double) (269 + row2 * 31));
					row2++;
				} else if (i < 11) {
					xpos.push(WIDTH / 8 + 3 * radius);
					ypos.push(253.5 + row3 * 31);
					row3++;
				} else {
					xpos.push(WIDTH / 8 + radius);
					ypos.push((double) (238 + row4 * 31));
					row4++;
				}
			}
			ball = new ArrayList<Ball>();

			for (int i = 0; i < 16; i++) {
				if (i == 15)
					ball.add(new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0),
							"cueball"));
				else if (i == 10)
					ball.add(new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0),
							"eightball"));
				else if (i % 2 == 0)
					ball.add(new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0),
							"stripe"));
				else
					ball.add(
							new Ball((double) xpos.pop(), (double) ypos.pop(), radius, mass, new Speed(0, 0), "solid"));
			}

			started = false;
			stripeTurn = true;
			contTurn = false;
			stripeDone = false;
			solidDone = false;
			paused = true;
		} else {
			if (!paused) {
				started = true;
				// Change to ArrayList to remove the balls
				// If stripeCont is true, then it is the stripe player's turn.
				for (int i = 0; i < ball.size(); i++) {
					if (ball.get(i).getX() > WIDTH - 50 && ball.get(i).getY() > HEIGHT - 50
							|| ball.get(i).getX() > WIDTH - 50 && ball.get(i).getY() < 50
							|| ball.get(i).getX() < 50 && ball.get(i).getY() > HEIGHT - 50
							|| ball.get(i).getX() < 50 && ball.get(i).getY() < 50
							|| ball.get(i).getX() < WIDTH / 2 + 25 && ball.get(i).getX() > WIDTH / 2 - 25
									&& ball.get(i).getY() > HEIGHT - 50
							|| ball.get(i).getX() < WIDTH / 2 + 25 && ball.get(i).getX() > WIDTH / 2 - 25
									&& ball.get(i).getY() < 50) {
						if (ball.get(i).getType().equals("cueball")) {
							scratch = true;
							// Switch the turn.
							contTurn = false;
							ball.add(new Ball(3 * WIDTH / 4, HEIGHT / 2, radius, mass, new Speed(0, 0), "cueball"));
						} else if (ball.get(i).getType().equals("stripe") && stripeTurn) {
							if (!scratch)
								contTurn = true;
							playerData.put("stripe", playerData.get("stripe") - 1);
							if (playerData.get("stripe") == 0) {
								stripeDone = true;
							}
						} else if (ball.get(i).getType().equals("solid") && !stripeTurn) {
							if (!scratch)
								contTurn = true;
							playerData.put("solid", playerData.get("solid") - 1);
							if (playerData.get("solid") == 0) {
								solidDone = true;
							}
						} else if (ball.get(i).getType().equals("eightball")) {
							gameOver = true;

						}
						ball.remove(i);
						if (ball.size() == 0)
							gameOver = true;
					}

				}

				double passed = 0.0;
				while (passed + next < 1.0) {
					for (int i = 0; i < ball.size(); i++) {

						if (ball.get(i) == ball1)
							ball.get(i).collide(ball2, next);
						else if (ball.get(i) != ball2)
							ball.get(i).move(next);
					}
					passed += next;
					updateCollision();
				}
				next += passed;
				next -= 1.0;

				boolean moving = false;
				for (int i = 0; i < ball.size(); i++) {
					ball.get(i).move(1.0 - passed);
					if (ball.get(i).getSpeed().getX() > 0.02 || ball.get(i).getSpeed().getX() < -0.02
							|| ball.get(i).getSpeed().getY() > 0.02 || ball.get(i).getSpeed().getY() < -0.02)
						moving = true;
				}
				if (scratch) {
					if (stripeTurn)
						g2d.drawString("Scratch. Player 1 loses the turn.", WIDTH + 100, 2000);
					else
						g2d.drawString("Scratch. Player 2 loses the turn.", WIDTH + 100, 200);
				}
				if (!moving) {
					paused = true;
					scratch = false;
					if (!contTurn) {
						stripeTurn = !stripeTurn;
					}
					contTurn = false;
				}
			}

			g2d.setColor(Color.GREEN);
			g2d.fill(new Rectangle2D.Double(0, 0, Panel.WIDTH, Panel.HEIGHT));

			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.WHITE);
			g2d.drawLine(3 * Panel.WIDTH / 4, 0, 3 * Panel.WIDTH / 4, Panel.HEIGHT);
			g2d.draw(new Arc2D.Double(Panel.WIDTH * 3 / 4 - Panel.HEIGHT / 8, 3 * Panel.HEIGHT / 8, Panel.HEIGHT / 4,
					Panel.HEIGHT / 4, 90, -180, Arc2D.OPEN));
			g2d.setColor(Color.getHSBColor(140, 22, 52));
			g2d.fill(new Rectangle2D.Double(Panel.WIDTH, 0, 40, Panel.HEIGHT));
			g2d.setColor(Color.BLACK);
			g2d.fill(new Ellipse2D.Double(-10, -10, 50, 50));
			g2d.fill(new Ellipse2D.Double(WIDTH / 2, -10, 50, 50));
			g2d.fill(new Ellipse2D.Double(-10, Panel.HEIGHT - 40, 50, 50));
			g2d.fill(new Ellipse2D.Double(WIDTH / 2, HEIGHT - 40, 50, 50));
			g2d.fill(new Ellipse2D.Double(WIDTH - 40, -10, 50, 50));
			g2d.fill(new Ellipse2D.Double(WIDTH - 40, HEIGHT - 40, 50, 50));

			// Paints each ball.
			for (int i = 0; i < ball.size(); i++) {
				ball.get(i).paint(g2d);
			}
		}

		if (update)
			updateCollision();
	}

	/**
	 * Pauses the game.
	 */
	public static void pause() {
		paused = true;
	}

	/**
	 * Unpauses the game/starts it if the game has ended.
	 */
	public static void play() {
		paused = false;
		gameOver = false;
	}

	/**
	 * Pauses if on play, plays if on pause.
	 */
	public static void toggle() {
		paused = !paused;
	}

	/**
	 * Checks if the game is paused.
	 * 
	 * @return Returns true if the game is paused, false otherwise.
	 */
	public static boolean isPaused() {
		return paused;
	}

	/**
	 * Indicates that the game has updated and needs to be repainted.
	 */
	public static void setUpdate() {
		update = true;
	}

	/**
	 * Updates the collisions between in-game objects by switching the indexes of
	 * the affected balls in the ArrayList {@code_ball} if applicable.
	 */
	public void updateCollision() {
		next = 1000000000;

		for (int i = 0; i < ball.size() - 1; i++) {
			for (int j = i + 1; j < ball.size(); j++) {
				double min = ball.get(i).minCollision(ball.get(j));
				if (min < next) {
					next = min;
					ball1 = ball.get(i);
					ball2 = ball.get(j);
				}
			}
		}
		update = false;
	}
}
