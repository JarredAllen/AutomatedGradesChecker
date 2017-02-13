/**
 * 
 */
package web;

import java.awt.event.ActionListener;

/**
 * An interface that would be used to automatically send queries to the server if needed to stop a timeout
 * 
 * @author Jarred
 * @since 2/12/2017
 * @version 2/12/2017
 */
public interface AutoConnector extends ActionListener {
	/**
	 * Begin the occasional autoconnecting to the web server
	 */
	void start();
	
	/**
	 * End the occasional autoconnecting to the web server
	 */
	void stop();
}
