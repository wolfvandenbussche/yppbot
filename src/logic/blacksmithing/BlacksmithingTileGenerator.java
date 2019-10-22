package logic.blacksmithing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


//Wordt alleen gebruikt tijdens development
public class BlacksmithingTileGenerator {
	
	
	  public static void main(String[] args) {
		  generateTiles();
	    }
	  
	  private static void generateTiles() {
		  File dir = new File("./src/rsc/blacksmithing/generatedtiles/");
	        if (dir.isDirectory()) {
	            String[] directories = dir.list((File current, String name) -> new File(current, name).isDirectory());
	            for (String directorie : directories) {
	                BufferedImage finalImage = new BufferedImage(60, 60, BufferedImage.TYPE_BYTE_BINARY);
	                for (int i = 0; i < finalImage.getHeight(); i++) {
	                    for (int j = 0; j < finalImage.getWidth(); j++) {
	                        Color color = new Color(0, 0, 0);
	                        finalImage.setRGB(j, i, color.getRGB());
	                    }
	                }
	                String[] imageStrings = new File("./src/rsc/blacksmithing/generatedtiles/" + directorie).list();
	                int count = 0;
	                for (String imageString : imageStrings) {
	                    try {
	                        File img = new File("./src/rsc/blacksmithing/generatedtiles/"+directorie+"/"+imageString);
	                        BufferedImage image = ImageIO.read(img);
	                        for (int i = 0; i < image.getHeight(); i++) {
	                            for (int j = 0; j < image.getWidth(); j++) {
	                                if(finalImage.getRGB(j, i) != image.getRGB(j, i)){
	                                    Color color = new Color(255,255,255);
	                                    finalImage.setRGB(j, i, color.getRGB());
	                                }
	                            }
	                        }
	                        count++;
	                        File rename = new File("./src/rsc/blacksmithing/generatedtiles/"+directorie+"/"+directorie + count+".png");
	                        img.renameTo(rename);
	                    } catch (IOException exception) {
	                        System.out.println(exception);
	                    }
	                }
	            	try {
	        			File outputfile = new File( "src/rsc/blacksmithing/generatedtiles/" + directorie+".png");
	        			ImageIO.write(finalImage, "png", outputfile);
	        		} catch (IOException e) {
	        			System.out.println(e);
	        			e.printStackTrace();
	        		}
	            }
	        }
	       
	  }
}
