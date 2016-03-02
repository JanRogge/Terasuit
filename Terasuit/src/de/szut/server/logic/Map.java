package de.szut.server.logic;

/**
 * 
 * @author Simeon
 *
 */
public enum Map {
	
	Nightsun ((byte) 2);
	
	private byte id;
	
	/**
	 * Initialisiert eine neue Map
	 * @param id
	 */
	Map(byte id) {
		this.id = id;
	}
	
	/**
	 * Gibt die ID zur�ck
	 * @return
	 */
	public byte getID() {
		return id;
	}
}
