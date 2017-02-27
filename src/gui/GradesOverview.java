package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

//import java.util.List;

//import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import main.ClassManager;

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
		
		JPanel listOfGrades=new JPanel();
		String[]classes=ClassManager.getCurrentClasses().toString().split("\n");
		listOfGrades.setLayout(new GridLayout(classes.length, 4));
		for(String cls:classes) {
			String[]fields=cls.split(" ");
			listOfGrades.add(new JLabel("Period "+fields[0]));
			listOfGrades.add(new JLabel(fields[1].replace('_', ' ')));
			listOfGrades.add(new JLabel(fields[2]));
			listOfGrades.add(new JLabel("Updated "+fields[3].replace('_', ' ')));
		}
		add(listOfGrades, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		JFrame frame=new JFrame("Automated Grades Checker");
		frame.setContentPane(new GradesOverview());
		frame.setLocation(500, 500);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(false);
		frame.setVisible(true);
	}
}
