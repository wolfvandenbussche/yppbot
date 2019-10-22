package logic.bilging.pieces;

public class BlueSquarePiece extends BilgePiece {
	public BlueSquarePiece() {
		super(BilgePieces.BlueSquarePiece);
	}
	@Override
	public String toString() {
		return "BS";
	}
	

	@Override
	public int toInt() {
		return BilgePieces.BlueSquarePiece.ordinal();
	}
}
