package test;

import java.util.ArrayList;

/**
 * The class that logs any statements that may be useful for debugging
 * 
 * @author Jarred
 * @version 1/15/2017
 * @since 1/14/2017
 */
public final class DebugLog {
	
	private static ArrayList<String>log;
	
	static {
		log=new ArrayList<>();
	}
	
	/**
	 * Log something to the debug log
	 * 
	 * @param line The line to be added to the debug log
	 * @param tag The appropriate constant for how recent the developments pertaining to it are.
	 */
	public static void logStatement(String line, int tag) {
		log.add(line);
		switch(tag) {
			case CONCURRENCY_LOG_CODE:
				log.add(line+" (concurrency)");
				if(isDebuggingAll) {
					System.out.println("DEBUG LOG: "+line+" (concurrency)");
				}
				break;
				
			default:
				log.add(line+" (unknown type)");
				if(isDebuggingCurrentDevelopment) {
					System.out.println("DEBUG LOG: "+line+" (unknown type)");
				}
				break;
		}
	}
	
	
	//Constants because magic numbers are evil
	
	/**
	 * Stores if the code is currently going to print all debug logs to the standard out.
	 * <p>
	 * Should be false for any release.
	 * 
	 * @since 1/14/2017
	 */
	private static final boolean isDebuggingAll=false;

	/**
	 * Stores if the code is currently going to print all debug logs that pertain to recent development/debugging of the code to the standard out.
	 * <p>
	 * Should be false for any release.
	 * 
	 * @since 1/14/2017
	 */
	private static final boolean isDebuggingCurrentDevelopment=true;
	
	/**
	 * Used to indicate that the logged statement pertains to debugging concurrency.
	 * 
	 * @since 1/15/2017
	 * @see #logStatement(String, int)
	 */
	public static final int CONCURRENCY_LOG_CODE=1;
	
	//Ignore this, this just makes it a static class.
	private DebugLog(){}
}
