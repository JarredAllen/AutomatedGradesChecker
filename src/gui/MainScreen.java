package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * The class that handles the main window of the application once it loads.
 * 
 * @author Jarred
 * @version 1/14/2017
 * @since 1/14/2017
 */
public class MainScreen extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private NorthSubPanel northPanel;
	
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
			//TODO: Insert a center panel based on the selection of the user for their screen.
		}
	}
	
	//Constants because magic numbers are bad
	
	/**
	 * Open the application to the default menu screen
	 * @since 1/14/2017
	 */
	public static final int MENU_SCREEN=0;
	/**
	 * Open the application to the screen for grades in one specific class.
	 *  <p>
	 *  The code to be passed in is this number plus the period of the class that is being shown in-depth
	 *  
	 *  @since 1/14/2017
	 */
	public static final int GRADES_SCREEN=16;
	
	/**
	 * The subpanel used to populate the northern part of the MainScreen
	 * 
	 * @author Jarred
	 * @version 1/14/2017
	 * @since 1/14/2017
	 */
	private class NorthSubPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		public NorthSubPanel() {
			setLayout(new BorderLayout());
		}
	}
}
