package logic.bilging.pieces;


public class BlueCirclePiece extends BilgePiece {
	public BlueCirclePiece() {
		super(BilgePieces.BlueCirclePiece);
	}

	@Override
	public String toString() {
		return "BC";
	}

	@Override
	public int toInt() {
		return BilgePieces.BlueCirclePiece.ordinal();
	}
}
