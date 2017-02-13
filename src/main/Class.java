package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that handles storing information on the user's classes
 * 
 * @author Jarred
 * @version 2/6/2017
 * @since 2/6/2017
 */
public class Class implements Comparable<Class>{
	
	
	private static ArrayList<Class> classes;
	
	private int period;

	private String name;
	private double grade;
	private String dateUpdated;
	
	static {
		classes=new ArrayList<Class>();
		
		/*
		 * Commented out code because this is just to be used for testing purposes
		new Class(0,"H-Chem", .907, "Jan 1");
		new Class(1,"AP Lang", .873, "Jan 3");
		new Class(2,"Pre-Calc H", .913, "Feb 4");
		new Class(3,"Band", 1.0, "Aug 12");
		new Class(4,"History", .666, "Feb 6");
		*/
	}
	
	/**
	 * Properly instantiate a new Class object
	 * 
	 * @param period The period of the class
	 * @param name The class name
	 * @param grade The grade the user has in the class
	 * @param dateUpdated The last time grades were updated
	 */
	public Class(int period, String name, double grade, String dateUpdated) {
		this.period=period;
		this.name=name;
		this.grade=grade;
		this.dateUpdated=dateUpdated;
		classes.add(this);
	}
	
	public int compareTo(Class c) {
		return this.period-c.period;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	
	/**
	 * @return A list of all current class objects
	 */
	public static List<Class> listAllClasses() {
		return Collections.unmodifiableList(classes);
	}
}
