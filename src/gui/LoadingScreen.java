package gui;

import java.awt.Graphics;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The loading screen that is displayed when the application is first booted up.
 * 
 * @author Jarred
 * @version 1/14/2017
 * @since 1/14/2017
 */
public class LoadingScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	public LoadingScreen() {
		setLayout(new FlowLayout());
		
		add(new JLabel("Loading our wonderful app..."));
	}
	
	//TODO Draw the loading screen for the user.
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
