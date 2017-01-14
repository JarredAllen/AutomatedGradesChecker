package main;

import java.util.*;

/**
 * @author Jarred
 * @version 1/14/2017
 * @since 1/14/2017
 * @see Main#main(String[])
 */
public class ArgumentHolder {
	
	//Options with no arguments
	private HashSet<String> options;
	
	//Options with arguments, the mapped string is the given argument
	private HashMap<String, String> paramOptions;
	
	/**
	 * Process all of the command line arguments into a format that programs can read more easily.
	 * 
	 * @param args The command line arguments that it should process
	 * @throws IllegalArgumentException If the list of arguments is not a valid argument list.
	 */
	protected ArgumentHolder(String[] args) {
		List<String> parameterlessOptions=Arrays.asList(Main.parameterlessOtions);
		List<String> parameterOptions=Arrays.asList(Main.parameterOptions);
		for(int i=0;i<args.length;i++) {
			String arg=args[i].toLowerCase();
			if(parameterlessOptions.contains(arg)) {
				options.add(arg);
				continue;
			}
			if(parameterOptions.contains(arg)) {
				try {
					String param=args[i+1];
					paramOptions.put(arg, param);
					options.add(arg);				//this line is used so that all arguments will be in the options list
													//even ones that have an associated parameter
				}
				catch(ArrayIndexOutOfBoundsException aioobe) {
					System.out.printf("Execution failed because of a missing argument to command line option %s.\n", arg);
					throw new IllegalArgumentException("Execution failed because of a missing argument to command line option"+arg);
				}
				i++;
				continue;
			}
			System.out.printf("Unrecognized option %s.", arg);
			throw new IllegalArgumentException("Unrecognized option "+arg);
		}
	}
	
	/**
	 * Determine if a given option was passed
	 * 
	 * @param arg The option to check
	 * @return True if the optionwas passed, false if it was not
	 * @since 1/14/2017
	 */
	public boolean containsOption(String arg) {
		if(arg==null) {
			return false;
		}
		return options.contains(arg.toLowerCase());
	}
	
	/**
	 * Retrieve the argument passed into this option
	 * 
	 * @param option The option to check
	 * @return The argument given to this option, or <code>null</code> if either the option was not provided or the option was provided
	 				without an argument
	 * @since 1/14/2017
	 */
	public String getArgument(String option) {
		return paramOptions.get(option);
	}
}
