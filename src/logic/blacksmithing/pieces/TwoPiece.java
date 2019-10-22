package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class TwoPiece extends BlacksmithingPiece {
	public TwoPiece() {
		super();
	}

	@Override
	public String toString() {
		return "2";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		if (row - 2 >= 0) {
			if (board[row - 2][column] != null) {
				moveList.add(new Point(row - 2, column));
			}
			if (column + 2 < 6 && board[row - 2][column + 2] != null) {
				moveList.add(new Point(row - 2, column + 2));
			}
			if (column - 2 >= 0 && board[row - 2][column - 2] != null) {
				moveList.add(new Point(row - 2, column - 2));
			}
		}
		if (column - 2 >= 0 && board[row][column - 2] != null) {
			moveList.add(new Point(row, column - 2));
		}
		if (column + 2 < 6 && board[row][column + 2] != null) {
			moveList.add(new Point(row, column + 2));
		}
		if (row + 2 < 6) {
			if (board[row + 2][column] != null) {
				moveList.add(new Point(row + 2, column));
			}
			if (column + 2 < 6 && board[row + 2][column + 2] != null) {
				moveList.add(new Point(row + 2, column + 2));
			}
			if (column - 2 >= 0 && board[row + 2][column - 2] != null) {
				moveList.add(new Point(row + 2, column - 2));
			}
		}

		return moveList;
	}
}