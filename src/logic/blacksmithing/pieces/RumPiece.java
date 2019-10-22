package logic.blacksmithing.pieces;

import java.awt.Point;
import java.util.ArrayList;

public class RumPiece extends BlacksmithingPiece {
	public RumPiece() {
		super();
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board) {
		ArrayList<Point> moveList = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (!(i == row && column == j)) {
					if (board[i][j] != null) {
						moveList.add(new Point(i, j));
					}
				}

			}
		}

		return moveList;
	}
}