package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.List;

import main.Class;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JPanel that displays all of the user's classes and the user's current grades in the class
 * 
 * @author Jarred
 * @version 2/6/2017
 * @since 2/6/2017
 */
@SuppressWarnings("serial")
public class GradesOverview extends JPanel {
	
	public GradesOverview() {
		setLayout(new BorderLayout());
		
		List<Class> classes=Class.listAllClasses();
		
		JPanel listOfGrades=new JPanel();
		listOfGrades.setLayout(new GridLayout(classes.size(), 3));
		for(Class c:classes) {
			listOfGrades.add(new JLabel(c.getPeriod()+""));
			listOfGrades.add(new JLabel(c.getName()));
			listOfGrades.add(new JLabel(String.format("%.01f",c.getGrade()*100)+"%"));
		}
		add(listOfGrades, BorderLayout.CENTER);
	}
	
}
