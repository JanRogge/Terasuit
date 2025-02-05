package de.szut.client.grafik;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import de.szut.client.logic.ServerConnection;

public class LoginRegisterPanel {
	JLabel registerPanel = new JLabel();
	JLabel loginPanel = new JLabel();
	JTextField userField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton btnlogin = new JButton();
	JPasswordField password2Field = new JPasswordField();
	JTextField mailField = new JTextField();
	JLabel lbusername = new JLabel();
	JLabel lbpassword = new JLabel();
	JLabel lbpassword2 = new JLabel();
	JLabel lbmail = new JLabel();
	ServerConnection connection;

	/**
	 * Initialisiert das LoginRegisterPanel mit der Verbindung
	 * 
	 * @param connection
	 *            Die Verbindung zum Server
	 */
	public LoginRegisterPanel(ServerConnection connection) {
		this.connection = connection;
	}

	/**
	 * Erstellt ein Fenster mit Kn�pfen zum Einloggen
	 * 
	 * @param panel
	 */
	public void popupLogin(Panel panel) {

		btnlogin = new JButton("Login");
		btnlogin.setBounds(800, 650, 200, 50);
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String password = "";
				for (char c : passwordField.getPassword()) {
					password += c;
				}
				if (connection.isServerAccess()) {
					connection.login(userField.getText(), password);
				} else {
					JOptionPane.showMessageDialog(panel,
							"Server not reachable", "Problem",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		panel.add(btnlogin);

		lbusername = new JLabel("Username:");
		lbusername.setBounds(800, 450, 200, 50);
		panel.add(lbusername);

		lbpassword = new JLabel("Password:");
		lbpassword.setBounds(800, 490, 200, 50);
		panel.add(lbpassword);

		createpopup(panel);

		userField = new JTextField();
		userField.setBounds(800, 485, 200, 20);
		panel.add(userField);
		userField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(800, 525, 200, 20);
		panel.add(passwordField);
		passwordField.setColumns(10);

		panel.repaint();
	}

	/**
	 * Erstellt ein Fenster zum Registrieren
	 * 
	 * @param panel
	 */
	public void popupRegister(Panel panel) {

		btnlogin = new JButton("Register");
		btnlogin.setBounds(800, 650, 200, 50);
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				String password1 = "";
				String password2 = "";
				for (char c : passwordField.getPassword()) {
					password1 += c;
				}
				for (char c : password2Field.getPassword()) {
					password2 += c;
				}
				if (password1.equals(password2)) {
					if (connection.isServerAccess()) {
						connection.register(userField.getText(), password1,
								mailField.getText());
					} else {
						JOptionPane.showMessageDialog(panel,
								"Server not reachable", "Problem",
								JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(panel,
							"Password is not identical", "Inane warning",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panel.add(btnlogin);

		lbusername = new JLabel("Username:");
		lbusername.setBounds(800, 450, 200, 50);
		panel.add(lbusername);

		lbpassword = new JLabel("Password:");
		lbpassword.setBounds(800, 490, 200, 50);
		panel.add(lbpassword);

		lbpassword2 = new JLabel("Password again:");
		lbpassword2.setBounds(800, 530, 200, 50);
		panel.add(lbpassword2);

		lbmail = new JLabel("E-Mail:");
		lbmail.setBounds(800, 570, 200, 50);
		panel.add(lbmail);

		createpopup(panel);

		userField = new JTextField();
		userField.setBounds(800, 485, 200, 20);
		panel.add(userField);
		userField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(800, 525, 200, 20);
		panel.add(passwordField);
		passwordField.setColumns(10);

		password2Field = new JPasswordField();
		password2Field.setBounds(800, 565, 200, 20);
		panel.add(password2Field);
		password2Field.setColumns(10);

		mailField = new JTextField();
		mailField.setBounds(800, 605, 200, 20);
		panel.add(mailField);
		mailField.setColumns(10);

		panel.repaint();

	}

	/**
	 * Erstellt den Hintergrund f�r die Register Buttons/ die Login-Buttons
	 * 
	 * @param panel
	 */
	private void createpopup(Panel panel) {
		registerPanel = new JLabel("");
		registerPanel.setIcon(new ImageIcon("Wallpaper/LoginBackground.png"));
		registerPanel.setBounds(770, 335, 500, 500);
		panel.add(registerPanel);
	}

	/**
	 * Zerst�rt konsequent alle Login/Register Komponenten
	 * 
	 * @param panel
	 */
	public void popupdestroy(Panel panel) {
		panel.remove(lbmail);
		panel.remove(lbpassword);
		panel.remove(lbpassword2);
		panel.remove(lbusername);
		panel.remove(password2Field);
		panel.remove(mailField);
		panel.remove(btnlogin);
		panel.remove(passwordField);
		panel.remove(userField);
		panel.remove(registerPanel);
		panel.repaint();
	}
}