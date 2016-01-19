package grafig;

import inGame.BaseBuildings;
import inGame.BtnCreator;
import inGame.Funktions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SetButtons {

	LoginRegisterPanel loginRegisterPanel = new LoginRegisterPanel();
	boolean registerOpen = false;
	boolean loginOpen = false;
	boolean serverCreateOpen = false;
	BaseBuildings buildings = new BaseBuildings();
	BtnCreator btnCreator = new BtnCreator();

	public void setbuttons(Panel panel, String picName, Loader loader,
			Funktions func) {

		// #########################################################################
		//
		// MAINPANEL
		//
		// #########################################################################

		// Buttons f�r das Startpanel werden gesetzt und mit Aktionlisener
		// versetzt
		if (picName.equals("Wallpaper/Start_Hintergrund.png")) {

			// Start-Button
			JButton btnStart = new JButton("Start");
			btnStart.setBounds(168, 290, 245, 65);
			btnStart.setBackground(new Color(255, 50, 0));
			btnStart.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1,
					Color.red));
			btnStart.setFont(new Font("ArialB", Font.BOLD, 24));
			btnStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Start"-Buttons gelangt man in die
					// Lobby
					loader.connection.init();
					if (loader.connection.isServerAccess()) {
						loader.switchPanel(loader.Lobbypage);
					} else {
						System.out
								.println("Server konnte nicht gefunden werden. ");
					}
				}
			});
			panel.add(btnStart);

			// Option-Button
			JButton btnOption = new JButton("Option");
			btnOption.setBounds(138, 383, 245, 65);
			btnOption.setBackground(new Color(255, 70, 0));
			btnOption.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1,
					Color.red));
			btnOption.setFont(new Font("Arial", Font.BOLD, 24));
			btnOption.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Options"-Buttons gelangt man in die
					// Optionen
					loader.switchPanel(loader.Lobbypage);
				}
			});
			panel.add(btnOption);

			// Exit-Button
			JButton btnExit = new JButton("Exit");
			btnExit.setBounds(183, 480, 245, 65);
			btnExit.setBackground(new Color(255, 90, 0));
			btnExit.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1,
					Color.red));
			btnExit.setFont(new Font("Arial", Font.BOLD, 24));
			btnExit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Schlie�st das Programm

					loader.exit();
				}
			});
			panel.add(btnExit);

			JButton btnLogin = new JButton("LOGIN");
			btnLogin.setBounds(800, 732, 90, 25);
			btnLogin.setBackground(new Color(255, 90, 0));
			btnLogin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {

					if (loginOpen == false) {
						if (registerOpen == true) {
							loginRegisterPanel.popupdestroy(panel);
						}
						loginRegisterPanel.popupLogin(panel);
						loginOpen = true;
						registerOpen = false;
					} else {
						loginRegisterPanel.popupdestroy(panel);
						loginOpen = false;
					}
				}
			});
			panel.add(btnLogin);

			JButton btnRegister = new JButton("REGISTER");
			btnRegister.setBounds(920, 732, 90, 25);
			btnRegister.setBackground(new Color(255, 90, 0));
			btnRegister.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (registerOpen == false) {
						if (loginOpen == true) {
							loginRegisterPanel.popupdestroy(panel);
						}
						loginRegisterPanel.popupRegister(panel);
						registerOpen = true;
						loginOpen = false;
					} else {
						loginRegisterPanel.popupdestroy(panel);
						registerOpen = false;
					}
				}
			});
			panel.add(btnRegister);

			// #########################################################################
			//
			// SERVERLIST
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/serverlist.png")) {
			// JOIN-Button
			JButton btnJoin = new JButton("JOIN");
			btnJoin.setBounds(303, 689, 170, 60);// links / runter / breite /
													// h�he
			btnJoin.setBackground(new Color(255, 90, 0));
			btnJoin.setFont(new Font("Arial", Font.BOLD, 24));
			btnJoin.setIcon(new ImageIcon("Wallpaper/Join_Pfeil.png"));
			btnJoin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Join"-Buttons gelangt man in eine
					// Spielgruppe
					if (loader.connection.connectGroup()) {
						loader.switchPanel(loader.Grouppage);
					} else {
						System.out
								.println("Gruppe voll oder ein fehler ist aufgetretten");
					}

				}
			});
			panel.add(btnJoin);

			// Create-Button
			JButton btnCreateGroup = new JButton("CREATE");
			btnCreateGroup.setBounds(626, 689, 170, 60);// links / runter /
														// breite / h�he
			btnCreateGroup.setBackground(new Color(255, 90, 0));
			btnCreateGroup.setFont(new Font("Arial", Font.BOLD, 24));
			btnCreateGroup.setIcon(new ImageIcon("Wallpaper/Create_Pfeil.png"));
			btnCreateGroup.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (loader.connection.createGroup()) {
						// Beim klick auf dem "Create"-Buttons gelangt man in
						// eine Spielgruppe, als Besitzer
						loader.switchPanel(loader.Grouppage_owner);
					} else {
						System.out
								.println("Gruppe konnte nicht erstellt werden!");
					}

				}
			});
			panel.add(btnCreateGroup);

			// Back-Button
			JButton btnBACK = new JButton("BACK");
			btnBACK.setBounds(510, 710, 85, 30);// links / runter / breite /
												// h�he
			btnBACK.setBackground(new Color(255, 90, 0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 12));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Der Weg zur�ck
					loader.connection.close();
					loader.switchPanel(loader.Mainpage);
				}
			});
			panel.add(btnBACK);

			// #########################################################################
			//
			// LOBBY
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/Lobby.png")) {

			// Back-Button
			JButton btnBACK = new JButton("RETURN");
			btnBACK.setBounds(430, 695, 170, 60);// links / runter / breite /
													// h�he
			btnBACK.setBackground(new Color(255, 90, 0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (loader.connection.returnLobby()) {
						loader.switchPanel(loader.Lobbypage);
					} else {
						System.out
								.println("Verbindung zur Gruppe konnte nicht geschlossen werden!");
					}
				}
			});
			panel.add(btnBACK);

			// #########################################################################
			//
			// LOBBY_Besitzer
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/Lobby_BESITZER.png")) {
			// Back-Button
			JButton btnBACK = new JButton("RETURN");
			btnBACK.setBounds(430, 695, 170, 60);// links / runter / breite /
													// h�he
			btnBACK.setBackground(new Color(255, 90, 0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (loader.connection.closeLobby()) {
						loader.switchPanel(loader.Lobbypage);
					} else {
						System.out
								.println("Gruppenlobby konnte nicht geschlossen werden!!!");
					}
				}
			});
			panel.add(btnBACK);

			// START-BATTLE-Button
			JButton btnBattleStart = new JButton("START");
			btnBattleStart.setBounds(135, 695, 170, 60);// links / runter /
														// breite / h�he
			btnBattleStart.setBackground(new Color(255, 90, 0));
			btnBattleStart.setFont(new Font("Arial", Font.BOLD, 24));
			btnBattleStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (loader.connection.startGame()) {
						loader.switchPanel(loader.Gamepage);
					} else {
						System.out.println("Spiel kann nicht gestartet werden");
					}
				}
			});
			panel.add(btnBattleStart);

			// #########################################################################
			//
			// Maingame
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/Maingame.png")) {

			// Erstellen der Basis
			buildings.buildBase(panel, loader, func, buildings.red,
					buildings.blue, buildings.default_position_Leftside_x,
					buildings.default_position_Leftside_y);
			buildings.buildBase(panel, loader, func, buildings.grun,
					buildings.gelb, buildings.default_position_Rightside_x,
					buildings.default_position_Rightside_y);

			// Back-Button
			JButton btnBACK = new JButton("X");
			btnCreator.createOne(btnBACK, 700, 550, 60, 60, 87);
			btnBACK.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					if (loader.connection.returnToLobbyFromGame()) {
						loader.switchPanel(loader.Mainpage);
					} else {
						System.out.println("Kann das Spiel nicht verlassen!!");
					}

				}
			});
			panel.add(btnBACK);

			panel.repaint();
		}
	}
}
