package main;

import test.DebugLog;

/**
 * A class that handles loading the required data and then stores it for parts of the program to recall later.
 * 
 * @author Jarred
 * @version 1/14/2017
 * @since 1/14/2017
 */
public class Loader implements Runnable{

	@Override
	public void run() {
		// TODO Load everything into memory.
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ie) {}
		DebugLog.logStatement("About to wake up Main. (concurrency)", DebugLog.DEBUG_RECENT_CODE);
		synchronized(Main.lock) {
			Main.lock.notifyAll();
		}
		DebugLog.logStatement("Main has been woken up. (concurrency)", DebugLog.DEBUG_RECENT_CODE);
	}
}
