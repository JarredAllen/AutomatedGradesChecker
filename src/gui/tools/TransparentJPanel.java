package gui.tools;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A JFrame that has a transparent background
 * 
 * @author Jarred
 * @version 2/25/2017
 * @since 2/25/2017
 */
@SuppressWarnings("serial")
public class TransparentJPanel extends JPanel{
	
	public TransparentJPanel() {
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	@Override
	public void paintComponent(Graphics g) {}
}
