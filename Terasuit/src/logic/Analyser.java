package logic;

import grafig.Loader;

import java.util.ArrayList;

public class Analyser {

	private Loader loader;
	private State state;
	private GameLobby game;
	
	private boolean isHost;

	public Analyser(Loader loader) {
		state = State.MENU;
		this.loader = loader;
	}

	public void analyse(String message) {
		switch (state) {
		case MENU:
			analyseMenuMessage(message);
			break;
		case LOBBY:
			analyseLobbyMessage(message);
			break;
		case GAME:
			analyseGameMessage(message);
			break;
		}
	}

	public void switchState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	private void analyseMenuMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (0): // Stats
			break;
		case (1): // Get GameList
			ArrayList<Lobby> list = new ArrayList<Lobby>();
			String[] splittedMessage = message.substring(1).split(",");
			for (String s : splittedMessage) {
				if (s.length() != 0) {
					byte[] b = s.getBytes();
					list.add(new Lobby(b[0], s.substring(3), b[1],
							((b[2] & 8) >> 3) == 1, (byte) (b[2] & 7)));
				}
			}
			loader.updateLobbyList(list.toArray(new Lobby[list.size()]));
			break;
		case (2): // Join Game
			isHost = bytes[2] > 0;
			splittedMessage = message.substring(3).split(",");
			String[] names = new String[splittedMessage.length];
			for (int i = 0; i < splittedMessage.length; i++) {
				if (splittedMessage[i].length() > 1) {
					System.out.println((byte) splittedMessage[i].charAt(0) + "�");
					names[i] = splittedMessage[i];
				} else {
					names[i] = "";
				}
			}
			if (isHost) {
				state = State.LOBBY;
				loader.switchPanel(loader.Grouppage_owner);
			} else {
				state = State.LOBBY;
				loader.switchPanel(loader.Grouppage);
			}
			game = new GameLobby(names);
			loader.updatePlayerList(names, isHost);
			break;
		case (3): // Log in
			System.out.println("success");
			// TODO: An Feldmann: Hier Funktionsaufruf zum Anzeige �ndern (Login
			// disabeln Logout enabeln)
			break;
		}
	}

	private void analyseLobbyMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (16): // Position Wechseln
			game.switchPlayers(bytes[1], bytes[2]);
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (17): // Spieler tritt dem Spiel bei
			System.out.println("hehehe");
			game.addPlayer(bytes[1], message.substring(2));
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (18): // Spieler verl�sst das Spiel
			if (bytes.length != 1) {
				game.removePlayer(bytes[1]);
				loader.updatePlayerList(game.getPlayerNames(), isHost);
			} else {
				switchState(State.MENU);
				loader.switchPanel(loader.Lobbypage);
				loader.connection.refreshServerList(false, "", 0, 4, 0);
				game = null;
				// (Spieler wurde aus dem Spiel entfernt)
			}
			break;
		case (19): // Spiel wird gestartet
			switchState(State.GAME);
			loader.switchPanel(loader.Gamepage);
			// TODO: An Feldmann: Hier Funktionsaufruf Spiel starten
			break;
		case (20):
			loader.setText(game.getPlayerName(bytes[1]) + ": " + message.substring(2));
			break;
		}
	}

	private void analyseGameMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (32): // Spieler erstellt oder verbessert ein geb�ude ein Geb�ude
			byte playerNumber = bytes[1];
			byte buildingPosition = bytes[2];
			byte id = bytes[3];
			// TODO: An Feldmann: Hier Funktionsaufruf Geb�ude bauen (id127 ist upgrade)
			break;
		case (33): // Ein eigenes Geb�ude startet eine Produktion
			id = bytes[1];
			buildingPosition = bytes[2];
			//TODO: An Feldmann: Hier Einheitenproduktion starten
			break;
		case (34): // Spieler erstellt eine Einheit
			playerNumber = bytes[1];
			short unitPosition = (short) (bytes[2] << 8 + bytes[3]);
			id = bytes[4];
			short unitID = (short) (bytes[5] << 8 + bytes[6]);
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit erstellen
			break;
		case (35): // Spieler bewegt eine Einheit
			playerNumber = bytes[1];
			byte direction = bytes[2];
			short[] unitIDs = new short[(bytes.length - 2) / 2];
			for (int i = 3; i < bytes.length; i += 2) {
				unitIDs[(i - 2) / 2] = (short) (bytes[i] << 8 + bytes[i + 1]);
			}
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit bewegen
			break;
		case (36): // Einheit beginnt schie�en
			break;
		case (37): // Einheit stirbt
			break;
		case (38): // Spieler verl�sst das Spiel
			playerNumber = bytes[1];
			// TODO: An Feldmann: Hier Funktionsaufruf Spieler verl�sst anzeigen
			break;
		case (39): // Spiel gewonnen/verloren
			boolean won = bytes[1] > 0;
			// TODO: An Feldmann: Hier Funktionsaufruf Sieg/Niederlage
			break;
		case (20):
			String msg = message.substring(3);
			loader.setGameText(msg);
			break;
		}
	}
}
