package logic.bilging.pieces;

public class NullBilgingPiece extends BilgePiece{

	public NullBilgingPiece() {
		super();
	}
	
	@Override
	public String toString() {
		return "NL";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.NullPiece.ordinal();
	}

}
