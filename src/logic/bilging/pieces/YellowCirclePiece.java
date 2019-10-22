package logic.bilging.pieces;

public class YellowCirclePiece extends BilgePiece{
	public YellowCirclePiece() {
		super(BilgePieces.YellowCirclePiece);
	}
	
	public String toString() {
		return "YC";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.YellowCirclePiece.ordinal();
	}
}
