package web;

import java.awt.event.ActionEvent;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.util.Scanner;

import javax.swing.Timer;

import test.DebugLog;

/**
 * Automatically connects to 
 * 
 * @author Jarred
 *
 */
public class FJUHSDAutoConnector implements AutoConnector {
	
	private Timer myTimer;
	
	private String sessionCookie;
	
	public FJUHSDAutoConnector(String cookie) {
		myTimer=new Timer(120000, this);
		sessionCookie=cookie;
	}

	@Override
	/**
	 * @inheritDoc
	 */
	public void start() {
		myTimer.start();
	}
	
	@Override
	/**
	 * @inheritDoc
	 */
	public void stop() {
		myTimer.stop();
	}
	
	@Override
	/**
	 * @inheritDoc
	 */
	public void actionPerformed(ActionEvent e) {
		reconnect();
	}
	
	public void reconnect() {
		try {
			URLConnection grades=new URL(FJUHSDAeriesConnectionManager.aeriesGradesURL).openConnection();
			//TODO Add the cookie to the connection
			grades.connect();
			//just go through the data to be cautious
			Scanner input=new Scanner(grades.getInputStream());
			while(input.hasNextLine()) {
				input.nextLine();
			}
			input.close();
		} catch (IOException e) {
			DebugLog.logStatement("Failed connecting to Aeries", DebugLog.FAILURE_LOG_CODE);
			System.exit(1);
		}
		// TODO Connect to Aeries just to not make it think you idled
	}
	

	@Override
	/**
	 * @inheritDoc
	 */
	protected void finalize() throws Throwable {
		stop();
		super.finalize();
	}
}
