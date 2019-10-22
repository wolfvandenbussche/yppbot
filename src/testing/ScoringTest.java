package testing;

import static org.junit.Assert.assertEquals;
import java.awt.Point;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import logic.blacksmithing.BlacksmithingBoard;
import logic.blacksmithing.pieces.*;

public class ScoringTest {

	
	BlacksmithingBoard blacksmithingBoard;

	public ScoringTest() {
		blacksmithingBoard = new BlacksmithingBoard();
	}


	@Test
	public void testOneTwoThreeFour() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { 
			{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
			{ new TwoPiece(), new TwoPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
			{ new ThreePiece(), new ThreePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
			{ new FourPiece(), new FourPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
			{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
			{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
			blacksmithingBoard.updateBoard(board);
		
		ArrayList<Point> orderList = new ArrayList<Point>();
		orderList.add(new Point(0,0));
		orderList.add(new Point(1,0));
		orderList.add(new Point(2,0));
		orderList.add(new Point(3,0));
		orderList.add(new Point(0,1));
		orderList.add(new Point(1,1));
		orderList.add(new Point(2,1));
		orderList.add(new Point(3,1));
		
		int score = blacksmithingBoard.getScore(orderList);
		assertEquals(score,20);
	}
	
	@Test
	public void testOneTwoThreeFourFalse() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { 
			{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
			{ new TwoPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
			{ new ThreePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
			{ new FourPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
			{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
			{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
			blacksmithingBoard.updateBoard(board);
		
		ArrayList<Point> orderList = new ArrayList<Point>();
		orderList.add(new Point(0,0));
		orderList.add(new Point(2,0));
		orderList.add(new Point(3,0));
		orderList.add(new Point(3,0));
		
		int score = blacksmithingBoard.getScore(orderList);
		assertEquals(score,0);
	}

}
