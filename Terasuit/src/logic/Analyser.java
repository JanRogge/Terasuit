package logic;

import grafig.Loader;

import java.awt.Point;
import java.util.ArrayList;

public class Analyser {

	private Loader loader;
	private State state;
	private GameLobby game;

	private boolean isHost;
	private byte position;

	public Analyser(Loader loader) {
		state = State.MENU;
		this.loader = loader;
	}

	public void analyse(byte[] bs) {
		switch (state) {
		case MENU:
			analyseMenuMessage(bs);
			break;
		case LOBBY:
			analyseLobbyMessage(bs);
			break;
		case GAME:
			analyseGameMessage(bs);
			break;
		}
	}

	public void switchState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	private void analyseMenuMessage(byte[] message) {
		switch (message[0]) {
		case (0): // Stats
			break;
		case (1): // Get GameList
			ArrayList<Lobby> list = new ArrayList<Lobby>();
			byte[][] splittedMessage = getSplitString(message, 1);
			for (byte[] s : splittedMessage) {
				if (s.length != 0) {
					System.out.println((byte) castToString(s).charAt(0)
							+ (byte) castToString(s).charAt(1) + "are");
					list.add(new Lobby(s[0], castToString(s).substring(3),
							s[1], ((s[2] & 8) >> 3) == 1, (byte) (s[2] & 7)));
				}
			}
			loader.updateLobbyList(list.toArray(new Lobby[list.size()]));
			break;
		case (2): // Join Game
			isHost = (message[2] & 4) > 0;
			position = (byte) (message[2] & 3);
			splittedMessage = getSplitString(message, 3);
			String[] names = new String[splittedMessage.length];
			for (int i = 0; i < splittedMessage.length; i++) {
				if (splittedMessage[i].length > 1) {
					names[i] = castToString(splittedMessage[i]);
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
			loader.connection.setLoggedIn(true);
			loader.connection.setName(castToString(message).substring(1));
			loader.loggIn(loader.connection.getName());
			break;
		}
	}

	private void analyseLobbyMessage(byte[] bs) {
		switch (bs[0]) {
		case (16): // Position Wechseln
			if (bs.length == 4) {
				position = bs[3];
			}
			game.switchPlayers(bs[1], bs[2]);
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (17): // Spieler tritt dem Spiel bei
			System.out.println("hehehe");
			game.addPlayer(bs[1], castToString(bs).substring(2));
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (18): // Spieler verl�sst das Spiel
			System.out.println(bs.length + "akljfla");
			if (bs.length == 2) {
				game.removePlayer(bs[1]);
				loader.updatePlayerList(game.getPlayerNames(), isHost);
			} else {
				switchState(State.MENU);
				loader.switchPanel(loader.Lobbypage);
				loader.connection.refreshServerList(false, "", 0, 4, 255);
				game = null;
				// (Spieler wurde aus dem Spiel entfernt)
			}
			break;
		case (19): // Spieler wird Host
			loader.switchPanel(loader.Grouppage_owner);
			isHost = true;
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (20): // Spiel wird gestartet
			switchState(State.GAME);
			loader.switchPanel(loader.Gamepage);
			// TODO: An Feldmann: Hier Funktionsaufruf Spiel starten
			break;
		case (21):
			loader.setText(game.getPlayerName(bs[1]) + ": "
					+ castToString(bs).substring(2));
			break;
		}
	}

	private void analyseGameMessage(byte[] bs) {
		System.out.println();
		switch (bs[0]) {
		case (32): // Spieler erstellt oder verbessert ein geb�ude ein Geb�ude
			byte playerNumber = bs[1];
			byte buildingPosition = (byte) (bs[2] - 1);
			byte id = bs[3];
			String buildingName = null;
			switch (id) {
			case (1):
				buildingName = "Outpost";
				break;
			case (2):
				buildingName = "Barracks";
				break;
			case (3):
				buildingName = "Arsenal";
				break;
			case (4):
				buildingName = "Forge";
				break;
			case (5):
				buildingName = "Manufactory";
				break;
			case (6):
				buildingName = "Mechanics Terminal";
				break;
			case (7):
				buildingName = "Hospital";
				break;
			case (8):
				buildingName = "War Sanctum";
				break;
			case (9):
				buildingName = "Bank";
				break;
			case (10):
				buildingName = "Treasury";
				break;
			case (11):
				buildingName = "Armory";
				break;
			case (12):
				buildingName = "Generator";
				break;
			case (13):
				buildingName = "Solar Grid";
				break;
			case (14):
				buildingName = "Special Operations";
				break;
			}
			if (buildingName != null) {
				if (playerNumber == position) {
					loader.game.createBuilding(buildingName, "Buildings/"
							+ buildingName + ".png", (playerNumber << 2)
							+ (playerNumber >> 1) + buildingPosition + 1,
							(playerNumber << 2) + (playerNumber >> 1)
									+ buildingPosition + 19);
				} else {
					loader.game.createEnemyBuilding(buildingName, "Buildings/"
							+ buildingName + ".png", (playerNumber << 2)
							+ (playerNumber >> 1) + buildingPosition + 1,
							(playerNumber << 2) + (playerNumber >> 1)
									+ buildingPosition + 19);
				}
			}
			break;
		case (33): // Ein eigenes Geb�ude startet eine Produktion
			id = bs[1];
			buildingPosition = bs[2];
			// TODO: An Feldmann: Hier Einheitenproduktion starten
			break;
		case (34): // Spieler erstellt eine Einheit
			Point position = new Point((bs[2] << 8) + bs[3], (bs[4] << 8)
					+ bs[5]);
			short unitID = (short) (bs[7] << 8 + bs[8]);
			String name = "";
			boolean flying = false;
			switch (bs[6]) {
			case (1):
				name = "Marine";
				flying = false;
			}
			loader.game.entity("Unit/Ground/" + name + ".png", bs[1] + 1,
					flying, unitID, position);
			break;
		case (35): // Spieler bewegt eine Einheit
			playerNumber = bs[1];
			byte direction = bs[2];
			short[] unitIDs = new short[(bs.length - 2) / 2];
			for (int i = 3; i < bs.length; i += 2) {
				unitIDs[(i - 2) / 2] = (short) (bs[i] << 8 + bs[i + 1]);
			}
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit bewegen
			break;
		case (36): // Einheit beginnt schie�en
			break;
		case (37): // Einheit stirbt
			break;
		case (38): // Spieler verl�sst das Spiel
			playerNumber = bs[1];
			// TODO: An Feldmann: Hier Funktionsaufruf Spieler verl�sst anzeigen
			break;
		case (39): // Spiel gewonnen/verloren
			boolean won = bs[1] > 0;
			// TODO: An Feldmann: Hier Funktionsaufruf Sieg/Niederlage
			break;
		case (21):
			loader.setGameText(game.getPlayerName(bs[1]) + ": "
					+ castToString(bs).substring(2));
			break;
		}
	}

	private String castToString(byte[] message) {
		String s = "";
		for (byte i : message) {
			s += (char) i;
		}
		return s;
	}

	private byte[][] getSplitString(byte[] input, int bytesToCut) {
		ArrayList<byte[]> outerArray = new ArrayList<byte[]>();
		ArrayList<Byte> array = new ArrayList<Byte>();
		for (int i = bytesToCut; i < input.length; i++) {
			if (input[i] == 1) {
				outerArray.add(toPrimal(array.toArray(new Byte[array.size()])));
				array.clear();
			} else {
				array.add(input[i]);
			}
		}
		outerArray.add(toPrimal(array.toArray(new Byte[array.size()])));
		return outerArray.toArray(new byte[outerArray.size()][]);
	}

	private byte[] toPrimal(Byte[] splitted) {
		byte[] bytes = new byte[splitted.length];
		for (int i = 0; i < splitted.length; i++) {
			bytes[i] = splitted[i];
		}
		return bytes;
	}
}
