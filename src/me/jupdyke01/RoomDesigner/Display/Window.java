package me.jupdyke01.RoomDesigner.Display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import me.jupdyke01.RoomDesigner.Main;

public class Window extends Canvas {

	private static final long serialVersionUID = 1L;

	public Window(Main main) {
		JFrame frame = new JFrame(main.getName());
		Dimension d = new Dimension(main.getWidth(), main.getHeight());

		frame.setPreferredSize(d);
		frame.setSize(d);
		frame.setMaximumSize(d);
		frame.setMinimumSize(d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(main);
		frame.pack();
		frame.setVisible(true);
	}
	
	

}
