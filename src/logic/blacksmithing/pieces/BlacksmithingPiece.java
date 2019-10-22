package logic.blacksmithing.pieces;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public abstract class BlacksmithingPiece {

	private BufferedImage image;
	public static ArrayList<BlacksmithingPiece> pieces = populatePieces();
	
	protected BlacksmithingPiece() {
		try {
			this.image =ImageIO.read(this.getClass().getResource("/rsc/blacksmithing/generatedtiles/"+this.getClass().getSimpleName()+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract String toString();
	public abstract ArrayList<Point> getMoves(int row, int column, BlacksmithingPiece[][] board);
	
	public BufferedImage getBufferedImage() {
		return image;
	}
	
	private static ArrayList<BlacksmithingPiece> populatePieces()
    {
        ArrayList<BlacksmithingPiece> pieces = new ArrayList<>();
        pieces.add(new OnePiece());
        pieces.add(new TwoPiece());
        pieces.add(new ThreePiece());
        pieces.add(new FourPiece());
        pieces.add(new BarracksPiece());
        pieces.add(new TowerPiece());
        pieces.add(new HorsePiece());
        pieces.add(new QueenPiece());
        pieces.add(new RumPiece());
        return pieces;
    }
}
