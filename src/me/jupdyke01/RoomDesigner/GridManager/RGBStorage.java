package me.jupdyke01.RoomDesigner.GridManager;

public class RGBStorage {

	private TileType r, g, b;
	private TileSlot slot;
	
	public RGBStorage(TileSlot slot, TileType r, TileType g, TileType b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.slot = slot;
	}

	public TileType getRed() {
		return r;
	}

	public TileType getGreen() {
		return g;
	}

	public TileType getBlue() {
		return b;
	}
	
	public TileSlot getSlot() {
		return slot;
	}
}
