package me.jupdyke01.RoomDesigner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class KeyInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private final int NUM_KEYS = 400;
	private boolean[] keys = new boolean[NUM_KEYS];

	private final int NUM_BUTTONS = 10;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	
	private boolean[] prebuttons = new boolean[NUM_BUTTONS];
	private boolean[] prekeys = new boolean[NUM_KEYS];

	private int mouseX, mouseY;
	private int scroll;
	
	public KeyInput(Main main) {
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		main.addKeyListener(this);
		main.addMouseMotionListener(this);
		main.addMouseListener(this);
		main.addMouseWheelListener(this);
	}
	
	public void tick() {
		scroll = 0;
		prebuttons = buttons.clone();
		prekeys = keys.clone();
	}
	
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode];
	}

	public boolean buttonPressed(int button) {
		if (buttons[button] == true && prebuttons[button] == false) {
			return true;
		}
		return false;
	}
	
	public boolean wasKeyPressed(int keyCode) {
		if (keys[keyCode] == false && prekeys[keyCode] == true) {
			return true;
		}
		return false;
	}
	
	public boolean isButtonDown(int button) {
		return buttons[button];
	}


	public int getMouseX() {
		return mouseX;
	}



	public int getMouseY() {
		return mouseY;
	}



	public int getScroll() {
		return scroll;
	}



	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int) (e.getX());
		mouseY = (int) (e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int) (e.getX());
		mouseY = (int) (e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		prebuttons = buttons.clone();
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		prebuttons = buttons.clone();
		buttons[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		prekeys = keys.clone();
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		prekeys = keys.clone();
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	
}
