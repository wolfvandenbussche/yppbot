package logic.bilging.pieces;

public class JellyfishPiece extends BilgePiece{
	public JellyfishPiece() {
		super(BilgePieces.JellyfishPiece);
	}
	
	@Override
	public String toString() {
		return "JF";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.JellyfishPiece.ordinal();
	}
}
