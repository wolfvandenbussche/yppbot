package logic.bilging.pieces;

public class WhiteSquarePiece extends BilgePiece{
	public WhiteSquarePiece() {
		super(BilgePieces.WhiteSquarePiece);
	}
	
	@Override
	public String toString() {
		return "WS";
	}
	
	@Override
	public int toInt() {
		return BilgePieces.WhiteSquarePiece.ordinal();
	}
}
