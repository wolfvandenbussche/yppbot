package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class HorsePiece extends BlacksmithingPiece {
	public HorsePiece() {
		super();
	}

	@Override
	public String toString() {
		return "H";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		// NoordWesten
		if (row - 1 >= 0 && column - 2 >= 0 && board[row - 1][column - 2] != null) {
			moveList.add(new Point(row - 1, column - 2));
		}
		if (row - 2 >= 0 && column - 1 >= 0 && board[row - 2][column - 1] != null) {
			moveList.add(new Point(row - 2, column - 1));
		}
		// NoordOosten
		if (row - 1 >= 0 && column + 2 < 6 && board[row - 1][column + 2] != null) {
			moveList.add(new Point(row - 1, column + 2));
		}
		if (row - 2 >= 0 && column + 1 < 6 && board[row - 2][column + 1] != null) {
			moveList.add(new Point(row - 2, column + 1));
		}
		// ZuidOosten
		if (row + 1 < 6 && column + 2 < 6 && board[row + 1][column + 2] != null) {
			moveList.add(new Point(row + 1, column + 2));
		}
		if (row + 2 < 6 && column + 1 < 6 && board[row + 2][column + 1] != null) {
			moveList.add(new Point(row + 2, column + 1));
		}
		// ZuidWesten
		if (row + 1 < 6 && column - 2 >= 0 && board[row + 1][column - 2] != null) {
			moveList.add(new Point(row + 1, column - 2));
		}
		if (row + 2 < 6 && column - 1 >= 0 && board[row + 2][column - 1] != null) {
			moveList.add(new Point(row + 2, column - 1));
		}

		return moveList;
	}
}