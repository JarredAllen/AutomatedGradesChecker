package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Dimension;

import gui.LoadingScreen;
import gui.MainScreen;
import gui.NotificationScreen;
import test.DebugLog;
import web.LoadCredentialsFromFile;
import web.WebConnectionManager;

/**
 * The class that the application runs immediately upon startup.
 * 
 * @author Jarred
 * @version 2/26/2017
 * @since 1/14/2017
 */
public final class Main {

	public static ArgumentHolder ah;

	public static final String[] parameterlessOtions={"-c", "-justcheck", "-d", "--debug"};
	public static final String[] parameterOptions={};
	
	public static final Object lock=new Object();
	
	
	/**
	 * Starts running the application.
	 * 
	 * <p>Command line arguments that can be passed:
	 * <table border="1">
	 * <tr> <td><strong>Argument</strong></td>		<td><strong>Meaning</strong></td> </tr>
	 * <tr> <td> -c</br> --justcheck</td>			<td>Only check for updates and not activate the GUI for anything.</td></tr> 
	 * <tr> <td> -d</br> --debug</td>				<td>Have extra debugging information printed out.</td>
	 * </table>
	 * 
	 * @param args The command line arguments given to it.
	 */
	public static void main(String[] args) {
		ah=new ArgumentHolder(args);
		if(ah.containsOption("-c")||ah.containsOption("--justcheck")) {
			//System.out.println("Only checking");
			if(LoadCredentialsFromFile.hasLoginCredentials()) {
				WebConnectionManager conn=new WebConnectionManager.Builder(LoadCredentialsFromFile.getGradesInfoSource()).build();
				ClassManager current=conn.fillInGrades();
				if(!current.equals(ClassManager.getSavedClasses())) {
					NotificationScreen.createNewNotificationScreen();
				}
			}
			//if it does not, we are done here
			return;
		}
		JFrame loadingScreen=new JFrame();
		loadingScreen.setContentPane(new LoadingScreen());
		loadingScreen.setSize(loadingScreenSize, loadingScreenSize);
		loadingScreen.setLocation(200,200);//getCenterOffset(loadingScreenSize/2, loadingScreenSize/2));
        loadingScreen.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        loadingScreen.setUndecorated(true);
		loadingScreen.setTitle("Grades Monitor is loading...");
		loadingScreen.setVisible(true);
		
		//load into memory
		Loader loader=new Loader();
		new Thread(loader).start();
		while(!loader.isLoaded()) {
			try {
				synchronized(lock) {
					lock.wait();
				}
			} catch (InterruptedException e) {}
		}
		DebugLog.logStatement("Main has resumed. (concurrency)", DebugLog.CONCURRENCY_LOG_CODE);
		JFrame frame=new JFrame();
		frame.setContentPane(new MainScreen());
		frame.setSize(frameSize, frameSize);
		frame.setLocation(200,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Grades Monitor");
		frame.setVisible(true);
		loadingScreen.setVisible(false);
		loadingScreen.dispose();
	}
	
	@SuppressWarnings("unused")
	/**
	 * Gets the x and y coordinates needed to put the window in the center
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
	
	//Constants that can be tweaked to alter behavior
	private static int loadingScreenSize=200;
	private static int frameSize=500;
}
