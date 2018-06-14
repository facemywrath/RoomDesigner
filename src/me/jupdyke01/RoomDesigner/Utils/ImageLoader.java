package me.jupdyke01.RoomDesigner.Utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {

	public BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch(IOException e) {
			FileUtils.crashedFile(e);
			return null;
		}
	}
}
