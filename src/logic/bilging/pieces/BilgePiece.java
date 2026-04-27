package logic.bilging.pieces;

import java.util.Random;

public abstract class BilgePiece {

	private BilgePieces piece;

	public BilgePiece(BilgePieces piece) {
		this.piece = piece;
	}

	public BilgePiece() {
	}

	public abstract String toString();

	public abstract int toInt();

	public final static int RARE_CHANCE = 50;
	
	public static BilgePiece getRandomPiece() {
		Random random = new Random();
		int rare = random.nextInt(RARE_CHANCE);
		if(rare != 0) {
			int length = BilgePieces.values().length-(EXTRA_PIECES+USELESS_PIECES);
			int r = random.nextInt(length);
			BilgePiece randomPiece = null;
			switch (r) {
			case 0:
				randomPiece = new BlueCirclePiece();
				break;
			case 1:
				randomPiece = new YellowCirclePiece();
				break;
			case 2:
				randomPiece = new GreenCirclePiece();
				break;
			case 3:
				randomPiece = new WhiteSquarePiece();
				break;
			case 4:
				randomPiece = new BlueSquarePiece();
				break;
			case 5:
				randomPiece = new CyanSquarePiece();
				break;
			case 6:
				randomPiece = new PentagonPiece();
				break;
			}
			return randomPiece;
		} else {
			int length = (EXTRA_PIECES);
			int r = random.nextInt(length);
			BilgePiece randomPiece = null;
			switch (r) {
			case 0:
				randomPiece = new CrabPiece();
				break;
			case 1:
				randomPiece = new BlowfishPiece();
				break;
			case 2:
				randomPiece = new JellyfishPiece();
				break;
			}
			return randomPiece;
			
		}
	}

	public enum BilgePieces {
		BlueCirclePiece,
		YellowCirclePiece,
		GreenCirclePiece,
		WhiteSquarePiece,
		BlueSquarePiece,
		CyanSquarePiece,
		PentagonPiece,
		
		BlowfishPiece,
		CrabPiece,
		JellyfishPiece,
		
		NullPiece
	}
	public final static int EXTRA_PIECES = 3;
	public final static int USELESS_PIECES = 1;
}
