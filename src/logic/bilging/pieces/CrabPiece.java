package logic.bilging.pieces;

public class CrabPiece extends BilgePiece {
	public CrabPiece() {
		super(BilgePieces.CrabPiece);
	}
	
	@Override
	public String toString() {
		return "CR";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.CrabPiece.ordinal();
	}
	
	
}
