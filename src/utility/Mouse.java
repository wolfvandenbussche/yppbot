package utility;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class Mouse {
	
	private Robot robot;
	
	public Mouse() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void moveTo(Point point) {
		robot.mouseMove(point.x, point.y);
		randomSleep(1000,1200);
	}
	
	public void clickAt(Point point) {
		randomSleep(1000,1200);
		robot.mouseMove(point.x, point.y);    
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		randomSleep(197, 321);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	private void randomSleep(int min, int max) {
		try {
			Thread.sleep((long)((Math.random() * (max-min)) + min ));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
