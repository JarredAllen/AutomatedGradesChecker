package gui;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import web.LoadCredentialsFromFile;

/**
 * A JPanel that shows the login screen for the user to log in.
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 2/9/2017
 */
@SuppressWarnings("serial")
public class LoginScreen extends JPanel implements ActionListener {
	
	private ArrayList<LoginResponder> responders;

	private JTextField username;
	private JPasswordField password;
	private JCheckBox rememberLogin;
	private JComboBox<String> serverField;
	
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
		serverField=new JComboBox<String>(web.WebConnectionManager.validNotDeprecatedServers);
		rememberLogin=new JCheckBox("Remember login (necessary for notifications to work)");
		rememberLogin.setSelected(true);
		centerPanel.add(new JLabel("Username: "));
		centerPanel.add(username);
		centerPanel.add(new JLabel("Password: "));
		centerPanel.add(password);
		centerPanel.add(new JLabel("Login Server: "));
		centerPanel.add(serverField);
		centerPanel.add(rememberLogin);
		add(centerPanel, BorderLayout.CENTER);
		JButton submitButton=new JButton("Submit");
		submitButton.addActionListener(this);
		add(submitButton, BorderLayout.SOUTH);
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
		//System.out.println(responders);
		String uname=username.getText();
		String pass=new String(password.getPassword());
		String serv=serverField.getSelectedItem().toString();
		if(uname.equals("")) {
			JOptionPane.showMessageDialog(null, "Please input a username");
			return;
		}
		if(pass.equals("")) {
			JOptionPane.showMessageDialog(null, "Please input a password");
			return;
		}
		if(rememberLogin.isSelected()) {
			LoadCredentialsFromFile.setCredentials(serv, uname, pass);
		}
		else {
			int option=JOptionPane.showConfirmDialog(null, "Are you sure you don't want to remember your login credentials?\n" +
							"This portion of the app is still in beta test and may not work.", "Confirm", JOptionPane.OK_CANCEL_OPTION);
			if(option==JOptionPane.OK_OPTION) {
				LoadCredentialsFromFile.removeCredentials();
			}
			else {
				LoadCredentialsFromFile.setCredentials(serv, uname, pass);
			}
		}
		for(LoginResponder lr:responders) {
			//System.out.println("Alerting responder "+lr.getClass().toString());
			lr.respondToLogin(serv, uname, pass);
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
		void respondToLogin(String source, String username, String password);
	}
	
	/**
	 * Only here for testing purposes
	 * 
	 * @param args Ignored command line parameter
	 */
	public static void main(String[] args) {
		JFrame frame=new JFrame("Automated Grades Checker");
		LoginScreen scr=new LoginScreen();
		frame.setContentPane(scr);
		frame.setLocation(500, 500);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(false);
		frame.setVisible(true);
	}
}
