package logic.bilging.pieces;

public class BlowfishPiece extends BilgePiece{
	public BlowfishPiece() {
		super(BilgePieces.BlowfishPiece);
	}
	
	@Override
	public String toString() {
		return "BF";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.BlowfishPiece.ordinal();
	}
}
