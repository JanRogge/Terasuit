package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class BtnCreator {
	
	/**
	 * Erstellt aus angegebenen Werten Buttons
	 * @param btnBACK
	 * @param x
	 * @param y
	 * @param breite
	 * @param h�he
	 * @param ColorNum
	 */
	public void createOne(JButton defaultButton, int x, int y, int breite, int h�he, int ColorNum){
		defaultButton.setBounds(x, y, breite, h�he);
		defaultButton.setBackground(new Color(ColorNum,ColorNum,ColorNum));
		defaultButton.setFont(new Font("Arial", Font.BOLD, 10));
	}	
}
