package main;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Dimension;

import gui.LoadingScreen;

/**
 * The class that the application runs immediately upon startup.
 * 
 * @author Jarred
 * @version 1/14/2017
 * @since 1/14/2017
 */
public final class Main {

	public static ArgumentHolder ah;

	public static final String[] parameterlessOtions={"-c", "-justcheck"};
	public static final String[] parameterOptions={};
	
	
	/**
	 * Starts running the application.
	 * 
	 * <p>Command line arguments that can be passed:<table>
	 * <tr> <td>Argument</td>				<td>Meaning</td> </tr>
	 * <tr> <td> -c</br> --justcheck</td>	<td>Only check Aeries and not activate the GUI for anything.</td></tr> 
	 * </table>
	 * 
	 * @param args The command line arguments given to it.
	 */
	public static void main(String[] args) {
		ah=new ArgumentHolder(args);
		if(ah.containsOption("-c")||ah.containsOption("--justcheck")) {
			//TODO Use checking functionality here once it gets implemented
			return;
		}
		JFrame loadingScreen=new JFrame();
		loadingScreen.setContentPane(new LoadingScreen());
		loadingScreen.setSize(loadingScreenSize, loadingScreenSize);
		loadingScreen.setLocation(200,200);//getCenterOffset(loadingScreenSize/2, loadingScreenSize/2));
        loadingScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadingScreen.setUndecorated(true);
		loadingScreen.setVisible(true);
		//TODO Implement the Main.main()
	}
	
	@SuppressWarnings("unused")
	/**
	 * Gets the x and y co-ordinates needed to put the window in the center
	 * 
	 * @param xOff The x offset, with positive values to the left
	 * @param yOff The y offset, with positive values to the top
	 * @return A point
	 */
	private static Point getCenterOffset(int xOff, int yOff) {
		Dimension screenDimensions=Toolkit.getDefaultToolkit().getScreenSize();
		return new Point(screenDimensions.width-xOff, screenDimensions.height-yOff);
	}
	
	/*
	 * Just here to make it a static class, it should remain unused.
	 */
	private Main() {}
	
	//Constants that can be tweaked to alter performance
	private static int loadingScreenSize=100;

}
