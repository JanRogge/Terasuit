package de.szut.client.ingame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.szut.client.grafik.Loader;
import de.szut.client.grafik.Panel;

/**
 * 
 * @author Alexander
 *
 */
public class BaseBuildings {

	public String red = "Buildings/base_red.png";
	public String blue = "Buildings/base_blue.png";
	public String grun = "Buildings/base_gr�n.png";
	public String gelb = "Buildings/base_gelb.png";
	public String base = "Buildings/base.png";
	public String barracks = "Buildings/barracks.png";
	public int default_position_Leftside_x = 40;
	public int default_position_Leftside_y = 380;
	public int default_position_Rightside_x = 1350;
	public int default_position_Rightside_y = 380;
	int default_ender = 200;
	int default_interval = 45;
	Buildings building = new Buildings();
	JLabel label = new JLabel("");

	/**
	 * Funktions aufrufe, um alle Startgeb�ude zu platzieren (Slots und
	 * Rath�user)
	 * 
	 * @param field
	 *            Spielfeld auf dem die Eiheiten laufen
	 * @param game
	 *            Gameobjekt des laufenden Spiels
	 * @param buildingsArray
	 *            Geb�udeliste des Spiels
	 * @param load
	 *            Loader des Clients
	 * @param func
	 *            Funcobjekt des Clients
	 * @param FirstColor
	 *            Bildname des Slots des ersten Spielers
	 * @param SecColor
	 *            Bildname des Slots des zweiten Spielers
	 * @param default_position_X
	 *            kleinster xWert derBase
	 * @param default_position_Ykleinster
	 *            yWert derBase
	 * @param leftSide
	 *            ob die Base links ist
	 */
	public void buildBase(Panel field, Game game, Buildings[] buildingsArray,
			Loader load, Funktions func, String FirstColor, String SecColor,
			int default_position_X, int default_position_Y, boolean leftSide) {

		// Unterspielerfelder
		createEntity(field, load, func, game, buildingsArray, FirstColor,
				"Slot", default_position_X, default_position_Y, leftSide, 1);
		createEntity(field, load, func, game, buildingsArray, FirstColor,
				"Slot", default_position_X + default_interval,
				default_position_Y + default_interval, leftSide, 2);
		createEntity(field, load, func, game, buildingsArray, FirstColor,
				"Slot", default_position_X + default_interval * 2,
				default_position_Y + default_interval * 2, leftSide, 3);
		createEntity(field, load, func, game, buildingsArray, FirstColor,
				"Slot", default_position_X + default_interval * 3,
				default_position_Y + default_interval, leftSide, 4);
		// Oberespielerfelder
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot",
				default_position_X + default_interval, default_position_Y
						- default_interval, leftSide, 5);
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot",
				default_position_X + default_interval * 2, default_position_Y
						- default_interval * 2, leftSide, 6);
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot",
				default_position_X + default_interval * 3, default_position_Y
						- default_interval, leftSide, 7);
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot",
				default_position_X + default_interval * 4, default_position_Y,
				leftSide, 8);
		// Rathaus
		createMainBuilding(field, load, func, game, buildingsArray, base,
				"Base", default_position_X + default_interval * 2,
				default_position_Y, leftSide, 9);
	}

	/**
	 * Generiert alle m�glichen Hauptgeb�ude, die am anfang ben�tigt werden
	 * 
	 * @param field
	 *            Spielfeld auf dem die Eiheiten laufen
	 * @param loader
	 *            Loader des Clients
	 * @param func
	 *            Funcobjekt des Clients
	 * @param game
	 *            Gameobjekt des laufenden Spiels
	 * @param buildingsArray
	 *            Geb�udeliste des Spiels
	 * @param pictureName
	 *            Bildname des Slots
	 * @param EntityName
	 *            Name des Typs
	 * @param X
	 *            X-Position des Geb�udes
	 * @param Y
	 *            Y-Position des Geb�udes
	 * @param leftSide
	 *            ob das Geb�ude links ist
	 * @param ID
	 *            ID des Geb�udes
	 */
	private void createMainBuilding(Panel field, Loader loader, Funktions func,
			Game game, Buildings[] buildingsArray, String pictureName,
			String EntityName, int X, int Y, boolean leftSide, int ID) {
		building = new MainBuilding();

		// Bild wird geladen
		ImageIcon pic = new ImageIcon(pictureName);
		label = new JLabel("");
		label.setIcon(pic);
		// Position wird festgelegt
		label.setBounds(X, Y, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {
				for (int i = 0; i < buildingsArray.length; i++) {
					if (buildingsArray[i] != null) {
						if (buildingsArray[i].getLabel() == objUnit.getSource()) {
							// Slot ID
							int slotID = i;
							// ID des Geb�udes, in diesem Fall bei Start 0
							int primID = 0;
							game.createUserOptions(slotID, primID,
									buildingsArray);
						}
					}
				}
			}
		});
		building.setX(X);
		building.setY(Y);
		building.setLabel(label);
		building.setDescription("A underground construction site");
		building.setName(EntityName);
		building.setSpwanableEntity(searchForPossibleEntitys(EntityName));
		// ver�nderung der ID, wenn es f�r die andere Seite platziert wird
		if (leftSide) {
			building.setNumber(ID);
			buildingsArray[ID] = building;
		} else {
			ID = ID + 9;
			building.setNumber(ID);
			buildingsArray[ID] = building;
		}
		field.add(label);
		field.repaint();
	}

	/**
	 * L�d alle Slot auf dem Spielfeld und gibt ihnen Verweise auf m�gliche
	 * Aktionen
	 * 
	 * @param field
	 *            Spielfeld auf dem die Eiheiten laufen
	 * @param loader
	 *            Loader des Clients
	 * @param func
	 *            Funcobjekt des Clients
	 * @param game
	 *            Gameobjekt des laufenden Spiels
	 * @param buildingsArray
	 *            Geb�udeliste des Spiels
	 * @param Entitytype
	 *            Bildname des Slots
	 * @param EntityName
	 *            Name des Typs
	 * @param X
	 *            X-Position des Geb�udes
	 * @param Y
	 *            Y-Position des Geb�udes
	 * @param leftSide
	 *            ob das Geb�ude links ist
	 * @param ID
	 *            ID des Geb�udes
	 */
	public void createEntity(Panel field, Loader loader, Funktions func,
			Game game, Buildings[] buildingsArray, String Entitytype,
			String EntityName, int X, int Y, boolean leftSide, int ID) {
		building = new Buildings();

		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(X, Y, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {

				for (int i = 0; i < buildingsArray.length; i++) {

					if (buildingsArray[i] != null) {
						if (buildingsArray[i].getLabel() == objUnit.getSource()) {
							int slotID = i;
							int primID = 0;
							game.createUserOptions(slotID, primID,
									buildingsArray);
						}
					} else {
						// Index leer!
					}
				}
			}
		});
		building.setX(X);
		building.setY(Y);
		building.setLabel(label);
		building.setDescription("Ich bin ein ganz wichtiges Geb�ude!");
		building.setName(EntityName);
		building.setSpwanableEntity(searchForPossibleEntitys(EntityName));
		if (leftSide) {
			building.setNumber(ID);
			buildingsArray[ID] = building;
		} else {
			ID = ID + 9;
			building.setNumber(ID);
			buildingsArray[ID] = building;
		}
		field.add(label);
		field.repaint();
	}

	/**
	 * Hier wird aus den angegebenden Daten ein Geb�ude mit Optionen erstellt,
	 * dieses wir auf einem Slot platziert und das Slot dar�ber informiert
	 * 
	 * @param entityLocation
	 *            Bildname des Geb�udes
	 * @param X
	 *            X-Position des Geb�udes
	 * @param Y
	 *            Y-Position des Geb�udes
	 * @param buildingsArray
	 *            Geb�udeliste des Spiels
	 * @param description
	 *            Beschreibung des Geb�udes
	 * @param buildingName
	 *            Name des Geb�udes
	 * @param game
	 *            Gameobjekt des laufenden Spiels
	 * @param slotID
	 *            SlotID des Geb�udes (1-18); 9 + 18 sind Bases
	 * @param primID
	 *            PrimID des Geb�udes (19-36); 27 + 36 sind Bases
	 * @param field
	 *            Spielfeld auf dem die Eiheiten laufen
	 * @return Buildings[] Neue Geb�udeListe
	 */
	public Buildings[] createPrimaryBuilding(String entityLocation, int X,
			int Y, Buildings[] buildingsArray, String description,
			String buildingName, Game game, int slotID, int primID, Panel field) {
		building = new Buildings();
		// Das alte Geb�ude wird gel�scht und platz f�r das neue geschaffen
		if (buildingsArray[primID] != null) {
			field.remove(buildingsArray[primID].getLabel());
			building.setPrimerBuilding(buildingsArray[primID]);
			buildingsArray[primID - 18].setPrimerBuilding(null);
		}
		// Erstellung des neuen Geb�ude
		ImageIcon pic = new ImageIcon(entityLocation);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(X, (Y - getBestOptimum(buildingName)),
				pic.getIconWidth(), pic.getIconHeight());
		building.setNumber(primID);
		building.setSlotID(slotID);
		// Neue Aktionlistener
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {
				for (int i = 0; i < buildingsArray.length; i++) {
					if (buildingsArray[i] != null) {
						if (buildingsArray[i].getLabel() == objUnit.getSource()) {
							int primID = buildingsArray[i].getNumber();
							int slotID = buildingsArray[i].getSlotID();
							// SlotID/Geb�udeID wird ausgelesen und Optionen
							// erstellt f�r den Nutzer
							game.createUserOptions(slotID, primID,
									buildingsArray);
						}
					}
				}
			}
		});
		building.setX(X);
		building.setY(Y);
		building.setLabel(label);
		building.setDescription(description);
		building.setName(buildingName);
		building.setSpwanableEntity(searchForPossibleEntitys(buildingName));
		building.setPrice(searchForPrice(buildingName));
		buildingsArray[slotID].setPrimerBuilding(building);
		buildingsArray[primID] = building;

		field.repaint();
		return buildingsArray;
	}

	/**
	 * Zerst�rt das geb�ude auf dem Slot und l�scht auch alle Verbindungen mit
	 * dem Slot
	 * 
	 * @param buildingsArray
	 *            Geb�udeliste des Spiels
	 * @param slotID
	 *            SlotID des Geb�udes (1-18); 9 + 18 sind Bases
	 * @param field
	 *            Spielfeld auf dem die Eiheiten laufen
	 */
	public void destroyPrimaryBuilding(Buildings[] buildingsArray, int slotID,
			Panel field) {
		if (slotID >= 18) {
			field.remove(buildingsArray[slotID].getLabel());
			buildingsArray[slotID] = null;
			buildingsArray[slotID - 18].setPrimerBuilding(null);
			field.repaint();
		}
	}

	/**
	 * Preislisten f�r Geb�ude
	 * 
	 * @param buildingName
	 *            Name des Geb�udes
	 * @return Preis des Geb�udes
	 */
	private int[] searchForPrice(String buildingName) {
		switch (buildingName) {
		case "Outpost":
			return new int[] { 40, 30, 30, 00 };
		case "Barracks":
			return new int[] { 30, 00, 00, 00 };
		case "Arsenal":
			return new int[] { 80, 50, 00, 00 };
		case "Forge":
			return new int[] { 40, 30, 30, 00 };
		case "Manufactory":
			return new int[] { 60, 30, 30, 00 };
		case "Mechanics Terminal":
			return new int[] { 70, 20, 00, 00 };
		case "Armory":
			return new int[] { 60, 25, 25, 00 };
		case "Generator":
			return new int[] { 50, 00, 00, 00 };
		case "Solar Grid":
			return new int[] { 50, 00, 00, 00 };
		case "Bank":
			return new int[] { 50, 00, 00, 00 };
		case "Treasury":
			return new int[] { 50, 00, 00, 00 };
		case "Hospital":
			return new int[] { 40, 30, 10, 00 };
		case "War Sanctum":
			return new int[] { 65, 00, 00, 00 };
		case "Special Operations":
			return new int[] { 80, 30, 30, 00 };
		default:
			return null;
		}
	}

	/**
	 * Gibt die Optionen zur�ck die ein Geb�ude hat
	 * 
	 * @param BuildingName
	 *            Name des Geb�udes
	 * @return Liste der Optionen
	 */
	private String[] searchForPossibleEntitys(String BuildingName) {

		// 0 Emty Field
		// 1-3 Kasserne
		// 4-6 Forge
		// 7 Armory
		// 8-9 Energy Geb�ude
		// 10-11 Geld Geb�ude
		// 12-13 Hospital
		// 14 Rocketped
		// 15 Base

		// Recruit
		switch (BuildingName) {
		case "Slot":
			return new String[] { "Outpost", "Forge", "Hospital", "Bank",
					"Armory", "Generator", "Special Operations" };
		case "Outpost":
			return new String[] { "Marine", "Chronite Tank", "Recruit",
					"Barracks", "Destroy" };
		case "Barracks":
			return new String[] { "Marine", "Chronite Tank", "Sniper",
					"Gr�ditz", "Recruit", "Arsenal", "Destroy" };
		case "Arsenal":
			return new String[] { "Marine", "Chronite Tank", "Sniper",
					"Gr�ditz", "Hover Tank", "Black Queen", "Recruit",
					"Destroy" };
		case "Forge":
			return new String[] { "A25-Roman", "Scout", "Salvage",
					"Manufactory", "Destroy" };
		case "Manufactory":
			return new String[] { "A25-Roman", "Scout", "Phantom",
					"Sakata-MK2", "Salvage", "Mechanics Terminal", "Destroy" };
		case "Mechanics Terminal":
			return new String[] { "A25-Roman", "Scout", "Phantom",
					"Sakata-MK2", "Sakata Spider", "Gladiator", "Salvage",
					"Destroy" };
		case "Armory":
			return new String[] { "Financel Support", "Reinforcments",
					"Reserve Energy", "Modified Phantom", "Destroy" };
		case "Generator":
			return new String[] { "Power", "Solar Grid", "Destroy" };
		case "Solar Grid":
			return new String[] { "Power", "Modified Sakata", "Destroy" };
		case "Bank":
			return new String[] { "Exhange", "Treasury", "Destroy" };
		case "Treasury":
			return new String[] { "Traiding", "Destroy" };
		case "Hospital":
			return new String[] { "Resuscitate", "Meditec", "Rescue Team",
					"War Sanctum", "Destroy" };
		case "War Sanctum":
			return new String[] { "Recover", "Meditec", "Rescue Team", "Saint",
					"Sphinx", "Destroy" };
		case "Special Operations":
			return new String[] { "Launch Missile Green",
					"Launch Missile Blue", "Black Operations",
					"Special Froces", "Far Sniper", "A27-Pride", "Destroy" };
		case "Base":
			return new String[] {};
		default:
			return new String[] {};
		}
	}

	/**
	 * Hier k�nnen Geb�ude nach positioniert werden, falls sie gr��er sind als
	 * gedacht
	 * 
	 * @param buildingName
	 * @return
	 */
	public int getBestOptimum(String buildingName) {
		switch (buildingName) {
		case "Armory":
			return 7;
		case "Hospital":
			return 8;
		case "Outpost":
			return 2;
		case "Special Operations":
			return 2;
		case "War Sanctum":
			return 5;
		case "Treasury":
			return 25;
		default:
			return 0;
		}
	}
}
