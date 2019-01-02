package model;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

import speed.Speed;
import view.Panel;

/**
 * The Ball class that is responsible for storing information about each ball,
 * such as its type, size, mass, etc. Also handles the physics of the collisions
 * between balls and the table.
 * 
 * @author junlin.yi
 *
 */
public class Ball {
   private double x;
   private double y;
   private double radius = 10;
   private double mass = 1;
   private Speed speed;
   private String type;

   public static final Color lighter[] = { new Color(239, 41, 41), new Color(114, 159, 207), new Color(252, 234, 79),
            new Color(252, 175, 62), new Color(138, 226, 52), new Color(173, 127, 168), new Color(136, 138, 133),
            new Color(233, 185, 110) };
   public static final Color colors[] = { new Color(204, 0, 0), new Color(52, 101, 164), new Color(237, 212, 0),
            new Color(245, 121, 0), new Color(115, 210, 22), new Color(117, 80, 123), new Color(85, 87, 83),
            new Color(193, 125, 17) };
   public static final Color darker[] = { new Color(164, 0, 0), new Color(32, 74, 135), new Color(196, 160, 0),
            new Color(206, 92, 0), new Color(78, 154, 6), new Color(92, 53, 102), new Color(46, 52, 54),
            new Color(143, 89, 2) };
   private static int color_count = 0;
   private int color = 0;

   /**
    * Constructs a ball object with a predefined location, size, mass, speed,
    * and type.
    * 
    * @param x
    *           The x coordinate of the ball.
    * @param y
    *           The y coordinate of the ball.
    * @param radius
    *           The radius of the ball.
    * @param mass
    *           The mass of the ball.
    * @param speed
    *           The initial speed of the ball.
    * @param type
    *           The type of the ball (either stripe, nonstripe, cue, or black).
    */
   public Ball(double x, double y, double radius, double mass, Speed speed, String type) {
      this.x = x;
      this.y = y;
      setRadius(radius);
      setMass(mass);
      this.speed = speed;
      this.color = color_count++ % 8;
      this.type = type;
   }

   /**
    * Gets the speed of the ball.
    * 
    * @return Returns the speed as a Speed object.
    */
   public Speed getSpeed() {
      return speed;
   }

   /**
    * Gets the x coordinate of the position of the ball.
    * 
    * @return Returns a double containing the x coordinate.
    */
   public double getX() {
      return x;
   }

   /**
    * Gets the y coordinate of the position of the ball.
    * 
    * @return Returns a double containing the y coordinate.
    */
   public double getY() {
      return y;
   }

   /**
    * Gets the radius of the ball.
    * 
    * @return Returns a double containing the radius.
    */
   public double getRadius() {
      return radius;
   }

   /**
    * Gets the type of the ball.
    * 
    * @return Returns a String containing the type. Either "stripe",
    *         "nonstripe", "cue", or "black".
    */
   public String getType() {
      return type;
   }

   /**
    * Gets the mass of the ball.
    * 
    * @return Returns a double containing the mass.
    */
   public double getMass() {
      return mass;
   }

   /**
    * Gets the color of the ball.
    * 
    * @return Returns an int indicating the index of the color of the ball in
    *         the color array {@code_colors}.
    */
   public int getColor() {
      return color;
   }

   /**
    * Changes the radius of the ball.
    * 
    * @param radius2
    *           A double containing what the ball's radius should be set to.
    */
   public void setRadius(double radius2) {
      this.radius = radius2;
   }

   /**
    * Changes the mass of the ball.
    * 
    * @param mass2
    *           A double containing what the ball's mass should be set to.
    */
   public void setMass(double mass2) {
      this.mass = mass2;
   }

   /**
    * Increases the index of the color in the color array {@code_colors}.
    * 
    * @param colorIndex
    *           The amount to increment the array index by.
    */
   public void setColor(int colorIndex) {
      this.color = (colorIndex + 8) % 8;
   }

   /**
    * Moves the ball for a specified amount of the time.
    * 
    * @param time
    *           The amount of time to move for.
    */
   public void move(double time) {
      move(speed.getX() * time, speed.getY() * time);
   }

   /**
    * Changes the x and y position of the ball.
    * 
    * @param x
    * @param y
    */
   public void move(double x, double y) {
      this.x += x;
      this.y += y;

      if (this.x < radius) {
         this.x = 2 * radius - this.x;
         speed.addX(-2 * speed.getX());
         Panel.setUpdate();
      }

      if (this.x > Panel.WIDTH - radius) {
         this.x = 2 * (Panel.WIDTH - radius) - this.x;
         speed.addX(-2 * speed.getX());
         Panel.setUpdate();
      }

      if (this.y < radius) {
         this.y = 2 * radius - this.y;
         speed.addY(-2 * speed.getY());
         Panel.setUpdate();
      }

      if (this.y > Panel.HEIGHT - radius) {
         this.y = 2 * (Panel.HEIGHT - radius) - this.y;
         speed.addY(-2 * speed.getY());
         Panel.setUpdate();
      }
   }

   /**
    * Colors the ball according to the current color.
    * 
    * @param g
    *           The current graphics context, used to paint the ball.
    */
   public void paint(Graphics2D g) {
      g.setColor(colors[color]);
      g.fill(new Ellipse2D.Double(x - radius, y - radius, 2 * radius, 2 * radius));
   }

   /**
    * Doesn't actually handle the collision, but calculates the minimum value of
    * the change between the current ball and the ball it collides with.
    * 
    * @param next
    *           The ball to be collided with.
    * 
    * @return Returns the minimum value obtained from the collision with
    *         {@code_next}.
    */
   public double minCollision(Ball next) {
      double dX = getX() - next.getX();
      double dY = getY() - next.getY();
      double dVelocityX = speed.getX() - next.getSpeed().getX();
      double dVelocityY = speed.getY() - next.getSpeed().getY();

      if (dVelocityX == 0 && dVelocityY == 0) return Double.POSITIVE_INFINITY;

      double a = dVelocityX * dVelocityX + dVelocityY * dVelocityY;
      double b = dVelocityX * dX + dVelocityY * dY;
      double c = Math.pow(radius + next.getRadius(), 2) * a - Math.pow(dVelocityX * dY - dVelocityY * dX, 2);

      if (c < 0) return Double.POSITIVE_INFINITY;

      double start = (-b - Math.sqrt(c)) / a;
      double end = (-b + Math.sqrt(c)) / a;

      if (end < 0) return Double.POSITIVE_INFINITY;

      if (start < (start - end) / 2) // Large approximation, but it should work
         return Double.POSITIVE_INFINITY;

      if (start < 0) return 0.0;

      return start;
   }

   /**
    * Collides the current ball with {@code_next}.
    * 
    * @param next
    *           The ball to be collided with.
    * 
    * @param time
    *           The amount of time elapsed in the collision.
    */
   public void collide(Ball next, double time) {
      move(speed.getX() * time, speed.getY() * time);
      next.move(next.getSpeed().getX() * time, next.getSpeed().getY() * time);

      double m1 = getMass();
      double m2 = next.getMass();
      double angle = Math.atan2(next.getY() - getY(), next.getX() - getX());
      double v1 = speed.getComponent(angle);
      double v2 = next.getSpeed().getComponent(angle);
      double w1 = ((m1 - m2) * v1 + 2 * m2 * v2) / (m1 + m2);
      double w2 = ((m2 - m1) * v2 + 2 * m1 * v1) / (m1 + m2);

      speed.addComponent(angle, -v1 + w1);
      next.getSpeed().addComponent(angle, -v2 + w2);
   }
}