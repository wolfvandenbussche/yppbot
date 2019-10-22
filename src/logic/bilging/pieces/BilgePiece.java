package logic.bilging.pieces;

import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import ui.scenes.ResourceLoader;

public abstract class BilgePiece {

	private ImageView imageView;
	private ResourceLoader resourceLoader;
	private BilgePieces piece;

	public BilgePiece(BilgePieces piece) {
		this.piece = piece;
		
	}
	
	public BilgePiece() {
		imageView = new ImageView();
	}
	
	public abstract String toString();
	
	public abstract int toInt();

	public ImageView getImageView() {
		resourceLoader = new ResourceLoader();
		Image image = new Image(resourceLoader.getResource("/bilging/rsc/" + piece.toString().toLowerCase() + ".png"));
		imageView = new ImageView();
		imageView.setImage(image);
		FadeTransition ft = new FadeTransition(Duration.millis(1000), imageView);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		return imageView;
	} 

	public final static int RARE_CHANCE = 50;
	
	public static BilgePiece getRandomPiece() {
		Random random = new Random();
		int rare = random.nextInt(RARE_CHANCE);
		if(rare != 0) {
			int length = BilgePieces.values().length-(EXTRA_PIECES+USELESS_PIECES);
			int r = random.nextInt(length);
			BilgePiece randomPiece = null;
			switch (r) {
			case 0:
				randomPiece = new BlueCirclePiece();
				break;
			case 1:
				randomPiece = new YellowCirclePiece();
				break;
			case 2:
				randomPiece = new GreenCirclePiece();
				break;
			case 3:
				randomPiece = new WhiteSquarePiece();
				break;
			case 4:
				randomPiece = new BlueSquarePiece();
				break;
			case 5:
				randomPiece = new CyanSquarePiece();
				break;
			case 6:
				randomPiece = new PentagonPiece();
				break;
			}
			return randomPiece;
		} else {
			int length = (EXTRA_PIECES);
			int r = random.nextInt(length);
			BilgePiece randomPiece = null;
			switch (r) {
			case 0:
				randomPiece = new CrabPiece();
				break;
			case 1:
				randomPiece = new BlowfishPiece();
				break;
			case 2:
				randomPiece = new JellyfishPiece();
				break;
			}
			return randomPiece;
			
		}
	}

	public enum BilgePieces {
		BlueCirclePiece,
		YellowCirclePiece,
		GreenCirclePiece,
		WhiteSquarePiece,
		BlueSquarePiece,
		CyanSquarePiece,
		PentagonPiece,
		
		BlowfishPiece,
		CrabPiece,
		JellyfishPiece,
		
		NullPiece
	}
	public final static int EXTRA_PIECES = 3;
	public final static int USELESS_PIECES = 1;
}
