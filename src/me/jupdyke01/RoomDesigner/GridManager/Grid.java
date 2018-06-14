package me.jupdyke01.RoomDesigner.GridManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import me.jupdyke01.RoomDesigner.Main;

public class Grid {

	private ArrayList<RGBStorage> undoCache = new ArrayList<>();

	private Main main;
	private ArrayList<TileSlot> slots = new ArrayList<>();
	public TileSlot selected = null;

	public boolean exporting = false;


	public Grid(Main main) {
		this.main = main;
		for (int x = 0; x < main.getTileX(); x++) {
			for (int y = 0; y < main.getTileY(); y++) {
				slots.add(new TileSlot(x * main.getTileSize(), y * main.getTileSize(), this));
			}
		}
	}

	public void export() {
		File folder = new File("Rooms");
		if (!folder.exists())
			folder.mkdir();
		File imageFile = new File(folder, folder.listFiles().length + ".png");
		BufferedImage image = new BufferedImage(main.getTileX(), main.getTileY(), BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		try {
			if (!imageFile.exists()) 
				imageFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int x = 0; x < main.getTileX(); x++)
		{
			for(int y = 0; y < main.getTileY(); y++)
			{
				TileSlot at = getTileSlotAt(x, y);
				if(at == null)
					image.setRGB(x, y, new Color(0,0,0).getRGB());
				if(at == null)
					continue;
				int red = (at.getRed() == null?0:at.getRed().getType().ordinal());
				int green = (at.getGreen() == null?0:at.getGreen().getType().ordinal());
				int blue = (at.getBlue() == null?0:at.getBlue().getType().ordinal());
				int rgb = 0;
				rgb = red*65536 + green*256 + blue;
				image.setRGB(x, y, rgb);
			}
		}
		try {
			ImageIO.write(image, "png", imageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TileSlot getTileSlotAt(int x, int y)
	{
		for(TileSlot slot : slots)
		{
			if(slot.getTileX() == x  && slot.getTileY() == y )
				return slot;
		}
		return null;
	}


	public TileSlot getTileSlotAtPixel(int x, int y)
	{
		for(TileSlot slot : slots)
		{
			if(slot.getTileX() == Math.floor(x / getMain().getTileSize())  && slot.getTileY() == Math.floor(y / getMain().getTileSize()) )
				return slot;
		}
		return null;
	}

	public void tick() {
		for (TileSlot slot : slots) {
			slot.tick();
		}
		if (this.getMain().getKeyInput().isKeyDown(KeyEvent.VK_CONTROL)) {
			if (!this.exporting && this.getMain().getKeyInput().wasKeyPressed(KeyEvent.VK_ENTER)) {
				this.exporting = true;
				JPanel myPanel = new JPanel();
				int result = JOptionPane.showConfirmDialog(this.getMain(), myPanel, "Are you sure you want to export?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				if (result == JOptionPane.OK_OPTION) {
					this.export();
					this.exporting = false;
				}
				else
				{
					this.exporting = false;
				}

			}

		}
	}

	public void render(Graphics g) {
		for (int x = 0; x < main.getTileX(); x++) {
			for (int y = 0; y < main.getTileY(); y++) {
				g.setColor(Color.BLACK);
				g.drawRect(x * main.getTileSize() + main.getxOffset(), y * main.getTileSize() + main.getyOffset(), main.getTileSize(), main.getTileSize());
			}
		}
		for (TileSlot slot : slots) {
			slot.render(g);
		}
		for (TileSlot slot : slots) {
			slot.renderMenu(g);
		}
	}

	public void addUndo(RGBStorage rgbs) {
		if(undoCache.size() >= 50)
			undoCache.remove(0);
		//System.out.println(undoCache.size());
		undoCache.add(rgbs);
	}

	public RGBStorage getUndo() {
		if (undoCache.isEmpty())
			return null;
		RGBStorage rgbs = undoCache.get(undoCache.size() - 1);
		return rgbs;
	}

	public void removeUndo() {
		//	System.out.println(undoCache.isEmpty());
		if (undoCache.isEmpty())
			return;
		//	System.out.println(undoCache.size());
		undoCache.remove(undoCache.get(undoCache.size() - 1));
	}

	public Main getMain() {
		return main;
	}

}
