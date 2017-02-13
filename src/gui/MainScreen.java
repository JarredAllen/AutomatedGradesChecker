package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The class that handles the main window of the application once it loads.
 * 
 * @author Jarred
 * @version 2/11/2017
 * @since 1/14/2017
 */
public class MainScreen extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private NorthSubPanel northPanel;
	
	/**
	 * Creates a new MainScreen object
	 */
	public MainScreen() {
		this(LOGIN_SCREEN);
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
		JPanel centerPanel=null;
		switch(screenCode) {
		case MENU_SCREEN:
			centerPanel=new GradesOverview();
			break;
			
		case LOGIN_SCREEN:
			centerPanel=new LoginScreen();
			//TODO: Insert a center panel based on the selection of the user for their screen.
		}
		add(centerPanel, BorderLayout.CENTER);
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
}
