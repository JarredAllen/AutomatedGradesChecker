package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import test.DebugLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

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
		aeriesSessionIDCookie="ASP.NET_SessionId=53apd0ozysepytknlj2ffrd0";//TODO Figure out the correct cookie
		try {
			URL login=new URL(aeriesGradesURL);
			URLConnection connection=login.openConnection();
			connection.setDoOutput(true);
			Map<String, List<String>> responses=connection.getHeaderFields();
			for(String header:responses.keySet()) {
				if(header.equalsIgnoreCase("set-cookie")) {
					for(String value:responses.get(header)) {
						String cookie=value.split(";\\s*")[0];//splits it by semicolon followed by any whitespace
					}
				}
			}
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			Scanner input=new Scanner(connection.getInputStream());
			while(input.hasNextLine()) {
				System.out.println(input.nextLine());
			}
			//close resources after this is done
			input.close();
			out.close();
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
	public static final String aeriesLoginURL="https://mystudent.fjuhsd.net/Parent/LoginParent.aspx";
	public static final String aeriesGradesURL="https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx";
	
	private String aeriesSessionIDCookie;
	
	
	//Everything after here is for testing purposes
	
	/**
	 * Some testing functionality
	 * 
	 * @param args An ignored parameter
	 */
	public static void main(String[] args) {
		new FJUHSDAeriesConnectionManager();
	}
}
