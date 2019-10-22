package ui.controllers.manual;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import logic.blacksmithing.Blacksmithing;
import logic.blacksmithing.BlacksmithingReader;
import logic.blacksmithing.pieces.BlacksmithingPiece;
import ui.controllers.AbstractGameController;

public class BlacksmithingController extends AbstractGameController implements Initializable {
	@FXML
	private ImageView indicator;

	@FXML
	private GridPane moveGrid;

	private Blacksmithing blacksmithing;

	@FXML
	private ProgressIndicator progress;

	ArrayList<Point> bestMoves;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		blacksmithing = new Blacksmithing();
		start();
	}

	private void start() {
		BestMoves myRunnable = new BestMoves();
		Thread t = new Thread(myRunnable);
		t.start();
	}

	Point move = null;

	private void showMove() {
		// Moet uitgesteld worden om na het laden van de UI dit uit te voeren (door
		// movegrid)
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (bestMoves.size() != 0) {
					indicator.setVisible(true);
					move = bestMoves.remove(0);
					moveGrid.getChildren().clear();
					moveGrid.add(indicator, move.y, move.x);
				}
			}
		});
	}

	@Override
	public Point getUpperLeft() {
		// -16 door de progress bar
		return new Point(BlacksmithingReader.X, BlacksmithingReader.Y - 16);
	}

	@Override
	public void pressKey() {
		showMove();
		amountOfMoves--;
		if(amountOfMoves == 0) {
			indicator.setVisible(false);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	start();
			            }
			        }, 
			        2000 
			);
			
		}
	}

	@Override
	public void clickMouse() {
	}

	private int amountOfMoves;
	// Aparte thread anders kan de progress niet getoond worden.
	private class BestMoves implements Runnable {
		public void run() {
			BlacksmithingPiece[][] boardArray = BlacksmithingReader.readScreen();
			if (boardArray != null) {
				Timeline task = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
						new KeyFrame(Duration.seconds(2), new KeyValue(progress.progressProperty(), 1)));
				task.setCycleCount(Timeline.INDEFINITE);
				task.playFromStart();
				bestMoves = blacksmithing.bestOrder(boardArray);
				System.out.println(Arrays.deepToString(bestMoves.toArray()));
				amountOfMoves = bestMoves.size();
				task.stop();
				progress.setProgress(1);
				showMove();
			} else {
                new java.util.Timer().schedule( 
    			        new java.util.TimerTask() {
    			            @Override
    			            public void run() {
    			            	BestMoves myRunnable = new BestMoves();
    			        		Thread t = new Thread(myRunnable);
    			        		t.start();
    			            }
    			        }, 
    			        1000 
    			);
			}
		}

	}
}
