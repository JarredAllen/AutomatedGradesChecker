package main;

/*
 * See comment in static{} block
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
*/

/**
 * A class that handles storing information on the user's classes.
 * <p>This class is a user-defined class that stores a record, so it meets two master requirements.
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 2/6/2017
 */
class Class {
	
	private int period;

	private String name;
	private String grade;
	private String dateUpdated;
	
	static {
		//commented out in case I want to re-implement this functionality
		//classes=new ArrayList<Class>();
		
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
	 * Properly instantiate a new Class object. Note that it may replace spaces with underscores for string arguments
	 * 
	 * @param period The period of the class
	 * @param name The class name
	 * @param grade The grade the user has in the class
	 * @param dateUpdated The last time grades were updated
	 */
	public Class(int period, String name, String grade, String dateUpdated) {
		this.period=period;
		this.name=name.replace(' ', '_');
		this.grade=grade;
		this.dateUpdated=dateUpdated.replace(' ', '_');
	}
	

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Class) {//simple selection
			Class oc=(Class)obj;
			return period==oc.period && name.equals(oc.name) && grade.equals(oc.grade) && dateUpdated.equals(oc.dateUpdated);
		}
		return false;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return period+new Double(grade).hashCode()+name.hashCode()+dateUpdated.hashCode();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		return String.format("%d %s %s %s", period, name, grade, dateUpdated);
	}
	
	//getters and setters

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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	
	
	//Static fields and methods
	/**
	 * Produces a new Class object from the given string.
	 * <p><strong>Warning:</strong> It has undefined behavior if the string is not from a Class.toString() call
	 * 
	 * @param str The string from which a new Class object is to be built
	 * @return A class object equal to the one represented by the string
	 */
	public static Class fromString(String str) {
		String[] next=str.split(" ");
		return new Class(Integer.parseInt(next[0]), next[1], next[2], next[3]);
	}
}
