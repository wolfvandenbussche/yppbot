package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class BarracksPiece extends BlacksmithingPiece {
	public BarracksPiece() {
		super();
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		if (row != 0 && board[0][column] != null) {
			moveList.add(new Point(0, column));
		}
		if (row != 5 && board[5][column] != null) {
			moveList.add(new Point(5, column));
		}
		if (column != 0 && board[row][0] != null) {
			moveList.add(new Point(row, 0));
		}
		if (column != 5 && board[row][5] != null) {
			moveList.add(new Point(row, 5));
		}

		return moveList;
	}
}