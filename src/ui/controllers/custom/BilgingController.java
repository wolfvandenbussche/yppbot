package ui.controllers.custom;

import java.net.URL;
import java.util.ResourceBundle;

import logic.bilging.BilgingBoard;
import logic.bilging.pieces.BilgePiece;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ui.controllers.AbstractController;

public class BilgingController extends AbstractController implements Initializable {

	Image pieceCircle;

	@FXML
	private GridPane piecePane;

	@FXML
	private ImageView cursor;

	private int columns;
	private int rows;

	@FXML
	private Text latestMove;

	@FXML
	private Text score;

	private double piecePaneX;
	private double piecePaneY;

	private BilgingBoard board = new BilgingBoard();
	BilgePiece[][] boardArray = board.getBoardArray();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rows = board.getRows();
		columns = board.getColumns();

		piecePaneX = piecePane.getLayoutX();
		piecePaneY = piecePane.getLayoutY();

		updateBoard();
	}

	public void updateBoard() {
		piecePane.getChildren().clear();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				piecePane.add(boardArray[i][j].getImageView(), j, i);
			}
		}
	}

	@FXML
	void disableCursor(MouseEvent event) {
		if (cursor.isVisible()) {
			cursor.setVisible(false);
		}
	}

	@FXML
	void enableCursor(MouseEvent event) {
		if (!cursor.isVisible()) {
			cursor.setVisible(true);
		}

		int x = (int) ((event.getX() - 22.5) / 45);
		if (x < columns - 1) {
			x = (int) (x * 45 + piecePaneX);
			cursor.setLayoutX(x);
		}

		int y = (int) ((event.getY()) / 45);
		if (y < rows) {
			y = (int) (y * 45 + piecePaneY);
			cursor.setLayoutY(y);
		}
	}

	@FXML
	void makeMove(MouseEvent event) {
		int column = (int) ((event.getX() - 22.5) / 45);
		if (column < columns - 1) {
			int row = (int) ((event.getY()) / 45);
			if (row < rows) {
				System.out.println(row + " " + column);
				board.makeMove(row, column);
				boardArray = board.getBoardArray();
				updateBoard();
				latestMove.setText(board.getlatestComboAndScore());
				score.setText(Integer.toString(board.getScore()));
			}
		}

	}

}
