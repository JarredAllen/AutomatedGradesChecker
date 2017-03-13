package web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.ClassManager;

/**
 * A class to automatically connect to Aeries and read the grades
 * 
 * @author Jarred
 * @version 2/27/2017
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
			Scanner err=new Scanner(p.getErrorStream());
			try {
				p.waitFor();
			}
			catch (InterruptedException e) {
				if(p.isAlive()) {
					//If that is the case, something went horribly wrong, so I just give up
					System.exit(1);
				}
			}
			if(err.hasNext()) {
				input.close();
				err.close();
				System.out.println("Python process threw an exception");
				throw new IOException("Python process threw an exception");
			}
			String a=input.next();
			String b=input.next();
			String[] c={a,b};
			cookies=c;
			input.close();
			err.close();
			loggedIn=true;
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
		//System.out.println(Arrays.asList(cookies));
	}
	
	public String[] getCredentials() {
		String[]streetCred= new String[2];
		if("FJUHSD_Aeries".equals(LoadCredentialsFromFile.getGradesInfoSource())) {
			streetCred[0]=LoadCredentialsFromFile.getRememberedUsername();
			streetCred[1]=LoadCredentialsFromFile.getRememberedPassword();
		}
		else {
			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			Object[] message = {"Username:", username, "Password:", password};
			int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
			while (option != JOptionPane.OK_OPTION) {
				option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
			}
			streetCred[0]=username.getText();
			streetCred[1]=password.getText();
		}
		return streetCred;
	}
	
	/**
	 * Get the text of the login page by using a while loop to load the entire contents of the page into memory.
	 * <p>Uses a loop so it meets that mastery requirement.
	 * @return
	 */
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
			URL grades = new URL(aeriesGradesURL);
			URLConnection connection=grades.openConnection();
			prepareConnection((HttpURLConnection)connection);
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
	 * <p>Uses external library JSoup to fill in the grades
	 */
	public ClassManager fillInGrades() {
		String data=getMainGradesPage();
		Document page=Jsoup.parse(data);
		Element gradesTable=page.getElementById("ctl00_MainContent_subGBS_tblEverything");//ctl00_MainContent_subGBS_DataDetails_ctl00_trHeader
		StringBuilder classes=new StringBuilder();
		gradesTable=gradesTable.children().first().children().first().children().first().children().first().children().get(0);
													//Just moving down the hierarchy to the grades
		gradesTable=gradesTable.children().get(0).children().first().children().first().children().first();
		ArrayList<Element>rows=gradesTable.children();
		rows.remove(0);
		for(Element e:rows) {
			if(e.id().equals("ctl00_MainContent_subGBS_DataDetails_ctl09_trPriorTermHeading")) {
				break;
			}
			Elements entries=e.children();
			try {
				String period=entries.get(4).text().replace(' ', '_');
				String courseName=entries.get(2).text().replace(' ', '_');
				String grade=entries.get(6).children().first().text().replace(' ', '_');
				String date=entries.get(12).text().replace(' ', '_');
				classes.append(period+" "+courseName+" "+grade+" "+date+" \n");
			}
			catch(IndexOutOfBoundsException ioobe) {
				ioobe.printStackTrace();
				System.out.print(entries);
			}
		}
		
		return ClassManager.getClassManagerFromString(classes.toString());
	}
	
	private void prepareConnection(HttpURLConnection con) throws ProtocolException {
		con.setRequestProperty("Cookie", getAllCookies());
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
		System.out.println(new FJUHSDAeriesConnectionManager().fillInGrades());
	}

}
