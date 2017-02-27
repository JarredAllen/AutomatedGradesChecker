package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import gui.tools.TransparentJPanel;

import main.ClassManager;
import main.Main;

/**
 * @author Jarred, Jason
 * @version 2/27/2017
 * @since 2/25/2017
 */
@SuppressWarnings("serial")
public class NotificationScreen extends JPanel implements ActionListener {

	private Image backgroundImage; 
	
	private JFrame parent;
	
	//NOTE to future self:
	//If you need the advanceToMenuScreen parameter again, just use the following code snippet:
	//text instanceof JButton
	//and increase the scope of text so that it can be used as an instance variable
	
	/**
	 * Constructor for NotificationScreen
	 *
	 * @param parent provides JFrame for displaces
	 * @param advanceToMenuScreen determines the next process for screen
	 */
	public NotificationScreen(JFrame parent, boolean advanceToMenuScreen) {
		//set up variables
		backgroundImage=new ImageIcon("res/img/background.png").getImage();
		this.parent=parent;
		//set up the frame
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		JComponent text=null;
		if(advanceToMenuScreen) {
			text=new JButton("Your grades have changed!");
			JButton button=(JButton)text;
			button.setActionCommand("Check");
			button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			button.setForeground(Color.green);
			button.addActionListener(this);
			button.setContentAreaFilled(false);
		}
		else {
			text=new JLabel("Your grades have changed!");
			text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
			text.setForeground(Color.green);
		}
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
	/**
	 * @inheritDoc
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}
	/**
	 * Maintains updated Notification Screen
	 * 
	 * @param advanceToMenuScreen determines if process is proceeded
	 */
	public static void createNewNotificationScreen(boolean advanceToMenuScreen) {
		JFrame frame=new JFrame("Automated Grades Checker");
		NotificationScreen scr=new NotificationScreen(frame, advanceToMenuScreen);
		frame.setContentPane(scr);
		frame.setLocation(500, 500);
		frame.setSize(300, 175);
		frame.setDefaultCloseOperation(advanceToMenuScreen ? WindowConstants.EXIT_ON_CLOSE : WindowConstants.DISPOSE_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	/**
	 * A method used for testing the NotificationScreen
	 * 
	 * @param args Ignored command-line parameter
	 */
	public static void main(String[] args) {
		createNewNotificationScreen(true);
	}

	@Override
	/**
	 * @inheritDoc
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Check")) {
			ClassManager.getCurrentClasses().writeClassesToFile();
			Main.main(new String[0]);
			parent.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
		parent.dispose();
	}

}
