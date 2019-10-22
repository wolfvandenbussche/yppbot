package logic.bilging.pieces;

public class PentagonPiece extends BilgePiece{

	public PentagonPiece() {
		super(BilgePieces.PentagonPiece);
	}

	@Override
	public String toString() {
		return "PG";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.PentagonPiece.ordinal();
	}
}
