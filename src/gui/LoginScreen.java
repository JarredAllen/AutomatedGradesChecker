package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * A JPanel that shows the login screen for the user to log in.
 * 
 * @author Jarred
 * @version 2/9/2017
 * @since 2/9/2017
 */
@SuppressWarnings("serial")
public class LoginScreen extends JPanel {
	
	private ArrayList<LoginResponder> responders;

	private JTextField username;
	private JPasswordField password;
	
	/**
	 * Completely and properly instantiates a LoginScreen object
	 */
	public LoginScreen() {
		super();
		responders=new ArrayList<>();
		
		setLayout(new BorderLayout());
		add(new FixedSizeImageDisplay("res/img/GroupLogo.png", 300, 350), BorderLayout.NORTH);
			//TODO The above line does not work, figure out why
		
		JPanel centerPanel=new JPanel();
		username=new JTextField();
		password=new JPasswordField();
		centerPanel.add(username);
		centerPanel.add(password);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Add a new responder to be notified on user login events
	 * 
	 * @param lr The LoginResponder to be added
	 */
	public void addResponder(LoginResponder lr) {
		responders.add(lr);
	}
	
	/**
	 * Remove a responder from the list of those to be notified on user login events
	 * 
	 * @param lr The LoginResponder to be remove
	 * @return <code>true</code> if its list included that responder
	 */
	public boolean removeResponder(LoginResponder lr) {
		return responders.remove(lr);
	}
	
	/**
	 * Interface to be implemented by any class that needs to respond to login events
	 * 
	 * @author Jarred
	 * @version 2/9/2017
	 * @since 2/9/2017
	 */
	public interface LoginResponder {
		void respondToLogin(String username);
	}
}
