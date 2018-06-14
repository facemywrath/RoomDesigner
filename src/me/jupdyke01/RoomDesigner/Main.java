package me.jupdyke01.RoomDesigner;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import me.jupdyke01.RoomDesigner.Display.Window;
import me.jupdyke01.RoomDesigner.GridManager.Grid;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 2823157216838186288L;

	private int width = 1280, height = width / 12 * 9;
	private int tileX = 0, tileY = 0;
	private int xOffset = 0, yOffset = 0;
	private int tileSize = 64;
	private Thread thread;
	private boolean running = false;
	private KeyInput ki;
	private Grid grid;
	public selectionState selectState = selectionState.NONE;
	
	public int frames;
	
	String title = "Room Designer";

	public enum selectionState {
		NONE,
		LAYER_SELECTION,
		TILE_SELECTION;
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);

		grid.render(g);
		
		g.dispose();
		bs.show();
	}

	public void tick() {
		if (ki.isKeyDown(KeyEvent.VK_W))
			yOffset+=5;
		if (ki.isKeyDown(KeyEvent.VK_A))
			xOffset+=5;
		if (ki.isKeyDown(KeyEvent.VK_S))
			yOffset-=5;
		if (ki.isKeyDown(KeyEvent.VK_D))
			xOffset-=5;
		grid.tick();
		ki.tick();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
		
		ki = new KeyInput(this);
	}

	public void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		//	JTextField xField = new JTextField(5);
		//	JTextField yField = new JTextField(5);

		SpinnerModel xFieldModel = new SpinnerNumberModel(16, 1, 32, 1);
		SpinnerModel yFieldModel = new SpinnerNumberModel(16, 1, 32, 1);

		JSpinner xField = new JSpinner(xFieldModel);
		JSpinner yField = new JSpinner(yFieldModel);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("X:"));
		myPanel.add(xField);
		myPanel.add(Box.createHorizontalStrut(50));
		myPanel.add(new JLabel("Y:"));
		myPanel.add(yField);

		int result = JOptionPane.showConfirmDialog(this, myPanel, "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		if (result == JOptionPane.OK_OPTION) {
			tileX = (int) xField.getValue();
			tileY = (int) yField.getValue();
		} else {
			System.exit(1);
		}
		
		new Window(this);
		grid = new Grid(this);
		
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	public int getTileSize() {
		return tileSize;
	}
	
	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return title;
	}

	public KeyInput getKeyInput() {
		return ki;
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
