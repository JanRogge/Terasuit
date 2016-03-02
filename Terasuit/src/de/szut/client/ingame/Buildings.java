package de.szut.client.ingame;

import javax.swing.JLabel;

/**
 * Hier draus werden Objekte f�r Geb�ude erzeugt. Alle Geb�ude besitzen die
 * unten angegebenen Wert
 * 
 * @author Alexander
 */
public class Buildings {

	int building_Live = 0;
	int type = 0;
	int number = 0;
	int slotID = 0;
	int x = 0;
	int y = 0;
	boolean attackabel = false;
	JLabel label = new JLabel("");
	String description = "";
	String name = "";
	String[] spwanableEntity;
	Buildings primerBuilding = null;
	private int[] price;

	/**
	 * Gibt die X-Koordinate des Geb�udes zur�ck
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setzt die X-Koordinate
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gibt die Y-Koordinate des geb�udes zur�ck
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setzt die Y-Koordinate des Geb�udes
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gibt das Untergeb�ude zur�ck
	 * 
	 * @return
	 */
	public Buildings getPrimerBuilding() {
		return primerBuilding;
	}

	/**
	 * Setzt das Untergeb�ude
	 * 
	 * @param primerBuilding
	 */
	public void setPrimerBuilding(Buildings primerBuilding) {
		this.primerBuilding = primerBuilding;
	}

	/**
	 * Gibt die Liste der Spawnbaren Einheiten zur�ck
	 * 
	 * @return
	 */
	public String[] getSpwanableEntity() {
		return spwanableEntity;
	}

	/**
	 * Setzt die Liste der Spawnbaren Einheiten
	 * 
	 * @param spwanableEntity
	 */
	public void setSpwanableEntity(String[] spwanableEntity) {
		this.spwanableEntity = spwanableEntity;
	}

	/**
	 * Gibt den Namen zur�ck
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen des Geb�udes
	 * 
	 * @param name
	 *            neuer Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Beschreibung des Geb�udes zur�ck
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setzt die Beschreibung
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gibt die Nummer des Geb�udes zur�ck
	 * 
	 * @return
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Setzt die Nummer des Geb�udes
	 * 
	 * @return
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Gibt das Label des Geb�udes zur�ck
	 * 
	 * @return
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * Setzt das Label des Geb�udes
	 * 
	 * @param label
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}

	/**
	 * Gibt die Typnummer des Geb�udes zur�ck
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * Setzt die Typnummer des Geb�udes
	 * 
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Gibt die SlotID des Geb�udes zur�ck
	 * 
	 * @return
	 */
	public int getSlotID() {
		return slotID;
	}

	/**
	 * Setzt die SlotID
	 * 
	 * @return
	 */
	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	/**
	 * Setzt den Preis des Geb�udes
	 * 
	 * @param price
	 */
	public void setPrice(int[] price) {
		this.price = price;
	}

	/**
	 * Gibt den Preis des Geb�ubes zur�ck
	 * 
	 * @return
	 */
	public int[] getPrice() {
		return price;
	}
}
