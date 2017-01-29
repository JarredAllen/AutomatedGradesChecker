package main;

import test.DebugLog;

/**
 * A class that handles loading the required data and then stores it for parts of the program to recall later.
 * 
 * @author Jarred
 * @version 1/15/2017
 * @since 1/14/2017
 */
public class Loader implements Runnable{

	private boolean isLoaded;
	
	public Loader() {
		isLoaded=false;
	}
	
	@Override
	public void run() {
		// TODO Load everything into memory.
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ie) {}
		DebugLog.logStatement("About to wake up Main. (concurrency)", DebugLog.CONCURRENCY_LOG_CODE);
		isLoaded=true;
		synchronized(Main.lock) {
			Main.lock.notifyAll();
		}
		DebugLog.logStatement("Main has been woken up. (concurrency)", DebugLog.CONCURRENCY_LOG_CODE);
	}

	public boolean isLoaded() {
		return isLoaded;
	}
}
