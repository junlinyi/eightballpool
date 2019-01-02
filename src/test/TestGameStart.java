package test;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;
import org.junit.jupiter.api.Test;

import controller.Frame;

public class TestGameStart {
   
   /** Checks that creating a new Frame does not result in a null. */
   @Test
   public void randomlyTestReplaceWithRandomIndexes() {
	   JFrame frame = new Frame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		assertNotNull(frame);
   }
}
