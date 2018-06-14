package me.jupdyke01.RoomDesigner.GridManager;

import java.awt.Graphics;

public class Tile {

	TileSlot slot;
	TileType type;

	public Tile(TileSlot slot, TileType type) {
		this.type = type;
		this.slot = slot;
	}

	public void render(Graphics g) {
		if (type.image != null)
			g.drawImage(type.image, slot.getX() + slot.getGrid().getMain().getxOffset(), slot.getY() + slot.getGrid().getMain().getyOffset(), slot.getGrid().getMain());
	}
	
	public TileType getType() {
		if(type == null)
			return TileType.Void;
		return type;
	}

}
