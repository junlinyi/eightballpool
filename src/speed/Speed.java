package speed;
/**
 * A helper class that handles speed movement in the physics of the balls.
 * Stores information relating to the position and velocity of objects.
 * 
 * @author junlin.yi
 *
 */
public class Speed {
	private double x = 0;
	private double y = 0;

	/**
	 * Constructor for speed class. Initializes the velocity in terms of the x and y
	 * components.
	 * 
	 * @param x The x component of velocity.
	 * @param y The y component of velocity.
	 */
	public Speed(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x component of velocity.
	 * 
	 * @return Returns a double with the x component.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y component of velocity.
	 * 
	 * @return Returns a double with the y component.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the component velocity in a specified direction.
	 * 
	 * @param theta The angle to get the component velocity in.
	 * 
	 * @return Returns a double containing the component velocity.
	 */
	public double getComponent(double theta) {
		return Math.cos(theta) * x + Math.sin(theta) * y;
	}

	/**
	 * Adds a specified amount of speed to the x component.
	 * 
	 * @param speed The amount of speed to add.
	 */
	public void addX(double speed) {
		x += 0.99 * speed;
	}

	/**
	 * Adds a specified amount of speed to the y component.
	 * 
	 * @param speed The amount of speed to add.
	 */
	public void addY(double speed) {
		y += 0.9 * speed;
	}

	/**
	 * Sets the x component of speed to the specified speed.
	 * 
	 * @param speed The speed to set the x speed to.
	 */
	public void setX(double speed) {
		x = speed;
	}

	/**
	 * Sets the y component of speed to the specified speed.
	 * 
	 * @param speed The speed to set the y speed to.
	 */
	public void setY(double speed) {
		y = speed;
	}

	/**
	 * Adds a component velocity at a specified angle and speed.
	 * 
	 * @param theta The direction to add to the component velocity.
	 * 
	 * @param speed The amount of speed to add.
	 */
	public void addComponent(double theta, double speed) {
		x += Math.cos(theta) * speed;
		y += Math.sin(theta) * speed;
	}
}
