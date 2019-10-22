package logic.blacksmithing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import logic.blacksmithing.pieces.BlacksmithingPiece;

public class Blacksmithing {

	private BlacksmithingBoard board;
	private int ROWS;
	private int COLUMNS;

	private boolean endSession;

	public Blacksmithing() {
		board = new BlacksmithingBoard();
		ROWS = board.getRows();
		COLUMNS = board.getColumns();
	}

	// lijst van moves van de laatste point van vorige board
	ArrayList<Point> possibleLastMoveList = new ArrayList<Point>();

	private int amount = 0;

	public ArrayList<Point> bestOrder(BlacksmithingPiece[][] boardArray) {
		// Alle moves indien er geen moves worden meegegeven
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		amount++;
		if (possibleLastMoveList.isEmpty() || amount == 3) {
			amount = 0;
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					possibleMoves.add(new Point(i, j));
				}
			}
		} else {
			possibleMoves = possibleLastMoveList;
		}

		ArrayList<Point> lastPossibleMoves = new ArrayList<Point>();
		// deepcopy lastpossiblemoves zodat we kunnen blijven resetten
		for (int i = 0; i < possibleMoves.size(); i++) {
			lastPossibleMoves.add(possibleMoves.get(i));
		}

		ArrayList<Point> orderList = new ArrayList<Point>();
		int bestScore = 0;
		ArrayList<Point> bestOrderList = new ArrayList<Point>();
		board.updateBoard(boardArray);

		endSession = false;
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				endSession = true;
			}
		}, 10000);

		while (bestScore < 210 && !endSession) {
			// Resetten van values
			orderList.clear();
			possibleMoves.clear();
			for (int i = 0; i < lastPossibleMoves.size(); i++) {
				possibleMoves.add(lastPossibleMoves.get(i));
			}

			// Random move uit de lijst nemen
			while (!possibleMoves.isEmpty()) {
				Random rand = new Random();
				int random = rand.nextInt(possibleMoves.size());
				Point chosen = possibleMoves.get(random);
				possibleMoves = board.getMoves(chosen.x, chosen.y);
				board.makeMove(chosen.x, chosen.y);
				orderList.add(new Point(chosen.x, chosen.y));
			}
			board.updateBoard(boardArray);

			// size dan score
			if (orderList.size() > bestOrderList.size()) {
				//System.out.println("NIEUWE SIZE : " + orderList.size());
				bestOrderList.clear();
				for (int i = 0; i < orderList.size(); i++) {
					bestOrderList.add(orderList.get(i));
				}
				Point lastMove = orderList.get(orderList.size() - 1);
				possibleLastMoveList = board.getMoves(lastMove.x, lastMove.y);
			}

			if (orderList.size() >= bestOrderList.size()) {
				int score = board.getScore(orderList);
				if (score > bestScore) {
					//System.out.println("NIEUWE SCORE : " + score +" MET SIZE "+ orderList.size());
					bestScore = score;
					bestOrderList.clear();
					for (int i = 0; i < orderList.size(); i++) {
						bestOrderList.add(orderList.get(i));
					}
					Point lastMove = orderList.get(orderList.size() - 1);
					possibleLastMoveList = board.getMoves(lastMove.x, lastMove.y);
				}
			}

		}
		System.out.println(bestOrderList.size());
		return bestOrderList;
	}
}
