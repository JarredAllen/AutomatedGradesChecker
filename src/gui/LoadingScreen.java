package gui;

import java.awt.Graphics;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The loading screen that is displayed when the application is first booted up.
 * 
 * @author Jarred
 * @version 2/13/2017
 * @since 1/14/2017
 */
public class LoadingScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	public LoadingScreen() {
		setLayout(new BorderLayout());
		add(new ImageDisplay("res/img/GroupLogo.png"), BorderLayout.CENTER);
		add(new JLabel("Loading our wonderful app..."), BorderLayout.SOUTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
