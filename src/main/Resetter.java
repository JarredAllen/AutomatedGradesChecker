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

	/**
	 * Reset any data in the project to blank
	 * 
	 * @param args Ignored command line parameters
	 */
	public static void main(String[] args) {
		int option=JOptionPane.showConfirmDialog(null, null, "Reset Application", JOptionPane.OK_CANCEL_OPTION);
		if(option==JOptionPane.OK_OPTION) {
			File f=new File("res/txt");
			for(File txt:f.listFiles()) {
				txt.delete();
			}
		}
	}

}
