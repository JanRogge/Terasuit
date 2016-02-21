package connection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import server.GameServer;

/**
 * 
 * @author Simeon
 *
 */
public class GameAnalyser implements Analyser {

	private GameServer server;
	private short id;
	private byte position;

	public GameAnalyser(GameServer server, short id, byte position) {
		this.server = server;
		this.id = id;
		this.position = position;
	}

	/**
	 * Analysiert die Nachricht als Ingame Nachricht
	 * 
	 * @param input
	 */
	public void analyse(byte[] input) {
		switch (input[0]) {
		case (32): // Geb�ude (aus)bauen
			System.out.println(input.length);
			if (input.length == 3) {
				System.out.println(input[1] +"aja");
				if (input[2] < 127) {
					System.out.println(input[2]);
					server.build(position, input[1], input[2]);
				} else {
					server.destroyBuilding(input[1], position);
				}
			}
			break;
		case (33): // Einheit erstellen
			if (input.length == 3) {
				System.out.println("Me Smash" + input[1] + " " + input[2]);
				byte unitID = input[1];
				byte buildingPlace = input[2];
				server.createUnit(position, unitID, buildingPlace);
			}
			break;
		case (34): // Einheit bewegen
			if (input.length > 2) {
				server.moveUnits(id, getUnits(input), ((input[2] & 2) >> 1)
						* Double.compare(input[1] & 2, 0.5),
						(input[2] & 1) == 1);
			}
			break;
		case (35): // Spiel verlassen
			System.out.println("leave");
			server.disconnect(id);
			break;

		case (36): // Chat
			server.broadcast(castToString(input).substring(1), id);
			break;
		}
	}

	/**
	 * Entnimmt einer bytefolge die enthaltenen Einheiten
	 * 
	 * @param input
	 * @return
	 */
	private int[] getUnits(byte[] input) {
		byte[] bytes1 = new byte[input.length - 1];
		for (int i = 2; i <= input.length; i++) {
			bytes1[i - 2] = input[i];
		}
		IntBuffer intBuffer = ByteBuffer.wrap(bytes1)
				.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
		int[] array = new int[intBuffer.remaining()];
		return intBuffer.get(array).array();
	}

	private String castToString(byte[] input) {
		String s = "";
		for (byte i : input) {
			s += (char) i;
		}
		return s;
	}
}