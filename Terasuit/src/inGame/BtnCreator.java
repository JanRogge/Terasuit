package inGame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class BtnCreator {

	public void createOne(JButton btnBACK, int x, int y, int breite, int h�he,
			int ColorNum) {
		btnBACK.setBounds(x, y, breite, h�he);// links / runter / breite / h�he
		btnBACK.setBackground(new Color(ColorNum, ColorNum, ColorNum));
		btnBACK.setFont(new Font("Arial", Font.BOLD, 10));

	}

}
