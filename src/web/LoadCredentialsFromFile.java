package web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;

/**
 * A class that deals with login credentials being stored on and written to a file.
 * 
 * @author Jarred
 * @version 2/12/2017
 * @since 2/12/2017
 */
public class LoadCredentialsFromFile {
	
	public static final String credentialsFileLocation="src/web/LoginCredentials.txt";
	
	/**
	 * Check the file storing login credentials to see if it has any remembered credentials
	 * 
	 * @return <code>true</code> if such credentials are remembered 
	 */
	public static boolean hasLoginCredentials() {
		try {
			Scanner fileIn=new Scanner(new File(credentialsFileLocation));
			String line=fileIn.nextLine();
			boolean returnValue = line.equalsIgnoreCase("REMEMBERED CREDENTIALS");
			fileIn.close();
			return returnValue;
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(2);
		}
		return false;
	}
	
	/**
	 * Check the file of login credentials and return the username, if it is stored
	 * 
	 * @return The stored username, or <code>null</code> if there is no stored username
	 */
	public static String getRememberedUsername() {
		String uname = null;
		if(hasLoginCredentials()) {
			try {
				Scanner fileIn=new Scanner(new File(credentialsFileLocation));
				fileIn.nextLine();
				uname=fileIn.nextLine();
				fileIn.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(2);
			}
		}
		return uname;
	}
	
	/**
	 * Check the file of login credentials and return the password, if it is stored
	 * 
	 * @return The stored password, or <code>null</code> if there is no stored password
	 */
	public static String getRememberedPassword() {
		String password = null;
		if(hasLoginCredentials()) {
			try {
				Scanner fileIn=new Scanner(new File(credentialsFileLocation));
				fileIn.nextLine();fileIn.nextLine();
				password=fileIn.nextLine();
				fileIn.close();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(2);
			}
		}
		return password;
	}
	
	/**
	 * Tells the program to forget stored file credentials. It has no effect on the existing session.
	 */
	public static void removeCredentials() {
		try {
			File f=new File(credentialsFileLocation);
			if(!f.exists()) {
				f.createNewFile();
			}
			PrintWriter fileOut=new PrintWriter(f);
			fileOut.write("NO REMEMBERED CREDENTIALS");
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public static void setCredentials(String username, String password) {
		try {
			File f=new File(credentialsFileLocation);
			if(!f.exists()) {
				f.createNewFile();
			}
			PrintWriter fileOut=new PrintWriter(f);
			fileOut.write("REMEMBERED CREDENTIALS\n");
			fileOut.write(username);
			fileOut.write("\n");
			fileOut.write(password);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	
	/**
	 * Only here for testing purposes
	 * 
	 * @param args Does nothing
	 */
	public static void main(String[] args) {
		System.out.println(hasLoginCredentials());
		System.out.println(getRememberedUsername());
		System.out.println(getRememberedPassword());
	}
}
