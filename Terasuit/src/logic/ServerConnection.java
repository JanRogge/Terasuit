package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerConnection {
	
	public boolean ServerAccess = true;
	
	public void init() {
		new ServerConnection();
	}
	
	public ServerConnection() {
		Socket socket;
		try {
			socket = new Socket("localhost", 1337);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String answer = input.readLine();
			JOptionPane.showMessageDialog(null, answer);
			this.setServerAccess(true);
		}
		catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
// #################################################################
	public void sendInformation(){
		// Liste der Updates wird gesendet
	}
	
	public boolean createGroup(){
		// Guppe erstellen
		// Sendet erschaffung zur�ck
		return true;
	}
	
	public boolean connectGroup(){
		// Versucht der Gruppe zu joinen
		// Sendet erfolgreiche Verbindung zur�ck oder auch nicht
		return true;
	}
	
	public boolean returnLobby(){
		// Verl�sst die aktuelle Gruppe
		// Sendet erfolgreiche verlassen der Gruppe
		return true;
	}
	
	public boolean closeLobby(){
		// Verl�sst die aktuelle Gruppe
		// Sendet erfolgreiche schlie�en der Gruppe
		return true;
	}

	public void close() {
		// Schlie�st die Verbindung
	}
	
	public boolean startGame(){
		// Startet f�r alle in der Gruppe das Spiel
		return true;
	}
	
	public boolean returnToLobbyFromGame(){
		// Gibt zur�ck ob das verlassen vom Server akzeptiert worden ist
		return true;
	}
	
	public void refreshServerList(){
		// Aktuellisiert die Listen
		// RETURN!!!
	}

	public boolean isServerAccess() {
		return ServerAccess;
	}

	public void setServerAccess(boolean serverAccess) {
		ServerAccess = serverAccess;
	}

}