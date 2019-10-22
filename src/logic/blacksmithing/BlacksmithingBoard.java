package logic.blacksmithing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logic.blacksmithing.pieces.*;
public class BlacksmithingBoard {

	private final int ROWS = 6;
	private final int COLUMNS = 6;
	private BlacksmithingPiece[][] boardArray;

	public BlacksmithingBoard() {

	}

	public void updateBoard(BlacksmithingPiece[][] boardArray) {
		//deepcopy
		this.boardArray = new BlacksmithingPiece[ROWS][COLUMNS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				this.boardArray[i][j] = boardArray[i][j];
			}
		}
	}

	public ArrayList<Point> getMoves(int row, int column) {
		return boardArray[row][column].getMoves(row, column, boardArray);
	}
	
	public void makeMove(int row, int column) {
		boardArray[row][column] = null;
	}

	public int getScore(ArrayList<Point> orderList) {
		ArrayList<Point> list = orderList;
		int score = 0;
		// Consecutive hits berekenen
		// Score toevoegen, per consecutive 2 punten
		int consecutive = 0;
		BlacksmithingPiece lastPiece = boardArray[list.get(0).x][list.get(0).y];
		for (int i = 1; i < list.size(); i++) {
			BlacksmithingPiece piece = boardArray[list.get(i).x][list.get(i).y];
			//consecutive
			if(lastPiece.toString() == piece.toString()) {
				consecutive++;
			} else {
				if(consecutive>1) {
					score+= consecutive*2;
				}
				consecutive = 0;
			}
			
			lastPiece = piece;
		}
		
		//patterns
		
		//Orderlist omzetten in de pieces
		ArrayList<BlacksmithingPiece> pieceOrder = new ArrayList<BlacksmithingPiece>();
		for (int i = 0; i < list.size(); i++) {
			pieceOrder.add(boardArray[list.get(i).x][list.get(0).y]);
		}
		
		String pieceOrderString = Arrays.deepToString(pieceOrder.toArray());
			
		//De patterns apart
		Pattern pattern = Pattern.compile("1, 2, 3, 4|4, 3, 2, 1|Q, B, T, H|H, T, B, Q");
        Matcher matcher = pattern.matcher(pieceOrderString);

        int patternCount = 0;
        while (matcher.find()) {
        	patternCount++;
        }
        score += (10*patternCount);
        
        //Alternating patterns
        pattern = Pattern.compile("1, 2, 3, 4, Q, B, T, H|1, 2, 3, 4, H, T, B, Q|4, 3, 2, 1, Q, B, T, H|4, 3, 2, 1, H, T, B, Q|Q, B, T, H, 1, 2, 3, 4|Q, B, T, H, 4, 3, 2, 1|H, T, B, Q, 1, 2, 3, 4|H, T, B, Q, 4, 3, 2, 1");
        matcher = pattern.matcher(pieceOrderString);

        int alternatingPattern = 0;
        while (matcher.find()) {
        	alternatingPattern++;
        }	
        score += (100*alternatingPattern);
	
		// Extra score geven als de laatste piece (rum piece) op een corner ligt
		Point point = list.get(list.size() - 1);
		if ( 	(point.x == 0 && point.y == 0) || 
				(point.x == 0 && point.y == 5) ||
				(point.x == 5 && point.y == 0) ||
				(point.x == 5 && point.y == 5))
				{
			score += 100;
		}

		return score;
	}

	public int getRows() {
		return ROWS;
	}

	public int getColumns() {
		return COLUMNS;
	}
}
