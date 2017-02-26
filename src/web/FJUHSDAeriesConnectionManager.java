package web;

import test.DebugLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Retrieves grades from the Aeries site maintained by FJUHSD.
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 2/25/2017
 * @see OldFJUHSDAeriesConnectionManager
 */
public final class FJUHSDAeriesConnectionManager implements WebConnectionManager {
	
	private boolean loggedIn;
	
	public FJUHSDAeriesConnectionManager() {
		cookies=new String[2];
		loggedIn=false;
		try {
			cookies[0]="AeriesNet="+URLEncoder.encode("LastSC_0=808&LastSN_0=5713&LastID_0=800002942", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// This should never be thrown
			System.exit(1);
		}
		URL login;
		try {
			login = new URL(aeriesLoginURL);
			URLConnection connection=login.openConnection();
			Map<String, List<String>> responses=connection.getHeaderFields();
			for(String header:responses.keySet()) {
				if("set-cookie".equalsIgnoreCase(header)) {
					for(String value:responses.get(header)) {
						String cookie=value.split(";\\s*")[0];//splits it by semicolon followed by any whitespace
						if(cookie.split("=")[0].equalsIgnoreCase("ASP.NET_SessionId")) {
							cookies[1]=cookie;
						}
						System.out.println(cookie);
					}
				}
			}
		}
		catch (IOException e) {
			//I do not believe that the application can recover from this, so I am simplifying the recovery process
			//Maybe make this start the application over, if you have time later
			System.exit(1);
		}
		login();
		//System.out.println(aeriesSessionIDCookie);
	}
	
	/**
	 * Called to make sure that the application logs in before advancing.</p>
	 * <p>When fully implemented, it will request the password of the user before advancing
	 */
	public void login() {
		JTextField username = new JTextField();
		JTextField password = new JPasswordField();
		Object[] message = {"Username:", username, "Password:", password};
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		while (option != JOptionPane.OK_OPTION) {
			option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		}
		login(username.getText(), password.getText());
	}
	
	/**
	 * Login on the web connection
	 * 
	 * @param username The username to use for logging in
	 * @param password The password to use for logging in
	 */
	public void login(String username, String password) {
		try {
			HttpsURLConnection others=(HttpsURLConnection)new URL("https://mystudent.fjuhsd.net/Parent/GeneralFunctions.asmx/GetIDPFromDomain").openConnection();
			others.setRequestProperty("Cookie", getAllCookies());
			others.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			others.setDoOutput(true);
			PrintWriter pw=new PrintWriter(others.getOutputStream());
			pw.write("{\"EM\":\"justjarred@hotmail.com\"}");
			pw.close();
		}
		catch (IOException e1) {
			// See above
			System.exit(1);
		}
		
		String html=getLoginPage();
		Document d=Jsoup.parse(html);
		
		//parse the html document and find all appropriate forms
		Element loginForm=d.getElementsByTag("form").first();
		Elements fields=loginForm.getElementsByTag("input");
		ArrayList<String>forms=new ArrayList<>();
		for(Element field:fields) {
			String name=field.attr("name");
			String body="";
			switch(name) {
			case "portalAccountUsername":
				body=username;
				break;
				
			case "portalAccountPassword":
				body=password;
				break;
				
			case "checkCookiesEnabled":
				body="true";
				break;

			case "checkMobileDevice":
			case "checkStandaloneMode":
			case "checkTabletDevice":
				body="false";
				break;
			
			case "portalAccountUsernameLabel":
			case "submit":
				break;
				
			default:
				DebugLog.logStatement("Unrecognized form on Aeries: "+name, DebugLog.INTERNET_LOG_CODE);
			}
			try {
				forms.add(name+"="+URLEncoder.encode(body, "UTF-8"));
			}
			catch (UnsupportedEncodingException e) {
				//I do not see how this exception may ever be thrown, but it must be caught anyways
				//If it happens, it might be recoverable or it might break later on in the code
				DebugLog.logStatement("Format error for encoding "+body+"in UTF-8.", DebugLog.CONCURRENCY_LOG_CODE);
			}
		}
		StringBuilder result=new StringBuilder();
		for(String param:forms) {
			if(result.length()==0) {
				result.append(param);
			}
			else {
				result.append("&"+param);
			}
		}
		//System.out.println(result.toString());
		try {
			HttpsURLConnection conn=(HttpsURLConnection)new URL(aeriesLoginURL).openConnection();
			prepareConnection(conn);
			conn.setDoOutput(true);
			new PrintWriter(conn.getOutputStream()).println(String.format("checkCookiesEnabled=true&checkMobileDevice=false&checkStandaloneMode=false&checkTabletDevice=false&portalAccountUsername=%s&portalAccountPassword=%s&portalAccountUsernameLabel=&submit=",
					username, password));
			conn.getOutputStream().close();
		}
		catch (IOException e) {
			// Such problems are likely unrecoverable (see above)
			e.printStackTrace();
			System.exit(1);
		}
		try {
			HttpURLConnection others=(HttpURLConnection)new URL("http://mystudent.fjuhsd.net/Parent/Widgets/ClassSummary/home").openConnection();
			others.setRequestProperty("Cookie", getAllCookies());
			others.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			others.setDoOutput(true);
			PrintWriter pw=new PrintWriter(others.getOutputStream());
			pw.write("{\"IsProfile\":\"True\",\"ShowPrintButton\":\"False\",\"ShowBackground\":\"True\"}");
			pw.close();
			//one last connection
			others=(HttpsURLConnection)new URL("https://mystudent.fjuhsd.net/Parent/studentdata/reportcardhistory/true/?_=1488127731489").openConnection();
			others.setRequestProperty("Cookie", getAllCookies());
			others.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			others.setRequestMethod("GET");
			others.connect();
			
		}
		catch (IOException e1) {
			// See above
			System.exit(1);
		}
		try {
			HttpsURLConnection grades=(HttpsURLConnection)new URL(aeriesGradesURL).openConnection();
			prepareConnection(grades);
			grades.setRequestMethod("GET");
			Scanner input=new Scanner(grades.getInputStream());
			while(input.hasNext()) {
				System.out.println(input.nextLine());
			}
			if(grades.getURL().toString().equals(aeriesGradesURL)) {
				loggedIn=true;
			}
			else {
				System.out.println(grades.getURL().toString());
			}
			input.close();
		}
		catch (IOException e1) {
			//should never be thrown
			e1.printStackTrace();
			System.exit(1);
		}
		loggedIn=true;
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
	public InputStream getMainGradesPage() {
		if(!loggedIn) {
			login();
		}
		//TODO Implement getMainGradesPage()
		return (InputStream)null;
	}
	
	@Override
	/**
	 * @inheritDoc
	 */
	public void fillInGrades() {
		try {
			InputStream data=getMainGradesPage();
			//TODO Implement fillInGrades()
			data.close();
		}
		catch(IOException ioe) {
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
		}
	}
	
	private void prepareConnection(HttpURLConnection con) throws ProtocolException {
		con.setRequestProperty("Cookie", getAllCookies());
		con.setUseCaches(false);
		con.setRequestMethod("POST");
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
	public static final String aeriesLoginURL="https://mystudent.fjuhsd.net/Parent/LoginParent.aspx";
	public static final String aeriesGradesURL="https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx";
	public static final String userAgent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	
	//Instance variables
	private String[]cookies;
	
	
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
