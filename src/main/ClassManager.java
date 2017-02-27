package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A class used to keep track of sets of classes
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 2/26/2017
 */
public class ClassManager {
	private ArrayList<Class> classes;
	
	public ClassManager(Class[] classes) {
		this.classes=new ArrayList<>(Arrays.asList(classes));
	}
	
	public ClassManager(Collection<Class> classes) {
		this.classes=new ArrayList<>(classes);
	}
	
	public void writeToSavedClasses() {
		try {
			File f=new File(lastDataFile);
			if(!f.exists()) {
				f.createNewFile();
			}
			PrintWriter writer=new PrintWriter(f);
			for(Class c:classes) {
				writer.write(c.toString()+"\n");
			}
			writer.close();
		}
		catch(IOException ioe) {
			//Something has broken beyond my abilities to fix
			System.exit(1);
		}
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			ClassManager mgr=(ClassManager)obj;
			return classes.equals(mgr.getClasses());
		}
		catch (ClassCastException cce) {
			return false;
		}
	}
	
	/**
	 * @inheritDoc
	 */
	public int hashCode() {
		return classes.hashCode();
	}
	
	public List<Class> getClasses() {
		return Collections.unmodifiableList(classes);
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		return classes.toString();
	}
	
	
	//Static methods and fields

	public static final String lastDataFile="res/txt/SavedData.txt";
	
	/**
	 * Loads the saved data from the last time it was run
	 * 
	 * @return The class data from last time
	 */
	public static ClassManager getSavedClasses() {
		try {
			Scanner input=new Scanner(new File(lastDataFile));
			StringBuilder buffer=new StringBuilder();
			while(input.hasNextLine()) {
				buffer.append(input.nextLine()+"\n");
			}
			input.close();
			return getClassManagerFromString(buffer.toString());
		}
		catch (FileNotFoundException e) {
			//The file does not exist, so I return null due to no such classes existing
			//Please do not 
			return null;
		}
	}
	
	/**
	 * Take this string and return a ClassManager representing all stored classes
	 * 
	 * @param str The classes, each separated by a line
	 * @return A Classmanager storing all of those classes
	 */
	public static ClassManager getClassManagerFromString(String str) {
		Scanner input=new Scanner(str);
		ClassManager savedData=new ClassManager(new ArrayList<Class>());
		while(input.hasNextLine()) {
			String next=input.nextLine();
			savedData.classes.add(Class.fromString(next));
		}
		input.close();
		return savedData;
	}
	
	
	/**
	 * Method for testing this class
	 *  
	 * @param args Ignored command-line arguments
	 */
	public static void main(String[] args) {
		/*ArrayList<Class> classes=new ArrayList<>();
		classes.add(new Class(1, "AP-US History", .875, "Feb 9"));
		classes.add(new Class(1, "French", .998, "Jan 8"));
		classes.add(new Class(2, "Band", 1, "May 4"));
		classes.add(new Class(3, "Physics", .6108, "Feb 26"));
		classes.add(new Class(4, "Calculus", .918, "Jan 8"));
		System.out.println(getSavedClasses().equals(new ClassManager(classes)));*/
	}
}
