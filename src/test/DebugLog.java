package test;

import java.util.ArrayList;

import main.Main;

/**
 * The class that logs any statements that may be useful for debugging
 * 
 * @author Jarred
 * @version 2/13/2017
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
	 * @param tag The appropriate constant for how recent the developments pertaining to it are. <b>See also:</b> {@link #CONCURRENCY_LOG_CODE}
	 */
	public static void logStatement(String line, int tag) {
		log.add(line);
		try {
			if(Main.ah.containsOption("-d")||Main.ah.containsOption("-debug")) {
				System.out.println(line);
			}
			else {
				switch(tag) {
				case CONCURRENCY_LOG_CODE:
					log.add(line+" (concurrency)");
					if(isDebuggingAll) {
						System.out.println("DEBUG LOG: "+line+" (concurrency)");
					}
					break;

				case INTERNET_LOG_CODE:
					log.add(line+" (internet)");
					if(isDebuggingAll) {
						System.out.println("DEBUG LOG: "+line+" (internet)");
					}
					break;

				case FAILURE_LOG_CODE:
					log.add(line+" (failure)");
					if(isDebuggingCurrentDevelopment) {
						System.out.println("DEBUG LOG: "+line+" (failure)");
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
		}
		catch(NullPointerException npe) {
			System.out.println("NPE in DebugLog. This should not happen if run from Main, but will otherwise happen.");
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
	/**
	 * Used to indicate that the logged statement pertains to unexpected errors in the execution.
	 * 
	 * @since 2/9/2017
	 * @see #logStatement(String, int)
	 */
	public static final int FAILURE_LOG_CODE=2;
	/**
	 * Used to indicate that the logged statement pertains to connecting to internet webpages.
	 * 
	 * @since 2/12/2017
	 * @see #logStatement(String, int)
	 */
	public static final int INTERNET_LOG_CODE=3;
	
	//Ignore this, this just makes it a static class.
	private DebugLog(){}
}
