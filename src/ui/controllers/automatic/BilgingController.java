package ui.controllers.automatic;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import logic.bilging.Bilging;
import logic.bilging.BilgingReader;
import logic.bilging.pieces.BilgePiece;
import ui.controllers.AbstractGameController;

public class BilgingController extends AbstractGameController implements Initializable {


	private Bilging bilging;
	private Robot robot;
	private Random random;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		bilging = new Bilging();
		random = new Random();
		try {
			robot = new Robot();
			showBestMove();
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	private void showBestMove() {
		BilgePiece[][] boardArray = BilgingReader.readScreen();

		if (boardArray != null) {

			
			 ArrayList<Point> bestMoves = bilging.xMoves(boardArray);
			 Point bestMove = bestMoves.get(0);
			 Rectangle puzzleBounds = BilgingReader.getPuzzleBounds();
			if(puzzleBounds!= null) {
				 int x = 28 + 2 + (random.nextInt(45)+1) -2 + (bestMove.y*45);
				 int y = (random.nextInt(40)+2) + (bestMove.x*45);
				 
				 robot.mouseMove(puzzleBounds.x + x, puzzleBounds.y + y);
				 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				 try {
					Thread.sleep(random.nextInt(48)+110);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			} else {
				try {
					Thread.sleep(random.nextInt(48)+110);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			 showBestMove();
			 
		} else {
			showBestMove();
		}
	}

	@Override
	public Point getUpperLeft() {
		return new Point(BilgingReader.X, BilgingReader.Y);
	}

	@Override
	public void pressKey() {
		//showBestMove();
	}

	@Override
	public void clickMouse() {
		//showBestMove();
	}

}
