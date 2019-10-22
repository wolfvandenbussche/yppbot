package logic.bilging;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import logic.bilging.pieces.*;
import utility.PuzzlePirates;

public class BilgingReader {

	public static int X = 89;
	public static int Y = 46;

	private static int WIDTH = 270;
	private static int HEIGHT = 540;

	private static int ROWS = 12;
	private static int COLUMNS = 6;

	private final static int BILGETITLE = -14641726;

	private final static int BLUECIRCLE = -15087373;
	private final static int YELLOWCIRCLE = -7806267;
	private final static int GREENCIRCLE = -12875882;
	private final static int WHITESQUARE = -11026955;
	private final static int BLUESQUARE = -15103798;
	private final static int CYANSQUARE = -16458548;
	private final static int PENTAGON = -2;
	private final static int BLOWFISH = -331196;
	private final static int JELLYFISH = -4;

	private final static int WBLUECIRCLE = -16089662;
	private final static int WYELLOWCIRCLE = -13203536;
	private final static int WGREENCIRCLE = -15178851;
	private final static int WWHITESQUARE = -14452285;
	private final static int WBLUESQUARE = -16096334;
	private final static int WCYANSQUARE = -16611917;
	private final static int WPENTAGON = -10;
	private final static int WBLOWFISH = -10187140;
	private final static int WCRAB = -15054981;
	private final static int WJELLYFISH = -13;

	public static BilgePiece[][] readScreen() {
		PuzzlePirates.isRunning();
		PuzzlePirates.toTop();
		BilgePiece[][] boardArray = new BilgePiece[ROWS][COLUMNS];

		Rectangle bounds = getPuzzleBounds();
		if (bounds == null) {
			return null;
		}
		BufferedImage boardImage = PuzzlePirates.getImage(bounds);

		boolean unidentifiedPieces = false;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				// Middelste pixel nemen
				int rgb = boardImage.getRGB((j * 45) + 22, (i * 45) + 22);
				BilgePiece piece = null;

				switch (rgb) {
				case BLUECIRCLE:
					piece = new BlueCirclePiece();
					break;
				case WBLUECIRCLE:
					piece = new BlueCirclePiece();
					break;
				case YELLOWCIRCLE:
					piece = new YellowCirclePiece();
					break;
				case WYELLOWCIRCLE:
					piece = new YellowCirclePiece();
					break;
				case GREENCIRCLE:
					piece = new GreenCirclePiece();
					break;
				case WGREENCIRCLE:
					piece = new GreenCirclePiece();
					break;
				case WHITESQUARE:
					piece = new WhiteSquarePiece();
					break;
				case WWHITESQUARE:
					piece = new WhiteSquarePiece();
					break;
				case BLUESQUARE:
					piece = new BlueSquarePiece();
					break;
				case WBLUESQUARE:
					piece = new BlueSquarePiece();
					break;
				case CYANSQUARE:
					piece = new CyanSquarePiece();
					break;
				case WCYANSQUARE:
					piece = new CyanSquarePiece();
					break;
				case PENTAGON:
					piece = new PentagonPiece();
					break;
				case WPENTAGON:
					piece = new PentagonPiece();
					break;
				case BLOWFISH:
					piece = new BlowfishPiece();
					break;
				case WBLOWFISH:
					piece = new BlowfishPiece();
					break;
				case WCRAB:
					piece = new CrabPiece();
					break;
				case JELLYFISH:
					piece = new JellyfishPiece();
					break;
				case WJELLYFISH:
					piece = new JellyfishPiece();
					break;
				}

				if (piece == null) {
					//System.out.println("Unidentified color : " + rgb + " " + i + " " + j);
					piece = new NullBilgingPiece();
					unidentifiedPieces = true;
				}
				boardArray[i][j] = piece;

			}
		}
		if(unidentifiedPieces) {
			return null;
		}
		return boardArray;
	}

	public static Rectangle getPuzzleBounds() {
		Rectangle rectangle = PuzzlePirates.getPuzzlePiratesBounds();
		BufferedImage image = PuzzlePirates.getImage(rectangle);
		//PuzzlePirates.takeScreenshot(image, "bilge");
		if (image.getRGB(192, 10) != BILGETITLE) {
			return null;
		}

		Rectangle newBounds = new Rectangle(rectangle.x + X, rectangle.y + Y, WIDTH, HEIGHT);

		return newBounds;
	}
	
	
}
