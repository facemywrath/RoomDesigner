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

	public TileType getR() {
		return r;
	}

	public TileType getG() {
		return g;
	}

	public TileType getB() {
		return b;
	}
	
	public TileSlot getSlot() {
		return slot;
	}
}
