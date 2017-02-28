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

import web.LoadCredentialsFromFile;
import web.WebConnectionManager;

/**
 * A class used to keep track of sets of classes
 * 
 * @author Jarred
 * @version 2/27/2017
 * @since 2/26/2017
 */
public class ClassManager {
	
	private ArrayList<Class> classes;
	
	public ClassManager(Class[] classes) {
		this(Arrays.asList(classes));
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
	 * <p>This is a a method that has both a parameter and a return value
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
	@Override
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
		StringBuilder builder=new StringBuilder();
		for(Class c:classes) {
			builder.append(c.toString()+"\n");
		}
		return builder.toString();
	}
	
	/**
	 * Writes all of this ClassManager's classes to the file storing the last data
	 */
	public void writeClassesToFile() {
		try {
			File f=new File(lastDataFile);
			if(!f.exists()) {
				f.createNewFile();
			}
			PrintWriter writer=new PrintWriter(f);
			writer.print(toString());
			writer.close();
		}
		catch(FileNotFoundException fnfe) {
			//I literally just created the file
			//I feel surrounded by insufferable cretins
		} catch (IOException e) {
			//I don't even know how this happened
			//The file did not exist, and the program could not create it.
			e.printStackTrace();
		}
	}
	
	
	//Static methods and fields

	public static final String lastDataFile="res/txt/SavedData.txt";
	private static ClassManager currentClasses=null;
	
	/**
	 * Retrieve the current classes of the user
	 * 
	 * @return A ClassManager representing all of the current classes or null, if the user is not logged in
	 */
	public static ClassManager getCurrentClasses() {
		if(currentClasses==null && LoadCredentialsFromFile.hasLoginCredentials()) {
			//If there are no login credentials, then it can not return anything, so it returns null
			WebConnectionManager.Builder builder=new WebConnectionManager.Builder(LoadCredentialsFromFile.getGradesInfoSource());
			currentClasses=builder.build().fillInGrades();
		}
		return currentClasses;
	}
	
	/**
	 * Loads the saved data from the last time it was run
	 * 
	 * @return The class data from last time
	 */
	public static ClassManager getSavedClasses() {
		try {
			File f=new File(lastDataFile);
			if(!f.exists()) {
				f.createNewFile();
			}
			Scanner input=new Scanner(f);
			StringBuilder buffer=new StringBuilder();
			while(input.hasNextLine()) {
				buffer.append(input.nextLine()+"\n");
			}
			input.close();
			return getClassManagerFromString(buffer.toString());
		}
		catch (FileNotFoundException e) {
			//I literally just created that file
			//I feel like I'm surrounded by insufferable cretins
			return null;
		} catch (IOException e) {
			//The file did not exist, and I could not create the file
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
	 * @throws FileNotFoundException If the tester breaks
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Class> classes=new ArrayList<>();
		classes.add(new Class(0, "AP-US History", ".875", "Feb 9"));
		classes.add(new Class(1, "French", ".975", "Jan 8"));
		classes.add(new Class(2, "Band", "1", "May 4"));
		classes.add(new Class(3, "Physics", ".6108", "Feb 26"));
		classes.add(new Class(4, "Calculus", ".918", "Jan 8"));
		System.out.println(getSavedClasses().equals(new ClassManager(classes)));
	}
}
