package logic.bilging.pieces;

public class CyanSquarePiece extends BilgePiece{
	public CyanSquarePiece() {
		super(BilgePieces.CyanSquarePiece);
	}
	
	@Override
	public String toString() {
		return "CS";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.CyanSquarePiece.ordinal();
	}
}
