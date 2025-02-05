package de.szut.server.world;

import java.awt.Point;

public interface Building {

	public byte getType();

	public byte getPlayer();

	public byte getSlotID();
	
	public int[] getPrice(int lvl);

	public int[] getPrice();

	public int getLevel();
	
	public boolean hasUpgrade();

	public byte getUpgrade();

	public void cancelUpgrade();

	public boolean isFinished();

	public void upgrade();

	boolean build();

	public Unit create();

	public boolean createUnit(byte typeID, short unitID, Point position);
	
}
