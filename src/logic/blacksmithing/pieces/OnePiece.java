package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class OnePiece extends BlacksmithingPiece {
	public OnePiece() {
		super();
	}

	@Override
	public String toString() {
		return "1";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		if (row - 1 >= 0) {
			if (board[row - 1][column] != null) {
				moveList.add(new Point(row - 1, column));
			}
			if (column + 1 < 6 && board[row - 1][column + 1] != null) {
				moveList.add(new Point(row - 1, column + 1));
			}
			if (column - 1 >= 0 && board[row - 1][column - 1] != null) {
				moveList.add(new Point(row - 1, column - 1));
			}
		}
		if (column - 1 >= 0 && board[row][column - 1] != null) {
			moveList.add(new Point(row, column - 1));
		}
		if (column + 1 < 6 && board[row][column + 1] != null) {
			moveList.add(new Point(row, column + 1));
		}

		if (row + 1 < 6) {
			if (board[row + 1][column] != null) {
				moveList.add(new Point(row + 1, column));
			}
			if (column + 1 < 6 && board[row + 1][column + 1] != null) {
				moveList.add(new Point(row + 1, column + 1));
			}
			if (column - 1 >= 0 && board[row + 1][column - 1] != null) {
				moveList.add(new Point(row + 1, column - 1));
			}
		}

		return moveList;
	}
}
