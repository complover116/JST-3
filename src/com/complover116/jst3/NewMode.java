package com.complover116.jst3;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewMode {
	public static void start() {
		JFrame frame = new JFrame("JST3 - New Mode!");
		
		FreqGraph graph = new FreqGraph();
		frame.add(graph);
		graph.setPreferredSize(new Dimension(500, 1000));
		frame.setAlwaysOnTop(true);
		frame.setBounds(frame.getGraphicsConfiguration().getBounds());
		frame.setBackground(new Color(0, true));
		graph.setBackground(new Color(0, true));
		frame.setVisible(true);
	}
}
