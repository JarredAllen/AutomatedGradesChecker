package web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import test.DebugLog;

/**
 * Does not work, only here for possible future development
 * 
 * @author Jarred
 * @version 2/25/2017
 * @since 2/6/2017
 * @see FJUHSDAeriesConnectionManager
 */
public class OldFJUHSDAeriesConnectionManager implements WebConnectionManager {
	
	/**
	 * Completely and properly instantiates an OldFJUHSDAeriesConnectionManager object
	 */
	public OldFJUHSDAeriesConnectionManager() {
		try {
			URL login=new URL(aeriesGradesURL);
			URLConnection connection=login.openConnection();
			connection.setDoOutput(true);
			Map<String, List<String>> responses=connection.getHeaderFields();
			for(String header:responses.keySet()) {
				if("set-cookie".equalsIgnoreCase(header)) {
					for(String value:responses.get(header)) {
						String cookie=value.split(";\\s*")[0];//splits it by semicolon followed by any whitespace
						if(cookie.split("=")[0].equalsIgnoreCase("ASP.NET_SessionId")) {
							aeriesSessionIDCookie=cookie;
						}
					}
				}
			}
			HttpsURLConnection httpsConnection=(HttpsURLConnection)new URL(aeriesGradesURL).openConnection();
			prepareConnection(httpsConnection);
			String username="justjarred%40hotmail.com";
			String password="ha";
			String message=String.format("checkCookiesEnabled=true&checkMobileDevice=false&checkStandaloneMode=false&checkTabletDevice=false&portalAccountUsername=%s&portalAccountPassword=%s&portalAccountUsernameLabel=&submit=",
											username, password);
			httpsConnection.setRequestProperty("Content-Length", String.valueOf(message.getBytes(StandardCharsets.UTF_8).length));
			httpsConnection.setDoOutput(true);
			httpsConnection.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
			httpsConnection.connect();
			HttpsURLConnection grades=(HttpsURLConnection)new URL(aeriesGradesURL).openConnection();
			prepareConnection(grades);
			Scanner input=new Scanner(grades.getInputStream());
			while(input.hasNextLine()) {
				System.out.println(input.nextLine());
			}
			//close resources after this is done
			input.close();
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
			
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
			System.exit(1);
		}
		catch (IOException e) {
			e.printStackTrace();
			
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
			System.exit(1);
		}
		System.out.println(aeriesSessionIDCookie);
		FJUHSDAutoConnector ac=new FJUHSDAutoConnector(aeriesSessionIDCookie);
		ac.start();
	}
	
	private void prepareConnection(HttpURLConnection con) throws ProtocolException {
		con.setRequestProperty("Cookie", aeriesSessionIDCookie);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("User_Agent", userAgent);
	}
	
	
	@Override
	/**
	 * @inheritDoc
	 */
	public InputStream getMainGradesPage() {
		//TOqDO Implement getMainGradesPage() (todo statement interrupted because this is not being worked on)
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
	
	//Constants
	public static final String aeriesLoginURL="https://mystudent.fjuhsd.net/Parent/LoginParent.aspx";
	public static final String aeriesGradesURL="https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx";
	public static final String userAgent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
	
	//Instance variables
	private String aeriesSessionIDCookie;
	
	
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
