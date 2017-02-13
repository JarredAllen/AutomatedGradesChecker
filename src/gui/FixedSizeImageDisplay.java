package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
/**
 * A JPanel that is used to display an image at a specific size.
 * 
 * @author Jarred Koichi
 * @version 2/11/2017
 * @since 2/9/2017
 */
@SuppressWarnings("serial")
public class FixedSizeImageDisplay extends ImageDisplay { //hi im koichi
	//The sizes of the image that it displays
	private int xSize;
	private int ySize;
	
	/**
	 * Creates a new JPanel to display an image at a fixed size
	 * 
	 * @param image is the file that is used for the gui image
	 * @param xSize is the width of the image
	 * @param ySize is the height of the image
	 * creates the gui using image as the ImageIcon and the set dimensions
	 */
	public FixedSizeImageDisplay(ImageIcon image, int xSize, int ySize) {
		super(image);
		this.xSize=xSize;
		this.ySize=ySize;
	}
	
	/**
	 * Creates a new JPanel to display an image at a fixed size
	 * 
	 * @param filename is the name of the file
	 * @param xSize is the width of the image
	 * @param ySize is the height of the image
	 */
	public FixedSizeImageDisplay(String filename, int xSize, int ySize) {
		this(new ImageIcon(filename), xSize, ySize);
	}
	
	@Override
	/**
	 * @inheritdoc
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(image.getImage(), 0, 0, xSize, ySize, new Color(0,0,0,255), null);
	}

}
