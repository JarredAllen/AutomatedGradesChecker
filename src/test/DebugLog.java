package test;

import java.util.ArrayList;

/**
 * The class that logs any statements that may be useful for debugging
 * 
 * @author Jarred
 * @version 1/14/2017
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
			case DEBUG_ALL_CODE:
				if(isDebuggingAll) {
					System.out.println("DEBUG LOG: "+line);
				}
				break;

			case DEBUG_RECENT_CODE:
				if(isDebuggingCurrentDevelopment||isDebuggingAll) {
					System.out.println("DEBUG LOG: "+line);
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
	public static final boolean isDebuggingAll=false;

	/**
	 * Stores if the code is currently going to print all debug logs that pertain to recent development/debugging of the code to the standard out.
	 * <p>
	 * Should be false for any release.
	 * 
	 * @since 1/14/2017
	 */
	public static final boolean isDebuggingCurrentDevelopment=true;
	
	/**
	 * Constant to be used to indicate that this log statement is for older developments.
	 * 
	 * @since 1/14/2017
	 * @see #logStatement(String, int)
	 */
	public static final int DEBUG_ALL_CODE=0;
	
	/**
	 * Constant to be used to indicate that this log statement is for newer developments.
	 * 
	 * @since 1/14/2017
	 * @see #logStatement(String, int)
	 */
	public static final int DEBUG_RECENT_CODE=1;
	
	//Ignore this, this just makes it a static class.
	private DebugLog(){}
}
