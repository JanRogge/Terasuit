package de.szut.server.world;

import java.awt.Point;

public class Armory implements Building {
	
	public static final int MAXLVL = 0;
	public static final int BUILDINGTIME = 100;

	private int lvl = 0;
	private int buildTime;
	private int createTime;
	private byte position;
	private byte player;

	private Unit unit;
	
	private static final int[] prices =  {60, 25, 25, 00};
	private static final byte[] unitIDs = { 16 };
	private static final int[] numberOfUnits = { 1 };
	
	public Armory(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
	}

	@Override
	public byte getType() {
		return WorldConstants.ARMORYID;
	}

	@Override
	public byte getPlayer() {
		return player;
	}

	@Override
	public byte getSlotID() {
		return position;
	}

	@Override
	public int[] getPrice(int lvl) {
		return prices;
	}

	@Override
	public int[] getPrice() {
		return null;
	}
	
	@Override
	public int getLevel() {
		return lvl;
	}

	@Override
	public boolean hasUpgrade() {
		return lvl < MAXLVL;
	}

	@Override
	public byte getUpgrade() {
		return -128;
	}
	
	@Override
	public void cancelUpgrade() {
	}

	@Override
	public boolean isFinished() {
		return buildTime == 0;
	}
	
	@Override
	public void upgrade() {}

	@Override
	public boolean build() {
		if (buildTime >= 0) {
			buildTime--;
		}
		return buildTime == 0;
	}

	@Override
	public boolean createUnit(byte typeID, short unitID, Point position) {
		if (createTime <= 0 && buildTime <= 0) {
			boolean contains = false;
			for (int i = 0; i < numberOfUnits[lvl]; i++) {
				if (unitIDs[i] == typeID) {
					contains = true;
				}
			}
			if (contains) {
				unit = WorldConstants.getNewUnit(typeID, unitID, position, player);
				createTime = unit.getBuildTime();
				return true;
			}
		}
		return false;
	}

	@Override
	public Unit create() {
		if (createTime >= 0) {
			createTime--;
		}
		if (createTime == 0) {
			return unit;
		} else {
			return null;
		}
	}
}
