package de.szut.client.logic;

import java.io.IOException;

import de.szut.client.grafik.Loader;
import de.szut.server.logic.Logging;
import de.szut.server.logic.Server;

/**
 * 
 * @author Alexander, Simeon, Jan
 * Version 1.0
 * 02.03.2016
 */
public class START {

	static boolean server = false;
	
	public static void main(String[] args) {
		// Server wird gestartet
		if (server) {
			try {
				new Thread(new Server(3142)).start();
				Logging.loggingOn = true;
			} catch (IOException e) {
			}
		}
		// Client Lader wird gestartet
		Loader ld = new Loader();
		// Client wird gestartet
		ld.print();
	}
}
