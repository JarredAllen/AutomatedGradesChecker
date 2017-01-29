package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.io.InputStream;

public final class WebConnectionManager {
	
	public static InputStream getMainGradesPage() {
		
		return (InputStream)null;
	}
	
	//Constants
	public static final String aeriesLoginURL;
	public static final String aeriesGradesURL;
	
	private static String aeriesSessionIDCookie;//TODO: Initialize this
	
	static {
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
	
	//just here to make it a static class
	private WebConnectionManager(){}
}
