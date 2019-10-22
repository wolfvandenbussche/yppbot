package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class FourPiece extends BlacksmithingPiece {
	public FourPiece() {
		super();
	}

	@Override
	public String toString() {
		return "4";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		if (row - 4 >= 0) {
			if (board[row - 4][column] != null) {
				moveList.add(new Point(row - 4, column));
			}
			if (column + 4 < 6 && board[row - 4][column + 4] != null) {
				moveList.add(new Point(row - 4, column + 4));
			}
			if (column - 4 >= 0 && board[row - 4][column - 4] != null) {
				moveList.add(new Point(row - 4, column - 4));
			}
		}
		if (column - 4 >= 0 && board[row][column - 4] != null) {
			moveList.add(new Point(row, column - 4));
		}
		if (column + 4 < 6 && board[row][column + 4] != null) {
			moveList.add(new Point(row, column + 4));
		}
		if (row + 4 < 6) {
			if (board[row + 4][column] != null) {
				moveList.add(new Point(row + 4, column));
			}
			if (column + 4 < 6 && board[row + 4][column + 4] != null) {
				moveList.add(new Point(row + 4, column + 4));
			}
			if (column - 4 >= 0 && board[row + 4][column - 4] != null) {
				moveList.add(new Point(row + 4, column - 4));
			}
		}

		return moveList;
	}
}