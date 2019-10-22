package testing;

import org.junit.jupiter.api.Test;

import logic.blacksmithing.BlacksmithingBoard;
import logic.blacksmithing.pieces.*;

import java.awt.Point;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 *
 * @author wolfv
 */

public class BlacksmithingPieceTest {

	BlacksmithingBoard blacksmithingBoard;

	public BlacksmithingPieceTest() {
		blacksmithingBoard = new BlacksmithingBoard();
	}

	@Test
	public void testTileOneNorthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 1);
		Point expResult1 = new Point(1, 0);
		Point expResult2 = new Point(1, 1);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileOneNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 4);
		Point expResult1 = new Point(1, 4);
		Point expResult2 = new Point(1, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileOneSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(4, 5);
		Point expResult1 = new Point(4, 4);
		Point expResult2 = new Point(5, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileOneSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(4, 0);
		Point expResult1 = new Point(4, 1);
		Point expResult2 = new Point(5, 1);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileOneMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(1, 1);
		Point expResult1 = new Point(1, 2);
		Point expResult2 = new Point(1, 3);
		Point expResult3 = new Point(2, 1);
		Point expResult4 = new Point(2, 3);
		Point expResult5 = new Point(3, 1);
		Point expResult6 = new Point(3, 2);
		Point expResult7 = new Point(3, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.size() == 8);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
		assertTrue("ERROR : result 5", result.contains(expResult5));
		assertTrue("ERROR : result 6", result.contains(expResult6));
		assertTrue("ERROR : result 7", result.contains(expResult7));
	}

	@Test
	public void testTileTwoNorthWest() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new TwoPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 0);
		Point expResult1 = new Point(0, 2);
		Point expResult2 = new Point(2, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileTwoNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new TwoPiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 5);
		Point expResult1 = new Point(0, 3);
		Point expResult2 = new Point(2, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileTwoSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new TwoPiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(3, 5);
		Point expResult1 = new Point(5, 3);
		Point expResult2 = new Point(3, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileTwoSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new TwoPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(3, 0);
		Point expResult1 = new Point(5, 2);
		Point expResult2 = new Point(3, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileTwoMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new TwoPiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(0, 2);
		Point expResult2 = new Point(0, 4);
		Point expResult3 = new Point(2, 0);
		Point expResult4 = new Point(2, 4);
		Point expResult5 = new Point(4, 0);
		Point expResult6 = new Point(4, 2);
		Point expResult7 = new Point(4, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.size() == 8);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
		assertTrue("ERROR : result 5", result.contains(expResult5));
		assertTrue("ERROR : result 6", result.contains(expResult6));
		assertTrue("ERROR : result 7", result.contains(expResult7));
	}

	@Test
	public void testTileThreeNorthWest() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new ThreePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(3, 0);
		Point expResult1 = new Point(0, 3);
		Point expResult2 = new Point(3, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileThreeNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new ThreePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(3, 5);
		Point expResult1 = new Point(0, 2);
		Point expResult2 = new Point(3, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileThreeSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new ThreePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 5);
		Point expResult1 = new Point(5, 2);
		Point expResult2 = new Point(2, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileThreeSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new ThreePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 0);
		Point expResult1 = new Point(5, 3);
		Point expResult2 = new Point(2, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileThreeMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new ThreePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(5, 2);
		Point expResult1 = new Point(2, 5);
		Point expResult2 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileThreeMiddle2() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new ThreePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 0);
		Point expResult1 = new Point(5, 0);
		Point expResult2 = new Point(5, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 3);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileFourNorthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new FourPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(4, 0);
		Point expResult1 = new Point(0, 4);
		Point expResult2 = new Point(4, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileFourNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new FourPiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(4, 5);
		Point expResult1 = new Point(0, 1);
		Point expResult2 = new Point(4, 1);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileFourSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new FourPiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(1, 5);
		Point expResult1 = new Point(5, 1);
		Point expResult2 = new Point(1, 1);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileFourSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new FourPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(1, 0);
		Point expResult1 = new Point(5, 4);
		Point expResult2 = new Point(1, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}

	@Test
	public void testTileFourMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new FourPiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.isEmpty());
	}

	@Test
	public void testTileBarracksNorthWest() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new BarracksPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(5, 0);
		Point expResult1 = new Point(0, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileBarracksNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new BarracksPiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileBarracksSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new BarracksPiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 5);
		Point expResult1 = new Point(5, 0);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileBarracksSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new BarracksPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileBarracksMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new BarracksPiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 2);
		Point expResult1 = new Point(2, 0);
		Point expResult2 = new Point(2, 5);
		Point expResult3 = new Point(5, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.size() == 4);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
	}

	@Test
	public void testTileTowerNorthWest() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new TowerPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 1);
		assertTrue("ERROR : result 0", result.contains(expResult0));
	}

	@Test
	public void testTileTowerNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new TowerPiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(5, 0);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 1);
		assertTrue("ERROR : result 0", result.contains(expResult0));
	}

	@Test
	public void testTileTowerSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new TowerPiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 1);
		assertTrue("ERROR : result 0", result.contains(expResult0));
	}

	@Test
	public void testTileTowerSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new TowerPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 1);
		assertTrue("ERROR : result 0", result.contains(expResult0));
	}

	@Test
	public void testTileTowerMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new TowerPiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(0, 4);
		Point expResult2 = new Point(4, 0);
		Point expResult3 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.size() == 4);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
	}

	@Test
	public void testTileHorseNorthWest() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new HorsePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 1);
		Point expResult1 = new Point(1, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileHorseNorthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new HorsePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(1, 3);
		Point expResult1 = new Point(2, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileHorseSouthEast() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new HorsePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(3, 4);
		Point expResult1 = new Point(4, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileHorseSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new HorsePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(3, 1);
		Point expResult1 = new Point(4, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

	@Test
	public void testTileHorseMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new HorsePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, { new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 1);
		Point expResult1 = new Point(1, 0);
		Point expResult2 = new Point(3, 0);
		Point expResult3 = new Point(4, 1);
		Point expResult4 = new Point(3, 4);
		Point expResult5 = new Point(4, 3);
		Point expResult6 = new Point(3, 0);
		Point expResult7 = new Point(4, 1);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 2);
		assertTrue("ERROR : result size", result.size() == 8);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
		assertTrue("ERROR : result 5", result.contains(expResult5));
		assertTrue("ERROR : result 6", result.contains(expResult6));
		assertTrue("ERROR : result 7", result.contains(expResult7));
	}

	@Test
	public void testTileQueenNorthWest() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new QueenPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(5, 0);
		Point expResult1 = new Point(0, 5);
		Point expResult2 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}
	
	@Test
	public void testTileQueenNorthWestEdge1() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new QueenPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(5, 0);
		Point expResult2 = new Point(1, 5);
		Point expResult3 = new Point(0, 1);
		Point expResult4 = new Point(5, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(1, 0);
		assertTrue("ERROR : result size", result.size() == 5);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
	}
	
	@Test
	public void testTileQueenNorthWestEdge2() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new QueenPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(0, 5);
		Point expResult2 = new Point(5, 1);
		Point expResult3 = new Point(4, 5);
		Point expResult4 = new Point(1, 0);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 1);
		assertTrue("ERROR : result size", result.size() == 5);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
	}
	
	@Test
	public void testTileQueenNorthEast() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new QueenPiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(5, 0);
		Point expResult2 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}
	
	@Test
	public void testTileQueenSouthEast() {

		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new QueenPiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(0, 5);
		Point expResult2 = new Point(5, 0);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 5);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}
	
	@Test
	public void testTileQueenSouthWest() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new QueenPiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(0, 0);
		Point expResult1 = new Point(0, 5);
		Point expResult2 = new Point(5, 5);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 0);
		assertTrue("ERROR : result size", result.size() == 3);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
	}
	
	@Test
	public void testTileQueenMiddle() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new QueenPiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(1, 0);
		Point expResult1 = new Point(3, 0);
		Point expResult2 = new Point(5, 0);
		Point expResult3 = new Point(0, 2);
		Point expResult4 = new Point(0, 5);
		Point expResult5 = new Point(5, 2);
		Point expResult6 = new Point(5, 4);
		Point expResult7 = new Point(5, 4);
		ArrayList<Point> result = blacksmithingBoard.getMoves(3, 2);
		assertTrue("ERROR : result size", result.size() == 8);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
		assertTrue("ERROR : result 5", result.contains(expResult5));
		assertTrue("ERROR : result 6", result.contains(expResult6));
		assertTrue("ERROR : result 7", result.contains(expResult7));
	}
	
	@Test
	public void testTileQueenRight() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new QueenPiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(2, 0);
		Point expResult1 = new Point(0, 5);
		Point expResult2 = new Point(5, 5);
		Point expResult3 = new Point(0, 3);
		Point expResult4 = new Point(5, 2);
		ArrayList<Point> result = blacksmithingBoard.getMoves(2, 5);
		assertTrue("ERROR : result size", result.size() == 5);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
		assertTrue("ERROR : result 2", result.contains(expResult2));
		assertTrue("ERROR : result 3", result.contains(expResult3));
		assertTrue("ERROR : result 4", result.contains(expResult4));
	}
	
	@Test
	public void testTileQueenBottomNull() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] {
				{ new OnePiece(), new OnePiece(), new OnePiece(), null, new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() },
				{ null, new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), null },
				{ new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece(), new OnePiece() }, 
				{ null, new OnePiece(), new OnePiece(), new QueenPiece(), new OnePiece(), null } };
		blacksmithingBoard.updateBoard(board);
		ArrayList<Point> result = blacksmithingBoard.getMoves(5, 3);
		assertTrue("ERROR : result size", result.size() == 0);
	}
	
	


	@Test
	public void testTileRum() {
		BlacksmithingPiece[][] board = new BlacksmithingPiece[][] { 
				{ new RumPiece(), null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, null },
				{ null, null, null, null, null, null }, { null, null, null, null, null, new OnePiece() },
				{ null, null, null, new TwoPiece(), null, null } };
		blacksmithingBoard.updateBoard(board);
		Point expResult0 = new Point(4, 5);
		Point expResult1 = new Point(5, 3);
		ArrayList<Point> result = blacksmithingBoard.getMoves(0, 0);
		assertTrue("ERROR : result size", result.size() == 2);
		assertTrue("ERROR : result 0", result.contains(expResult0));
		assertTrue("ERROR : result 1", result.contains(expResult1));
	}

}
