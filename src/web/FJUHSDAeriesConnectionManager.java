package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import test.DebugLog;

import java.io.IOException;
import java.io.InputStream;

/**
 * Retrieves grades from the Aeries site maintained by FJUHSD
 * 
 * @author Jarred
 * @version 2/9/2017
 * @since 2/6/2017
 */
public final class FJUHSDAeriesConnectionManager implements WebConnectionManager{
	
	/**
	 * Completely and properly instantiates a FJUHSDAeriesConnectionManager
	 */
	public FJUHSDAeriesConnectionManager(){
		aeriesLoginURL="https://mystudent.fjuhsd.net/Parent/LoginParent.aspx";
		aeriesGradesURL="https://mystudent.fjuhsd.net/Parent/GradebookSummary.aspx";
		try {
			URL login=new URL(aeriesLoginURL);
			login.openConnection();
		}
		catch(MalformedURLException e) {
			//TODO: Handle a malformed url
			e.printStackTrace();
		} catch (IOException e) {
			//TODO: Handle another exception in connecting to a url
			e.printStackTrace();
		}
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
			data.close();
		}
		catch(IOException ioe) {
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
		}
		//TODO Implement fillInGrades()
	}
	
	//Constants
	private final String aeriesLoginURL;
	private final String aeriesGradesURL;
	
	private static String aeriesSessionIDCookie;//TODO: Initialize the SessionIDCookie
}
