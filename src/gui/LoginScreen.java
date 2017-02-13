package gui;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import web.LoadCredentialsFromFile;

/**
 * A JPanel that shows the login screen for the user to log in.
 * 
 * @author Jarred
 * @version 2/12/2017
 * @since 2/9/2017
 */
@SuppressWarnings("serial")
public class LoginScreen extends JPanel implements ActionListener {
	
	private ArrayList<LoginResponder> responders;

	private JTextField username;
	private JPasswordField password;
	private JCheckBox rememberLogin;
	
	/**
	 * Completely and properly instantiates a LoginScreen object
	 */
	public LoginScreen() {
		super();
		responders=new ArrayList<>();
		
		setLayout(new BorderLayout());
		
		JPanel centerPanel=new JPanel();
		centerPanel.setLayout(new FlowLayout());
		username=new JTextField(35);
		password=new JPasswordField(35);
		rememberLogin=new JCheckBox("Remember login (necessary for notifications to work)");
		rememberLogin.setSelected(true);
		centerPanel.add(new JLabel("Username: "));
		centerPanel.add(username);
		centerPanel.add(new JLabel("Password: "));
		centerPanel.add(password);
		centerPanel.add(rememberLogin);
		add(centerPanel, BorderLayout.CENTER);
		add(new JButton("Submit"), BorderLayout.SOUTH);
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

	@Override
	/**
	 * Should only be called when the user submits
	 */
	public void actionPerformed(ActionEvent e) {
		String uname=username.getText();
		String pass=new String(password.getPassword());
		if(uname.equals("")) {
			//TODO Finish error checking
		}
		if(rememberLogin.isSelected()) {
			LoadCredentialsFromFile.setCredentials(uname, pass);
		}
		else {
			LoadCredentialsFromFile.removeCredentials();
		}
		for(LoginResponder lr:responders) {
			lr.respondToLogin(uname, pass);
		}
	}
	
	
	/**
	 * Interface to be implemented by any class that needs to respond to login events
	 * 
	 * @author Jarred
	 * @version 2/12/2017
	 * @since 2/9/2017
	 */
	public interface LoginResponder {
		void respondToLogin(String username, String password);
	}
}
