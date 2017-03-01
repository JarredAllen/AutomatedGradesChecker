package main;

import test.DebugLog;
import web.LoadCredentialsFromFile;

/**
 * A class that handles loading the required data and then stores it for parts of the program to recall later.
 * 
 * @author Jarred, Jason
 * @version 2/28/2017
 * @since 1/14/2017
 */
public class Loader implements Runnable{

	private boolean isLoaded;
	
	public boolean hasCredentials;
	
	/**
	 * Constructor for Loader 
	 */
	public Loader() {
		isLoaded=false;
		hasCredentials=false;
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public void run() {
		if(LoadCredentialsFromFile.hasLoginCredentials()) {
			hasCredentials=true;
			ClassManager.getCurrentClasses();
		}
		DebugLog.logStatement("About to wake up Main. (concurrency)", DebugLog.CONCURRENCY_LOG_CODE);
		isLoaded=true;
		synchronized(Main.lock) {
			Main.lock.notifyAll();
		}
		DebugLog.logStatement("Main has been woken up. (concurrency)", DebugLog.CONCURRENCY_LOG_CODE);
	}
	
	/**
	 * returns current status of isLoaded
	 */
	public boolean isLoaded() {
		return isLoaded;
	}
}
