package logic.blacksmithing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.blacksmithing.pieces.BlacksmithingPiece;
import utility.PuzzlePirates;

public class BlacksmithingReader {

	public static int X = 52;
	public static int Y = 67;

	private static int WIDTH = 360;
	private static int HEIGHT = 360;

	private static int ROWS = 6;
	private static int COLUMNS = 6;
	
	private static int BLACK = -16777216;

	private final static int BLACKSMITHINGTITLE = -15855242;

	
	public static BlacksmithingPiece[][] readScreen() {
		PuzzlePirates.isRunning();
		PuzzlePirates.toTop();
		BlacksmithingPiece[][] boardArray = new BlacksmithingPiece[ROWS][COLUMNS];

		Rectangle bounds = getPuzzleBounds();
		if (bounds == null) {
			return null;
		}
		BufferedImage boardImage = PuzzlePirates.getImage(bounds);
		
		//De green value gelijk zetten op 10 om zo een donkere binary image te hebben op het einde
        for (int i = 0; i < boardImage.getHeight(); i++) {
            for (int j = 0; j < boardImage.getWidth(); j++) {;
                int argb = boardImage.getRGB(j, i);
                int r = (argb) & 0xFF;
                //int g = (argb >> 8) & 0xFF;
                int b = (argb >> 16) & 0xFF;
                Color color = new Color(r, 10, b);
                boardImage.setRGB(j, i, color.getRGB());

            }
        }
        
        BufferedImage grayImage = new BufferedImage(boardImage.getWidth(), boardImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D grayGraphics = grayImage.createGraphics();
        grayGraphics.drawImage(boardImage, 0, 0, null);

        //Per tile identificeren welke tile het is
        //Ook opslaan in de rawtiles folder indien een tile niet wordt herkent
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                BufferedImage tile = grayImage.getSubimage(j * grayImage.getHeight() / 6, i * grayImage.getWidth() / 6, grayImage.getWidth() / 6, grayImage.getHeight() / 6);
                BlacksmithingPiece piece = getPiece(tile);
                boolean isBlank = isBlank(tile);
                if(isBlank) {
                	return null;
                }
                if(piece == null) {
                	  File outputfile = new File("./src/rsc/blacksmithing/rawtiles/unidentified"+(i*6+j)+".png");
                      try {
      					ImageIO.write(tile, "png", outputfile);
      					return null;
      				} catch (IOException e) {
      					e.printStackTrace();
      				}
                } else {
                	boardArray[i][j] = piece;
                }
                
            }
        }
        
        
        return boardArray;
	}

	//Kijken of de zwarte pixels overeen komen met onze gegenereerde template
	//Zie BlacksmithingTileGenerator
	private static int x = 0;
	private static BlacksmithingPiece getPiece(BufferedImage tile) {
		x++;
		for (int k = 0; k < BlacksmithingPiece.pieces.size(); k++)
       	{
		   BufferedImage image = BlacksmithingPiece.pieces.get(k).getBufferedImage();
		   boolean matches = true;
		   //+ en -3 om de borders niet te doen meetellen.
		   for (int i = 3; i < tile.getHeight()-3; i++) {
                for (int j = 3; j < tile.getWidth()-3; j++) {
                	 if (image.getRGB(j, i) == BLACK) {
                		 if (tile.getRGB(j, i) != BLACK) {
                             matches = false;
                		 }
                	 }
                }
           }
		   if(matches) {
			   PuzzlePirates.takeScreenshot(tile, x + "_" + BlacksmithingPiece.pieces.get(k));
			 return BlacksmithingPiece.pieces.get(k);   
		   }
       	}
		return null;
	}
	
	private static boolean isBlank(BufferedImage tile) {
		int total = tile.getHeight()*tile.getWidth();
		int blackTotal = 0;
		   for (int i = 0; i < tile.getHeight(); i++) {
               for (int j = 0; j < tile.getWidth(); j++) {
            	   if (tile.getRGB(j, i) == BLACK) {
                      blackTotal++;
          		 }
               }
          }
		 int perc = blackTotal/total;
		 //Als 80% van de image zwart is opnieuw probereren
		 if(perc > 0.8) {
			 return true;
		 } else {
			 return false;
		 }
	}

	public static Rectangle getPuzzleBounds() {
	
		Rectangle rectangle = PuzzlePirates.getPuzzlePiratesBounds();
		if(rectangle == null) {
			return null;
		}
		BufferedImage image = PuzzlePirates.getImage(rectangle);
		if (image.getRGB(150, 16) != BLACKSMITHINGTITLE) {
			return null;
		}

		Rectangle newBounds = new Rectangle(rectangle.x + X, rectangle.y + Y, WIDTH, HEIGHT);
		
		return newBounds;
	}
}
