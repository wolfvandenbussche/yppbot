package logic.bilging;

import java.util.ArrayList;
import java.util.Arrays;

import logic.bilging.pieces.BilgePiece;
import logic.bilging.pieces.BilgePiece.BilgePieces;
import logic.bilging.pieces.NullBilgingPiece;

public class BilgingBoard {

	private final int ROWS = 12;
	private final int COLUMNS = 6;
	private final int MIN_CONSECUTIVE = 3;
	private BilgePiece[][] boardArray;

	private int totalScore = 0;
	private String latestComboAndScore = "";
	private int latestScore = 0;
	private ArrayList<Integer> combo;

	public BilgingBoard() {
		boardArray = new BilgePiece[ROWS][COLUMNS];
		fillBoard();
		/*do {
			normalMove(false);
		} while (replace());*/
		totalScore = 0;
		latestComboAndScore = "";
	}

	public int makeMove(int row, int column) {
		if (row > ROWS || column > COLUMNS - 1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		// Check op special pieces
		BilgePiece first = boardArray[row][column];
		BilgePiece second = boardArray[row][column + 1];

		// Crab mag niet verplaatst worden
		if (first.toInt() == BilgePieces.CrabPiece.ordinal() || second.toInt() == BilgePieces.CrabPiece.ordinal()) {
			return 0;
		}

		// Blowfish
		if (first.toInt() == BilgePieces.BlowfishPiece.ordinal() || second.toInt() == BilgePieces.BlowfishPiece.ordinal()) {
			blowfishMove(row, column);
			return 1;
		}

		// JellyFish
		if (first.toInt() == BilgePieces.JellyfishPiece.ordinal() || second.toInt() == BilgePieces.JellyfishPiece.ordinal()) {
			jellyfishMove(row, column, second.toInt());
			return 1;
		}

		boardArray[row][column + 1] = first;
		boardArray[row][column] = second;
		return calculateScore();
	}

	private int calculateScore() {
		//ArrayList<Point> removePieces = new ArrayList<Point>();

		// remove crabs above water level
		/*for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (boardArray[i][j].toInt() == Pieces.CrabPiece.ordinal()) {
					if (i < ROWS - WATERLEVEL) {
						removePieces.add(new Point(i, j));
					}

				}
			}
		}*/

		int matches;
		int piece;
		//int lastJ = -1;
		combo = new ArrayList<Integer>();
		// DELETE HORIZONTAL
		for (int i = 0; i < ROWS; i++) {
			matches = 0;
			piece = -1;
			for (int j = 0; j < COLUMNS; j++) {
				if (boardArray[i][j].toInt() == piece) {
					matches++;
					//lastJ = j;
				}
				if (boardArray[i][j].toInt() != piece || j == COLUMNS - 1) {
					if (matches >= MIN_CONSECUTIVE) {
						/*for (int k = 0; k < matches; k++) {
							removePieces.add(new Point(i, lastJ - k));
						}*/
						combo.add(matches);
					}
					if (boardArray[i][j] != null) {
						piece = boardArray[i][j].toInt();
						matches = 1;

					}
				}
			}
		}

		// DELETE VERTICAL
		//lastJ = -1;
		for (int i = 0; i < COLUMNS; i++) {
			matches = 0;
			piece = -1;
			for (int j = 0; j < ROWS; j++) {
				if (boardArray[j][i].toInt() == piece) {
					matches++;
					//lastJ = j;
				}
				if (boardArray[j][i].toInt() != piece || j == ROWS - 1) {
					if (matches >= MIN_CONSECUTIVE) {
						/*for (int k = 0; k < matches; k++) {
							removePieces.add(new Point(lastJ - k, i));
						}*/
						combo.add(matches);
					}
					if (boardArray[j][i] != null) {
						piece = boardArray[j][i].toInt();
						matches = 1;
					}
				}
			}
		}

		/*for (int i = 0; i < removePieces.size(); i++) {
			Point removePiece = removePieces.get(i);
			boardArray[removePiece.x][removePiece.y] = new NullPiece();
		}*/
/*
		String comboString = "";
		int score = 0;
		for (int i = 0; i < combo.size(); i++) {
			int c = combo.get(i);
			comboString += c + "x";
			// scoring algorithm
			score += c + (c - MIN_CONSECUTIVE);
		}
		score += score * combo.size();
		totalScore += score;
		if (!comboString.equals("")) {
			latestComboAndScore = comboString.substring(0, comboString.length() - 1) + " | " + score;
			latestScore = score;
		} else {
			latestComboAndScore = "";
			latestScore = 0;
		}
		return (removePieces.size() > 0);*/
		
		int score = 0;
		for (int i = 0; i < combo.size(); i++) {
			int c = combo.get(i);
			score += c + (c - MIN_CONSECUTIVE);
		}
		score += score * combo.size();
		return score;
	}

	public int getScore() {
		return totalScore;
	}

	public String getlatestComboAndScore() {
		return latestComboAndScore;
	}

	public int getLatestScore() {
		return latestScore;
	}

	private void blowfishMove(int row, int column) {
		boardArray[row][column] = new NullBilgingPiece();

		if (row - 1 > 0) {
			boardArray[row - 1][column] = new NullBilgingPiece();
		}
		if (row + 1 < ROWS) {
			boardArray[row + 1][column] = new NullBilgingPiece();
		}

		if (column - 1 > 0) {
			boardArray[row][column - 1] = new NullBilgingPiece();
		}
		if (column + 1 < COLUMNS) {
			boardArray[row][column + 1] = new NullBilgingPiece();
		}

		if (row - 1 > 0 && column - 1 > 0) {
			boardArray[row - 1][column - 1] = new NullBilgingPiece();
		}

		if (row + 1 < ROWS && column + 1 < COLUMNS) {
			boardArray[row + 1][column + 1] = new NullBilgingPiece();
		}

		if (row - 1 > 0 && column + 1 < COLUMNS) {
			boardArray[row - 1][column + 1] = new NullBilgingPiece();
		}

		if (row + 1 < ROWS && column - 1 > 0) {
			boardArray[row + 1][column - 1] = new NullBilgingPiece();
		}

	}

	private void jellyfishMove(int row, int column, int replacePiece) {
		boardArray[row][column] = new NullBilgingPiece();
		// remove crabs above water level
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (boardArray[i][j].toInt() == replacePiece) {
					boardArray[i][j] = new NullBilgingPiece();
				}
			}
		}
	}

	public void replace() {
		// NULL's naar beneden plaatsen
		for (int i = 0; i < ROWS - 1; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (boardArray[i][j].toInt() == BilgePieces.NullPiece.ordinal()) {
					int jump = 0;
					for (int k = i; k < ROWS - 1; k++) {
						if (boardArray[k][j].toInt() == BilgePieces.NullPiece.ordinal()) {
							jump++;
						} else {
							break;
						}
					}
					BilgePiece temp = boardArray[i][j];
					boardArray[i][j] = boardArray[i + jump][j];
					boardArray[i + jump][j] = temp;
				}

			}
		}

		// NULLS omzetten in random pieces
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (boardArray[i][j].toInt() == BilgePieces.NullPiece.ordinal()) {
					boardArray[i][j] = BilgePiece.getRandomPiece();
				}
			}
		}
	}

	private void fillBoard() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				boardArray[i][j] = BilgePiece.getRandomPiece();
			}
		}
	}

	public BilgePiece[][] getBoardArray() {
		return boardArray;
	}

	public int getRows() {
		return ROWS;
	}

	public int getColumns() {
		return COLUMNS;
	}

	public void updateBoard(BilgePiece[][] boardArray) {
		// deep copy idpv reference
		final BilgePiece[][] deepcopy = new BilgePiece[boardArray.length][];
		for (int i = 0; i < boardArray.length; i++) {
			deepcopy[i] = Arrays.copyOf(boardArray[i], boardArray[i].length);
		}
		this.boardArray = deepcopy;
	}

	public void showBoard() {
		System.out.println("--BILGE--");
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				System.out.print(boardArray[i][j] + " ");
			}
			System.out.println("");
		}
	}

}
