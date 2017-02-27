package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import gui.tools.TransparentJPanel;
import main.Main;

/**
 * @author Jarred
 * @version 2/26/2017
 * @since 2/25/2017
 */
@SuppressWarnings("serial")
public class NotificationScreen extends JPanel implements ActionListener {

	private Image backgroundImage; 
	
	private JFrame parent;
	
	public NotificationScreen(JFrame parent) {
		//set up variables
		backgroundImage=new ImageIcon("res/img/background.png").getImage();
		this.parent=parent;
		//set up the frame
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		JButton text=new JButton("Your grades have changed!");
		text.setActionCommand("Check");
		text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		text.setForeground(Color.green);
		text.addActionListener(this);
		text.setContentAreaFilled(false);
		add(text, BorderLayout.CENTER);
		
		TransparentJPanel topPanel=new TransparentJPanel();
		topPanel.setLayout(new BorderLayout());
		JButton exitButton=new JButton();
		exitButton.setActionCommand("exit");
		exitButton.setIcon(new ImageIcon("res/img/X.png"));
		exitButton.addActionListener(this);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		topPanel.add(exitButton, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}
	
	public static void createNewNotificationScreen() {
		JFrame frame=new JFrame("Automated Grades Checker");
		NotificationScreen scr=new NotificationScreen(frame);
		frame.setContentPane(scr);
		frame.setLocation(500, 500);
		frame.setSize(300, 175);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	/**
	 * A method used for testing the NotificationScreen
	 * 
	 * @param args Ignored command-line parameter
	 */
	public static void main(String[] args) {
		createNewNotificationScreen();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		parent.dispose();
		if(arg0.getActionCommand().equals("Check")) {
			Main.main(new String[0]);
		}
	}

}
