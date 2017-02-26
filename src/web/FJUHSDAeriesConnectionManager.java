package web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * A class to automatically connect to Aeries and read the grades
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 2/26/2017
 */
public class FJUHSDAeriesConnectionManager implements WebConnectionManager {
	
	public FJUHSDAeriesConnectionManager() {
		login();
	}
	
	/**
	 * Before this method is called, it is not guaranteed that cookies[] has actual data
	 */
	public void login() {
		logIn(true);
	}
	
	private void logIn(boolean tryAgain) {
		try {
			String[] credentials=getCredentials();
			Process p=Runtime.getRuntime().exec(String.format("python src/python/fjuhsdConnect.py %s %s", 
													credentials[0], credentials[1]));
			Scanner input=new Scanner(p.getInputStream());
			try {
				p.waitFor();
			}
			catch (InterruptedException e) {
				if(p.isAlive()) {
					//If that is the case, something went horribly wrong, so I just give up
					System.exit(1);
				}
			}
			String a=input.next();
			String b=input.next();
			String[] c={a,b};
			cookies=c;
			input.close();
		}
		catch (IOException e) {
			//Unless this is a one-off thing, I don't see how it can recover
			//So I try it again, and, if it still breaks, give up
			if(tryAgain) {
				logIn(false);
			}
			else {
				System.exit(1);
			}
		}
		System.out.println(Arrays.asList(cookies));
	}
	
	public String[] getCredentials() {
		if(LoadCredentialsFromFile.hasLoginCredentials()) {
			return new String[0];
		}
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = {"Username:", username, "Password:", password};
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		while (option != JOptionPane.OK_OPTION) {
			option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		}
		return new String[0];
	}
	
	public String getLoginPage() {
		try {
			URL login = new URL(aeriesLoginURL);
			URLConnection connection=login.openConnection();
			connection.connect();
			Scanner page=new Scanner(connection.getInputStream());
			String buffer="";
			while(page.hasNext()) {
				buffer+=page.nextLine()+"\n";
			}
			page.close();
			return buffer;
		}
		catch (IOException ioe) {
			//see note on catch in constructor
			System.exit(1);
		}
		return "error";
	}
	
	@Override
	/**
	 * @inheritDoc
	 */
	public String getMainGradesPage() {
		if(!loggedIn) {
			login();
		}
		try {
			URL login = new URL(aeriesLoginURL);
			URLConnection connection=login.openConnection();
			connection.connect();
			Scanner page=new Scanner(connection.getInputStream());
			String buffer="";
			while(page.hasNext()) {
				buffer+=page.nextLine()+"\n";
			}
			page.close();
			return buffer;
		}
		catch (IOException ioe) {
			//see note on catch in constructor
			System.exit(1);
		}
		return "error";
	}
	
	@Override
	/**
	 * @inheritDoc
	 */
	public void fillInGrades() {
		@SuppressWarnings("unused")
		String data=getMainGradesPage();
		//TODO Finish this and remove the warning for unused data 
	}
	
	private void prepareConnection(HttpURLConnection con) throws ProtocolException {
		con.setRequestProperty("Cookie", getAllCookies());
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("User-Agent", userAgent);
	}
	
	private String getAllCookies() {
		StringBuilder value=new StringBuilder();
		for (String cookie:cookies) {
			if(value.length()!=0) {
				value.append(";");
			}
			value.append(cookie);
		}
		return value.toString();
	}
	
	//Constants
	public static final String aeriesLoginURL="https://mystudent.fjuhsd.net/Parent/LoginParent.aspx?page=GradebookSummary.aspx";
	public static final String aeriesGradesURL="https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx";
	public static final String userAgent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	
	//Instance variables
	private String[]cookies;
	private boolean loggedIn;
	
	
	//Everything after here is for testing purposes
	
	/**
	 * Testing some functionality
	 * 
	 * @param args An ignored parameter
	 */
	public static void main(String[] args) {
		new FJUHSDAeriesConnectionManager();
	}

}
