package de.szut.server.logic;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			new Thread(new Server(3142)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
