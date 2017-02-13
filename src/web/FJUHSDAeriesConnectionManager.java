package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.Scanner;

import test.DebugLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Retrieves grades from the Aeries site maintained by FJUHSD
 * 
 * @author Jarred
 * @version 2/12/2017
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
			Scanner input=new Scanner(connection.getInputStream());
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			
			//close resources after this is done
			input.close();
			out.close();
		}
		catch(MalformedURLException e) {
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
			System.exit(1);
		} catch (IOException e) {
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
			System.exit(1);
		}
		FJUHSDAutoConnector ac=new FJUHSDAutoConnector(aeriesSessionIDCookie);
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
	
	private String aeriesSessionIDCookie;//TODO: Initialize the SessionIDCookie
}
