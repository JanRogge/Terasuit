package de.szut.server.connection;

/**
 * 
 * @author Simeon
 *
 */
public interface Analyser {

	/**
	 * Analysiert eine Nachricht f�r den Server
	 * 
	 * @param input
	 *            Nachricht
	 */
	public void analyse(byte[] bs);
}
