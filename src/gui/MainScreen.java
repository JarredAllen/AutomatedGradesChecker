package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.tools.FixedSizeImageDisplay;

/**
 * The class that handles the main window of the application once it loads.
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 1/14/2017
 */
public class MainScreen extends JPanel implements LoginScreen.LoginResponder {
	private static final long serialVersionUID = 1L;
	
	private NorthSubPanel northPanel;
	
	private JPanel centerPanel;
	
	/**
	 * Creates a new MainScreen object
	 */
	public MainScreen() {
		this(MENU_SCREEN);
	}
	
	/**
	 * Creates a new MainScreen object that shows the corresponding view to the end user.
	 * 
	 * @param screenCode The code for the screen that the user will see {@link #MENU_SCREEN}
	 */
	public MainScreen(int screenCode) {
		setLayout(new BorderLayout());
		northPanel=new NorthSubPanel();
		add(northPanel, BorderLayout.NORTH);
		switch(screenCode) {
		case MENU_SCREEN:
			centerPanel=new GradesOverview();
			break;
			
		case LOGIN_SCREEN:
			centerPanel=new LoginScreen();
			((LoginScreen)centerPanel).addResponder(this);
			break;
		}
		add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 */
	public void showLoginScreen(LoginScreen.LoginResponder lr) {
		
	}
	
	//Constants because magic numbers are bad
	
	/**
	 * Open the application to the default menu screen
	 * 
	 * @since 1/14/2017
	 */
	public static final int MENU_SCREEN=1;
	/**
	 * Open the application to the login screen
	 * 
	 * @since 2/9/2017
	 */
	public static final int LOGIN_SCREEN=2;
	
	/**
	 * The subpanel used to populate the northern part of the MainScreen
	 * 
	 * @author Jarred
	 * @version 1/14/2017
	 * @since 1/14/2017
	 */
	private class NorthSubPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		/**
		 * Create a new NorthSubPanel
		 */
		public NorthSubPanel() {
			setLayout(new BorderLayout());
			JLabel label=new JLabel("Welcome.");
			add(label);
			
			add(new FixedSizeImageDisplay("res/img/GroupLogo.png", 300, 350), BorderLayout.NORTH);
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void respondToLogin(String source, String username, String password) {
		centerPanel=new GradesOverview();
		repaint();
	}
}
