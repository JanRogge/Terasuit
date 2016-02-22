
package world;

import java.awt.Point;

public class WorldConstants {

	// World
	public static final int BUILDINGSCOUNT = 4;

	// MainBuilding
	public static final byte MAINBUILDINGID = 127;
	public static final byte OUTPOSTID = 1;
	public static final byte BARRACKSID = 2;
	public static final byte ARSENALID = 3;
	public static final byte FORGEID = 4;
	public static final byte MANUFACTORYID = 5;
	public static final byte MECHANICSTERMINALID = 6;
	public static final byte HOSPITALID = 7;
	public static final byte WARSANCTUMID = 8;
	public static final byte BANKID = 9;
	public static final byte TREASURYID = 10;
	public static final byte ARMORYID = 11;
	public static final byte GENERATORID = 12;
	public static final byte SOLARGRIDID = 13;
	public static final byte SPECIALOPERATIONSID = 14;

	public static final byte MARINEID = 1;
	public static final byte CHRONITETANKID = 2;
	public static final byte SNIPERID = 3;
	public static final byte GR�DITZID = 4;
	public static final byte HOVERTANKID = 5;
	public static final byte BLACKQUEENID = 6;


	public static Building getBuilding(int id, byte position, byte player) {
		switch (id) {
		case MAINBUILDINGID:
			return new MainBuilding(player);
		case OUTPOSTID:
			return new Outpost(position, player);
		case FORGEID:
			return new Forge(position, player);
		case HOSPITALID:
			return new Hospital(position, player);
		case BANKID:
			return new Bank(position, player);
		case ARMORYID:
			return new Armory(position, player);
		case GENERATORID:
			return new Generator(position, player);
		case SPECIALOPERATIONSID:
			return new SpecialOperations(position, player);
		default:
			return null;
		}
	}

	public static Unit getUnit(byte type, short id, Point position, byte player) {
		switch (type) {
		case MARINEID:
			return new Marine(id, position, player);
		case CHRONITETANKID:
			return new ChroniteTank(id, position, player);
		case SNIPERID:
			return new Sniper(id, position, player);
		case GR�DITZID:
			return new Gr�ditz(id, position, player);
		case HOVERTANKID:
			return new HoverTank(id, position, player);
		case BLACKQUEENID:
			return new BlackQueen(id, position, player);
		default:
			return null;
		}
	}

	public static boolean isFlying(byte id) {
		switch (id) {
		case MARINEID:
			return Marine.FLYING;
		case CHRONITETANKID:
			return ChroniteTank.FLYING;
		case SNIPERID:
			return Sniper.FLYING;
		case GR�DITZID:
			return Gr�ditz.FLYING;
		case HOVERTANKID:
			return HoverTank.FLYING;
		case BLACKQUEENID:
			return BlackQueen.FLYING;
		default:
			return false;
		}
	}
}