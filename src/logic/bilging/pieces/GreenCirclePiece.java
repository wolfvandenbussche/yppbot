package logic.bilging.pieces;

public class GreenCirclePiece extends BilgePiece{
	public GreenCirclePiece() {
		super(BilgePieces.GreenCirclePiece);
	}
	
	@Override
	public String toString() {
		return "GC";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.GreenCirclePiece.ordinal();
	}
}
