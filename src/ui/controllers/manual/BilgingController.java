package ui.controllers.manual;

import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import logic.bilging.Bilging;
import logic.bilging.BilgingReader;
import logic.bilging.pieces.BilgePiece;
import ui.controllers.AbstractGameController;

public class BilgingController extends AbstractGameController implements Initializable {
	@FXML
	private ImageView indicator;

	@FXML	
	private GridPane moveGrid;

	private Bilging bilging;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		bilging = new Bilging();
		showBestMove();
		indicator.setVisible(true);

	}

	private void showBestMove() {
		/*Piece[][] boardArray = {
				{ new BlueCirclePiece(), new BlueSquarePiece(), new CyanSquarePiece(), new GreenCirclePiece(),new WhiteSquarePiece(), new YellowCirclePiece() },
				
				{ new BlueSquarePiece(), new CyanSquarePiece(), new BlueCirclePiece(), new WhiteSquarePiece(),new YellowCirclePiece(), new BlueCirclePiece() },
				
				{ new CyanSquarePiece(), new GreenCirclePiece(), new BlueCirclePiece(), new YellowCirclePiece(),new BlueCirclePiece(), new BlueSquarePiece() },
				
				{ new GreenCirclePiece(), new YellowCirclePiece(), new WhiteSquarePiece(), new BlueCirclePiece(),new YellowCirclePiece(), new YellowCirclePiece() },
				
				{ new WhiteSquarePiece(), new YellowCirclePiece(), new BlueCirclePiece(), new YellowCirclePiece(),new CyanSquarePiece(), new GreenCirclePiece() },
				
				{ new YellowCirclePiece(), new BlueSquarePiece(), new BlueCirclePiece(), new GreenCirclePiece(),new WhiteSquarePiece(), new YellowCirclePiece() },
				
				{ new BlueCirclePiece(), new BlueSquarePiece(), new CyanSquarePiece(), new GreenCirclePiece(),new WhiteSquarePiece(), new YellowCirclePiece() },
				
				{ new BlueCirclePiece(), new CyanSquarePiece(), new GreenCirclePiece(), new WhiteSquarePiece(),new YellowCirclePiece(), new BlueCirclePiece() },
				
				{ new CyanSquarePiece(), new GreenCirclePiece(), new WhiteSquarePiece(), new YellowCirclePiece(),	new BlueCirclePiece(), new BlueSquarePiece() },
				
				{ new GreenCirclePiece(), new WhiteSquarePiece(), new YellowCirclePiece(), new BlueCirclePiece(),
						new BlueSquarePiece(), new CyanSquarePiece() },
				
				{ new WhiteSquarePiece(), new YellowCirclePiece(), new BlueCirclePiece(), new BlueSquarePiece(),
						new CyanSquarePiece(), new GreenCirclePiece() },
				
				{ new YellowCirclePiece(), new BlueSquarePiece(), new CyanSquarePiece(), new GreenCirclePiece(),
						new WhiteSquarePiece(), new YellowCirclePiece() } };*/

		BilgePiece[][] boardArray = BilgingReader.readScreen();

		if (boardArray != null) {

			
			 ArrayList<Point> bestMoves = bilging.xMoves(boardArray);
			 
			  System.out.println(bestMoves);
			  
			  /*indicator.setVisible(true); 
			  Point bestMove = bestMoves.get(0);
			  moveGrid.getChildren().clear(); 
			  moveGrid.add(indicator, bestMove.y, bestMove.x);*/
			 

			/*indicator.setVisible(true);
			Point bestMove = bilging.bestOneMove(boardArray);
			System.out.println(bestMove);
			moveGrid.getChildren().clear();
			moveGrid.add(indicator, bestMove.y, bestMove.x);*/

		} else {
			indicator.setVisible(false);
			showBestMove();
		}
	}

	@Override
	public Point getUpperLeft() {
		return new Point(BilgingReader.X, BilgingReader.Y);
	}

	@Override
	public void pressKey() {
		showBestMove();
	}

	@Override
	public void clickMouse() {
		showBestMove();
	}

}
