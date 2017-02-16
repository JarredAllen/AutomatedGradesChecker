package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import test.DebugLog;

import java.io.IOException;
import java.io.InputStream;

/**
 * Retrieves grades from the Aeries site maintained by FJUHSD
 * 
 * @author Jarred
 * @version 2/13/2017
 * @since 2/6/2017
 */
public final class FJUHSDAeriesConnectionManager implements WebConnectionManager{
	
	/**
	 * Completely and properly instantiates a FJUHSDAeriesConnectionManager
	 */
	public FJUHSDAeriesConnectionManager() {
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
			HttpsURLConnection httpConnection=(HttpsURLConnection)new URL(aeriesGradesURL).openConnection();
			httpConnection.setRequestProperty("Cookie", aeriesSessionIDCookie);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			String username="justjarred@hotmail.com";
			String password="ha, losers";
			String message=String.format("CheckCookiesEnabled=true&CheckMobileDevice=false&CheckStandaloneMode=false&CheckTabletDevice=false&PortalAccountUsername=%s&PortalAccountPassword=%s&PortalAccountUsernameLabel&Submit",
											username, password);
			httpConnection.setRequestProperty("Content-Length", String.valueOf(message.getBytes(StandardCharsets.UTF_8).length));
			httpConnection.setDoOutput(true);
			httpConnection.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));
			httpConnection.connect();
			Scanner input=new Scanner(httpConnection.getInputStream());
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
	
	@Override
	/**
	 * @inheritDoc
	 */
	public InputStream getMainGradesPage() {
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
	
	//Constants
	public static final String aeriesLoginURL="https://mystudent.fjuhsd.net/Parent/LoginParent.aspx?page=GradebookSummary.aspx";
	public static final String aeriesGradesURL="https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx";
	
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
