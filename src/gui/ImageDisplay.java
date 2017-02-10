package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A simple JPanel for displaying an image
 * 
 * @author Jarred
 * @version 2/9/2017
 * @since 2/6/2017
 */
@SuppressWarnings("serial")
public class ImageDisplay extends JPanel {
	
	protected ImageIcon image;
	
	/**
	 * Creates a new ImageDisplay from a filename
	 * 
	 * @param filename The path to the file (relative or absolute0
	 */
	public ImageDisplay(String filename) {
		this(new ImageIcon(filename));
	}
	
	public ImageDisplay(ImageIcon image) {
		this.image=image;
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), new Color(0,0,0,255), null);
	}
}