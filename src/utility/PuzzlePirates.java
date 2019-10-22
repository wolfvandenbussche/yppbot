package utility;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PuzzlePirates {

	public static void takeScreenshot(BufferedImage image, String name ) {
		/*try {
			File outputfile = new File("src/rsc/screenshots/"+name+".png");
			ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}*/
	}
	
	public static BufferedImage getImage(Rectangle rectangle) {
		BufferedImage image = null;
		try {
			Robot robot = new Robot();
			image = robot.createScreenCapture(rectangle);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void toTop() {
		ExternalWindowManager.restoreWindow();
	}
	
	public static Rectangle getPuzzlePiratesBounds() {
		Rectangle bounds =  ExternalWindowManager.getWindowBounds();
		if(bounds == null) {
			return null;
		}
		bounds.x += 3;
		bounds.width -= 6;
		
		bounds.y += 26;
		bounds.height -= 29;
		return bounds;
	}

	public static boolean isRunning() {
		return ExternalWindowManager.isWindowAvailable("Puzzle Pirates - Deathwoefke on the Emerald ocean");
	}

}
