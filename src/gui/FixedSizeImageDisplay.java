package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class FixedSizeImageDisplay extends ImageDisplay {
	
	private int xSize;
	private int ySize;

	public FixedSizeImageDisplay(ImageIcon image, int xSize, int ySize) {
		super(image);
		this.xSize=xSize;
		this.ySize=ySize;
	}
	
	public FixedSizeImageDisplay(String filename, int xSize, int ySize) {
		this(new ImageIcon(filename), xSize, ySize);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image.getImage(), 0, 0, xSize, ySize, new Color(0,0,0,255), null);
	}

}
