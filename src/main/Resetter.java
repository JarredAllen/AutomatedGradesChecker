package main;

import java.io.File;

import javax.swing.JOptionPane;

/**
 * A class used to reset the project to its blank state, as though it has never been used
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 2/26/2017
 */
public class Resetter {

	public static final void reset() {
		int option=JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?\n"+
								"You will exit this application.", "Log Out", JOptionPane.OK_CANCEL_OPTION);
		if(option==JOptionPane.OK_OPTION) {
			File f=new File("res/txt");
			for(File txt:f.listFiles()) {
				txt.delete();
			}
		}
	}
	
	/**
	 * Reset any data in the project to blank
	 * 
	 * @param args Ignored command line parameters
	 */
	public static void main(String[] args) {
		reset();
	}

}
