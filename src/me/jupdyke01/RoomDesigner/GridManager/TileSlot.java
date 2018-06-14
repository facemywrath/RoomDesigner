package me.jupdyke01.RoomDesigner.GridManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import me.jupdyke01.RoomDesigner.Main.selectionState;

public class TileSlot {

	private Tile r, g, b;
	private int x, y;
	private Grid grid;

	public TileSlot(int x, int y, Grid grid) {
		this.x = x;
		this.y = y;
		this.grid = grid;
	}

	public void tick() {
		if (grid.getMain().getKeyInput().isKeyDown(KeyEvent.VK_CONTROL)) {
			if (grid.getMain().getKeyInput().wasKeyPressed(KeyEvent.VK_Z)) {
				//System.out.println(grid.getUndo() == null);

				if (grid.getUndo() != null && grid.getUndo().getSlot() != null && grid.getUndo().getSlot().equals(this)) {
					this.r = null;
					this.g = null;
					this.b = null;
					if (grid.getUndo().getR() != null)
						this.r = new Tile(this, grid.getUndo().getR());
					if (grid.getUndo().getG() != null)
						this.g = new Tile(this, grid.getUndo().getG());
					if (grid.getUndo().getB() != null)
						this.b = new Tile(this, grid.getUndo().getB());
					grid.removeUndo();
				}
			} else {
				if (grid.getMain().getKeyInput().getMouseX() >= x + grid.getMain().getxOffset() && grid.getMain().getKeyInput().getMouseX() < x + grid.getMain().getxOffset() + grid.getMain().getTileSize()) {
					if (grid.getMain().getKeyInput().getMouseY() >= y + grid.getMain().getyOffset() && grid.getMain().getKeyInput().getMouseY() < y + grid.getMain().getyOffset() + grid.getMain().getTileSize()) {
						if (grid.selected != null && !grid.selected.equals(this)) {
							this.r = null;
							this.g = null;
							this.b = null;
							setRGB(new Tile(this,(grid.selected.getRed() == null ? TileType.Void : grid.selected.getRed().getType())), new Tile(this,(grid.selected.getGreen() == null ? TileType.Void : grid.selected.getGreen().getType())), new Tile(this,(grid.selected.getBlue() == null ? TileType.Void : grid.selected.getBlue().getType())));
						}
					}
				}

			} 
		}
		if (grid.getMain().getKeyInput().buttonPressed(MouseEvent.BUTTON1)) {
			if (grid.getMain().selectState == selectionState.NONE) {
				if (grid.getMain().getKeyInput().getMouseX() >= x + grid.getMain().getxOffset() && grid.getMain().getKeyInput().getMouseX() < x + grid.getMain().getxOffset() + grid.getMain().getTileSize()) {
					if (grid.getMain().getKeyInput().getMouseY() >= y + grid.getMain().getyOffset() && grid.getMain().getKeyInput().getMouseY() < y + grid.getMain().getyOffset() + grid.getMain().getTileSize()) {
						grid.getMain().selectState = selectionState.LAYER_SELECTION;
						grid.selected = this;
						return;
					}
				}
			} else if (grid.selected.equals(this)) {
				int i = 1;
				for (int j = 1; j < 4; j++) {
					for(TileType type : TileType.values()) {
						int tempX = x + grid.getMain().getxOffset() + 5 + j*(grid.getMain().getTileSize() + 10);
						int tempY = y + grid.getMain().getyOffset() + i*(grid.getMain().getTileSize() + 10);

						int mouseX = grid.getMain().getKeyInput().getMouseX();
						int mouseY = grid.getMain().getKeyInput().getMouseY();

						if (mouseX >= tempX && mouseX < tempX + grid.getMain().getTileSize()) {
							if (mouseY >= tempY && mouseY < tempY + grid.getMain().getTileSize()) {
								switch (j) {
								case 1:
									//System.out.println("fIRST cLICKWLADLWKASK");
									r = null;
									setRed(new Tile(this, type));
									return;
								case 2:
									g = null;
									setGreen(new Tile(this, type));
									return;
								case 3:
									b = null;
									setBlue(new Tile(this, type));
									return;
								}
							}
						}
						i++;
					}
					i = 1;
				}
			}
			else
			{
				if(grid.getMain().getKeyInput().isKeyDown(KeyEvent.VK_SHIFT)) {
					fillAround(new ArrayList<>(), this);
				}
			}
		} 
		if (grid.getMain().getKeyInput().buttonPressed(MouseEvent.BUTTON3)) {
			grid.selected = null;
			grid.getMain().selectState = selectionState.NONE;
		}
	}

	public void fillAround(List<TileSlot> closedList, TileSlot original)
	{
		if(grid.selected == null)
			return;
		if(closedList.contains(this))
			return;
		TileSlot slot = this;
		if((slot.getRed() == null && original.getRed() == null) || slot.getRed() != null && original.getRed() != null && slot.getRed().getType() == original.getRed().getType()) 
			slot.setRed((grid.selected.getRed() != null?new Tile(slot, grid.selected.getRed().type):new Tile(slot, TileType.Void)));
		if((slot.getGreen() == null && original.getGreen() == null) || slot.getGreen() != null && original.getGreen() != null && slot.getGreen().getType() == original.getGreen().getType()) 
			slot.setGreen((grid.selected.getGreen() != null?new Tile(slot, grid.selected.getGreen().type):new Tile(slot, TileType.Void)));
		if((slot.getBlue() == null && original.getBlue() == null) || slot.getBlue() != null && original.getBlue() != null && slot.getBlue().getType() == original.getBlue().getType()) 
			slot.setBlue((grid.selected.getBlue() != null?new Tile(slot, grid.selected.getBlue().type):new Tile(slot, TileType.Void)));

		closedList.add(this);
		if(grid.getTileSlotAtPixel(x+1, y) != null)
			grid.getTileSlotAtPixel(x+1, y).fillAround(closedList, original);
		if(grid.getTileSlotAtPixel(x-1, y) != null)
			grid.getTileSlotAtPixel(x-1, y).fillAround(closedList, original);
		if(grid.getTileSlotAtPixel(x, y+1) != null)
			grid.getTileSlotAtPixel(x, y+1).fillAround(closedList, original);
		if(grid.getTileSlotAtPixel(x, y-1) != null)
			grid.getTileSlotAtPixel(x, y-1).fillAround(closedList, original);

	}

	public void renderMenu(Graphics graphics) {
		if (grid.selected != null && grid.selected.equals(this)) {
			graphics.setColor(Color.WHITE);
			graphics.drawRect(x + grid.getMain().getxOffset(), y + grid.getMain().getyOffset(), grid.getMain().getTileSize(), grid.getMain().getTileSize());
			graphics.setColor(Color.DARK_GRAY);
			graphics.fillRect(x + 5 + grid.getMain().getTileSize() + grid.getMain().getxOffset(), y - 5 + grid.getMain().getyOffset(), grid.getMain().getTileSize() * 3 + 40, grid.getMain().getTileSize() * (TileType.values().length + 1) + 10 * (TileType.values().length + 1));
			graphics.setColor(Color.RED);
			if (r == null)
				graphics.drawRect(x + 15 + grid.getMain().getTileSize() + grid.getMain().getxOffset(), y + grid.getMain().getyOffset(), grid.getMain().getTileSize(), grid.getMain().getTileSize());
			else
				graphics.drawImage(r.getType().image, x + 15 + grid.getMain().getTileSize() + grid.getMain().getxOffset(), y + grid.getMain().getyOffset() ,grid.getMain());
			graphics.setColor(Color.GREEN);
			if (g == null)
				graphics.drawRect(x + 5 + 2*(grid.getMain().getTileSize() + 10) + grid.getMain().getxOffset(), y + grid.getMain().getyOffset(), grid.getMain().getTileSize(), grid.getMain().getTileSize());
			else
				graphics.drawImage(g.getType().image, x + 5 + 2*(grid.getMain().getTileSize() + 10) + grid.getMain().getxOffset(), y + grid.getMain().getyOffset() ,grid.getMain());
			graphics.setColor(Color.BLUE);
			if (b == null)
				graphics.drawRect(x + 5 + 3*(grid.getMain().getTileSize() + 10) + grid.getMain().getxOffset(), y + grid.getMain().getyOffset(), grid.getMain().getTileSize(), grid.getMain().getTileSize());
			else
				graphics.drawImage(b.getType().image, x + 5 + 3*(grid.getMain().getTileSize() + 10) + grid.getMain().getxOffset(), y + grid.getMain().getyOffset() ,grid.getMain());

			int i = 1;
			for (int j = 1; j < 4; j++) {
				for(TileType type : TileType.values()) {
					int tempX = x + grid.getMain().getxOffset() + 5 + j*(grid.getMain().getTileSize() + 10);
					int tempY = y + grid.getMain().getyOffset() + i*(grid.getMain().getTileSize() + 10);
					graphics.drawImage(type.image, tempX, tempY, grid.getMain());
					i++;
				}
				i = 1;
			}
		}
	}

	public void render(Graphics graphics) {
		if (b != null)
			b.render(graphics);
		if (g != null)
			g.render(graphics);
		if (r != null)
			r.render(graphics);

		if (b == null && g == null && r == null && grid.getMain().selectState == selectionState.NONE) {
			if (grid.getMain().getKeyInput().getMouseX() >= x + grid.getMain().getxOffset() && grid.getMain().getKeyInput().getMouseX() < x + grid.getMain().getxOffset() + grid.getMain().getTileSize()) {
				if (grid.getMain().getKeyInput().getMouseY() >= y + grid.getMain().getyOffset() && grid.getMain().getKeyInput().getMouseY() < y + grid.getMain().getyOffset() + grid.getMain().getTileSize()) {
					graphics.setColor(Color.WHITE);
					graphics.drawRect(x + grid.getMain().getxOffset(), y + grid.getMain().getyOffset(), grid.getMain().getTileSize(), grid.getMain().getTileSize());
				}
			}
		}
	}

	public void setRGB(Tile r, Tile g, Tile b) {
		grid.addUndo(new RGBStorage(this, (r != null ? r.getType() : null), (g != null ? g.getType() : null), (b != null ? b.getType() : null)));
		if (r != null)
			this.r = r;
		if (g != null)
			this.g = g;
		if (b != null)
			this.b = b;
	}

	public void setRed(Tile r) {
		grid.addUndo(new RGBStorage(this, (r != null ? r.getType() : null), (g != null ? g.getType() : null), (b != null ? b.getType() : null)));
		this.r = r;
	}

	public void setGreen(Tile g) {
		grid.addUndo(new RGBStorage(this, (r != null ? r.getType() : null), (g != null ? g.getType() : null), (b != null ? b.getType() : null)));
		this.g = g;
	}

	public void setBlue(Tile b) {
		grid.addUndo(new RGBStorage(this, (r != null ? r.getType() : null), (g != null ? g.getType() : null), (b != null ? b.getType() : null)));
		this.b = b;
	}

	public Tile getRed() {
		return r;
	}

	public Tile getGreen() {
		return g;
	}

	public Tile getBlue() {
		return b;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTileX() {
		return (int) Math.floor(x / grid.getMain().getTileSize());
	}

	public int getTileY() {
		return (int) Math.floor(y / grid.getMain().getTileSize());
	}

	public Grid getGrid() {
		return grid;
	}
}
