package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class TowerPiece extends BlacksmithingPiece {
	public TowerPiece() {
		super();
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		// NoordOosten
		if (column != 5 && row != 0) {
			if (5 - row > column && board[0][(5 - (5 - row) + 5 - (5 - column))] != null) {
				// System.out.println("Noorderrand");
				moveList.add(new Point(0, (5 - (5 - row) + 5 - (5 - column))));
			}
			if (5 - row < column && board[5 - (5 - row) - (5 - column)][5] != null) {
				// System.out.println("Oosterrand");
				moveList.add(new Point(5 - (5 - row) - (5 - column), 5));
			}
			if (5 - row == column && board[0][5] != null) {
				// System.out.println("NoordOostDiagonaal");
				moveList.add(new Point(0, 5));
			}
		}
		// NoordWesten
		if (column != 0 && row != 0) {
			if (row > column && board[(5 - column) - (5 - row)][0] != null) {
				// System.out.println("Westerrand");
				moveList.add(new Point((5 - column) - (5 - row), 0));
			}
			if (row < column && board[0][(5 - row) - (5 - column)] != null) {
				// System.out.println("Noorderrand");
				moveList.add(new Point(0, (5 - row) - (5 - column)));
			}
			if (row == column && board[0][0] != null) {
				// System.out.println("NoordWestDiagonaal");
				moveList.add(new Point(0, 0));
			}
		}
		// Zuidwesten
		if (column != 0 && row != 5) {
			if (row > 5 - column && board[5][5 - (5 - row) - (5 - column)] != null) {
				// System.out.println("Zuiderrand");
				moveList.add(new Point(5, 5 - (5 - row) - (5 - column)));
			}
			if (row < 5 - column && board[row + column][0] != null) {
				// System.out.println("Westerrand");
				moveList.add(new Point(row + column, 0));
			}
			if (row == 5 - column && board[5][0] != null) {
				// System.out.println("ZuidWestDiagonaal");
				moveList.add(new Point(5, 0));
			}
		}
		// Zuidoosten
		if (column != 5 && row != 5) {
			if (5 - row > 5 - column && board[row + (5 - column)][5] != null) {
				// System.out.println("Oosterrand");
				moveList.add(new Point(row + (5 - column), 5));
			}
			if (5 - row < 5 - column && board[5][column + (5 - row)] != null) {
				// System.out.println("Zuiderrand");
				moveList.add(new Point(5, column + (5 - row)));
			}
			if (5 - row == 5 - column && board[5][5] != null) {
				// System.out.println("ZuidOostDiagonaal");
				moveList.add(new Point(5, 5));
			}
		}

		return moveList;
	}
}