package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerConnection implements Runnable {

	private boolean ServerAccess = true;
	private ConcurrentLinkedQueue<String> queue;
	private PrintStream writer;
	private BufferedReader reader;
	private Analyser analyser;

	public ServerConnection() {
		Socket socket;
		try {
			socket = new Socket("localhost", 3142);
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.writer = new PrintStream(socket.getOutputStream(), true);
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("Server Not Reachable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		queue = new ConcurrentLinkedQueue<String>();

		analyser = new Analyser();
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (reader.ready()) {
					String in = reader.readLine();
					analyser.analyse(in);
				}
				if (!queue.isEmpty()) {
					writer.println(queue.remove());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addMessage(String message) {
		queue.add(message);
	}

	// #################################################################
	// Men�

	/**
	 * Loggt den aktuell eingeloggten Spieler aus
	 */
	public void logout() {
		if (analyser.getState() == State.MENU) {
			addMessage(String.valueOf((char) 32));
		}
	}

	/**
	 * Fordert eine aktuelle Liste der Spiel-Server mit den �bergebenen
	 * Filteroptionen an.
	 * 
	 * @param noPassword
	 *            true: nur Spiele ohne Passwort; false: egal
	 * @param minPlayers
	 *            Minimale Anzahl an Spielern wird im Server =max gesetzt wenn
	 *            gr��er als max
	 * @param maxPlayers
	 *            Maximale Anzahl an Spielern
	 * @param mapID
	 *            Die ID der gew�nschten Map; 0 wenn keine spezielle Map
	 *            gew�nscht
	 */
	public void refreshServerList(boolean noPassword, byte minPlayers,
			byte maxPlayers, byte mapID) {
		if (analyser.getState() == State.MENU) {
			addMessage(String.valueOf((char) (64 + Boolean.compare(noPassword,
					false) << 4) + (minPlayers << 2) + maxPlayers)
					+ mapID);
		}
	}

	/**
	 * Erstellt eine neue Spiellobby und tritt dieser als Host bei
	 * 
	 * @param mapID
	 *            ID der gew�hlten Map
	 * @param name
	 *            Name des Spiels
	 * @param password
	 *            Passwort des Spiels
	 */
	public void createGroup(byte mapID, String name, String password) {
		if (analyser.getState() == State.MENU) {
			queue.clear();
			addMessage((char) 96 + mapID + name + "," + password);
			return;
		}
	}

	/**
	 * Tritt dem Spiel mit der �bergebenen ID bei
	 * 
	 * @param id
	 *            ID des gew�nschten Spiels
	 * @param password
	 *            Passwort des gew�nschten Spiels
	 */
	public void connectGroup(byte id, String password) {
		if (analyser.getState() == State.MENU) {
			queue.clear();
			addMessage(String.valueOf((char) 128 + id));
			return;
		}
	}

	/**
	 * Loggt einen Account in das Spiel ein
	 * 
	 * @param user
	 *            Name des Accounts
	 * @param password
	 *            Passwort des Accounts
	 */
	public void login(String user, char[] password) {
		if (analyser.getState() == State.MENU) {
			addMessage((char) 160 + user + password.toString());
		}
	}

	/**
	 * Registriert einen neuen Account mit den �bergebenen Daten
	 * 
	 * @param user
	 *            Name des Accounts
	 * @param password
	 *            Passwort des Accounts
	 * @param mail
	 *            E-Mail Addresse des Accounts
	 */
	public void register(String user, char[] password, String mail) {
		if (analyser.getState() == State.MENU) {
			addMessage((char) 192 + user + "," + password.toString() + ","
					+ mail + ",Admin");
		}
	}

	/**
	 * Beendet die Verbindung zum Server
	 * 
	 * nur aufrufen wenn das Spiel geschlossen wird
	 */
	public void close() {
		if (analyser.getState() == State.GAME) {
			queue.clear();
			queue.add(String.valueOf((char) 224));
		}
	}

	// #################################################################
	// Lobby

	/**
	 * wechselt die Position in der aktuellen Lobby
	 * 
	 * @param newPosition
	 *            gew�nschte Position nur Zahlen 0-3
	 */
	public void switchPosition(byte newPosition) {
		if (analyser.getState() == State.LOBBY) {
			if (newPosition < 4) {
				addMessage(String.valueOf((char) newPosition));
			}
		}
	}

	/**
	 * Verl�sst die aktuelle Lobby und kehrt ins Men� zur�ck
	 */
	public void returnFromLobby() {
		if (analyser.getState() == State.LOBBY) {
			queue.clear();
			addMessage(String.valueOf((char) 64));
		}
	}

	/**
	 * Entfernt einen Spieler aus der Lobby (Ben�tigt Hostrechte)
	 * 
	 * @param playerNumber
	 *            Die Nummer des Spielers
	 */
	public void kickPlayer(byte playerNumber) {
		if (analyser.getState() == State.LOBBY) {
			addMessage(String.valueOf((char) 128) + playerNumber);
			// TODO: Playernumber maybe verschieben
		}
	}

	/**
	 * Startet ein Spiel mit der aktuellen Lobby (Ben�tigt Hostrechte)
	 */
	public void startGame() {
		if (analyser.getState() == State.LOBBY) {
			queue.clear();
			addMessage(String.valueOf((char) 192));
		}
	}

	public void sendLobbyChatMessage() {

	}

	// #################################################################
	// Game

	/**
	 * Erstellt ein erw�nschtes Geb�ude an der gew�nschten Position
	 *
	 * @param position
	 *            Position des Geb�udes
	 * @param buildingType
	 *            Typ des Geb�udes
	 */
	public void createBuilding(byte position, String buildingType) {
		byte buildingID = -2;
		switch (buildingType) {
		case ("Barracks"):
			buildingID = 0;
			break;
		case ("Forge"):
			buildingID = 8;
			break;
		case ("Armory"):
			buildingID = 16;
			break;
		case ("Hospital"):
			buildingID = 24;
			break;
		case ("Outpost"):
			buildingID = 32;
			break;
		case ("Bank"):
			buildingID = 40;
			break;
		case ("Generator"):
			buildingID = 48;
			break;
		case ("Special Operations"):
			buildingID = 52;
			break;
		case ("Destroy"):
			buildingID = -1;
			break;
		}
		if (analyser.getState() == State.GAME && buildingID != -2) {
			if ((position & 252) == 0) {
				addMessage(String.valueOf((char) (32 + position)) + buildingID);
			}
		}
	}

	/**
	 * Verbessert ein ausgew�hltes Geb�ude
	 *
	 * @param position
	 *            Position des Geb�udes
	 */
	public void upgradeBuilding(byte position) {
		if (analyser.getState() == State.GAME) {
			if ((position & 252) == 0) {
				addMessage(String.valueOf((char) (32 + position)));
			}
		}
	}

	/**
	 * Zerst�rt ein ausgew�hltes Geb�ude
	 * 
	 * @param position
	 *            : Position des Geb�udes
	 */
	public void destroyBuilding(byte position) {
		if (analyser.getState() == State.GAME) {
			if ((position & 252) == 0) {
				addMessage(String.valueOf((char) (32 + 4 + position)));
			}
		}
	}

	/**
	 * Erstellt eine Einheit
	 * 
	 * @param id
	 *            ID der Einheit
	 */
	public void createUnit(byte id) {
		if (analyser.getState() == State.GAME) {
			addMessage(String.valueOf((char) 64) + id);
		}
	}

	/**
	 * Bewegt die Einheiten in eine gew�nschte richtung
	 * 
	 * @param unitID
	 *            IDs der Einheiten
	 * @param right
	 *            L�uft rechts
	 * @param walking
	 *            l�uft �berhaupt
	 */
	public void moveUnit(short[] unitID, boolean right, boolean walking) {
		if (analyser.getState() == State.GAME) {
			addMessage(String.valueOf((char) (96 + (Boolean.compare(false,
					right) << 2) + (Boolean.compare(false, walking) << 1)))
					+ unitID);
		}
	}

	/**
	 * Verl�sst ein laufendes Spiel und kehrt zum Men� zur�ck
	 */
	public void leaveGame() {
		if (analyser.getState() == State.GAME) {
			addMessage(String.valueOf((char) 128));
		}
	}

	/**
	 * Sendet eine Chatnachricht an den Server
	 * 
	 * @param message
	 *            Chatnachricht
	 */
	public void sendChatMessage(String message) {
		if (!message.contains("")) {
			addMessage((char) 192 + message);
		}
	}

	public boolean isServerAccess() {
		return ServerAccess;
	}

	public void setServerAccess(boolean serverAccess) {
		ServerAccess = serverAccess;
	}
}