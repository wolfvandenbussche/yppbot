package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class ThreePiece extends BlacksmithingPiece {
	public ThreePiece() {
		super();
	}

	@Override
	public String toString() {
		return "3";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		if (row - 3 >= 0) {
			if (board[row - 3][column] != null) {
				moveList.add(new Point(row - 3, column));
			}
			if (column + 3 < 6 && board[row - 3][column + 3] != null) {
				moveList.add(new Point(row - 3, column + 3));
			}
			if (column - 3 >= 0 && board[row - 3][column - 3] != null) {
				moveList.add(new Point(row - 3, column - 3));
			}
		}
		if (column - 3 >= 0 && board[row][column - 3] != null) {
			moveList.add(new Point(row, column - 3));
		}
		if (column + 3 < 6 && board[row][column + 3] != null) {
			moveList.add(new Point(row, column + 3));
		}
		if (row + 3 < 6) {
			if (board[row + 3][column] != null) {
				moveList.add(new Point(row + 3, column));
			}
			if (column + 3 < 6 && board[row + 3][column + 3] != null) {
				moveList.add(new Point(row + 3, column + 3));
			}
			if (column - 3 >= 0 && board[row + 3][column - 3] != null) {
				moveList.add(new Point(row + 3, column - 3));
			}
		}

		return moveList;
	}
}
