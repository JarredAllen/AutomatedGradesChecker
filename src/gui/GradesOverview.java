package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

//import java.util.List;

//import javax.swing.JLabel;
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
		
		JPanel listOfGrades=new JPanel();
		
		add(listOfGrades, BorderLayout.CENTER);
	}
	
}
