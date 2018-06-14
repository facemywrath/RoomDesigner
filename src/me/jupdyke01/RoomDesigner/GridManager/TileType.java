package me.jupdyke01.RoomDesigner.GridManager;

import java.awt.image.BufferedImage;

import me.jupdyke01.RoomDesigner.Utils.ImageLoader;

public enum TileType {

	Void("/void.png"),
	Floor("/floor.png"),
	Wall("/wall.png"),
	Torch("/torch.png"),
	Book("/book.png");
	
	public int ordinal;
	public BufferedImage image;
	
	TileType(String path) {
		ImageLoader loader = new ImageLoader();
		image = loader.loadImage(path);
	}
	
}
