package logic.bilging;
import java.awt.Point;
import java.util.ArrayList;

import logic.bilging.pieces.BilgePiece;

public class Bilging {

	private BilgingBoard board;
	private int ROWS;
	private int COLUMNS;

	public Bilging() {
		board = new BilgingBoard();
		ROWS = board.getRows();
		COLUMNS = board.getColumns();
	}

	public Point bestOneMove(BilgePiece[][] boardArray) {
		Point point = null;
		int maxScore = 0;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS - 1; j++) {
				board.updateBoard(boardArray);
				board.makeMove(i, j);
				int score = board.getLatestScore();
				if (score > maxScore) {
					maxScore = score;
					point = new Point(i, j);
				}
			}
		}
		return point;
	}

	public int boardNumber = 1;
	public ArrayList<Point> xMoves(BilgePiece[][] boardArray) {
		ArrayList<Point> bestMoves = new ArrayList<Point>();

		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS - 1; j++) {
				board.updateBoard(boardArray);
				int score = board.makeMove(i, j);
				BilgePiece[][] newBoardArray = board.getBoardArray();
				System.out.println("board " + boardNumber);
				System.out.println("score " + score);
				boardNumber++;
				for (int k = 0; k < ROWS; k++) {
					for (int l = 0; l < COLUMNS; l++) {
						System.out.print(newBoardArray[k][l]+ " ");
					}
					System.out.println("");
				}
			}
		}
		return bestMoves;
	}
	/*public ArrayList<Point> bestTwoMove(Piece[][] boardArray) {
		ArrayList<Point> bestMoves = new ArrayList<Point>();
		List<Point> previousMoves = new ArrayList<Point>();
		int maxScore = 0;
		int score;
		boolean moreMoves;
		Piece[][] array;
		Piece[][] array2;

		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS - 1; j++) {
				if (previousMoves.size() >= 0) {
					previousMoves = previousMoves.subList(0, 0);
				}
				
				board.updateBoard(boardArray);
				moreMoves = board.makeMove(i, j);
				array = board.getBoardArray();
				
				previousMoves.add(new Point(i, j));

				if(moreMoves) {
					score = board.getLatestScore()/1;
					if (score > maxScore) {
						maxScore = score;
						bestMoves.clear();
						for (int h = 0; h < previousMoves.size(); h++) {
							bestMoves.add(previousMoves.get(h));
						}
						continue;
					}
				}
				
				

				for (int k = 0; k < ROWS; k++) {
					for (int l = 0; l < COLUMNS - 1; l++) {
						if (previousMoves.size() >= 1) {
							previousMoves = previousMoves.subList(0, 1);
						}
			


						board.updateBoard(array);
						moreMoves = board.makeMove(k, l);
						board.getBoardArray();
						
						previousMoves.add(new Point(k, l));

						if(moreMoves) {
							score = board.getLatestScore()/2;
							if (score > maxScore) {
								maxScore = score;
								bestMoves.clear();
								for (int h = 0; h < previousMoves.size(); h++) {
									bestMoves.add(previousMoves.get(h));
								}
								continue;
							}
						}
						
						


						
					
						
					}
				}
				

			}
		}

		return bestMoves;
	}
	*/

	public ArrayList<Point> recursive(BilgePiece[][] boardArray, int numThreads, int depth) {
		ArrayList<Point> bestMoves = new ArrayList<Point>();
		
		depth--;
		if(depth == 0) {
			return bestMoves;
		} else {
			return recursive(boardArray, depth,0);
		}
	
	}
	
	  /*public List<Point> searchDepth(Piece[][] boardArray, int depth)
	    {
	        List<Point> bestSwap = null;

	        for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS - 1; j++) {

	            board.updateBoard(boardArray);
	            boolean moreMoves = board.makeMove(i, j);
	            int score = 0;
	            int initialScore = board.getScore();
	            
	            
	            if(depth > 1) {
	            	searchDepth(boardArray,depth);
	            }
	        }
	        }

	        return bestSwap;
	    }*/

}
