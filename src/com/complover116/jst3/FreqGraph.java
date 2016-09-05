package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class FreqGraph extends JPanel {

	
	Frequency freq[] = new Frequency[0];
	/**
	 * 
	 */
	private static final long serialVersionUID = 6237218907241567762L;
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.white);
		for(int i = 0; i < freq.length; i ++) {
			g2d.fillRect(freq[i].frequency, 0, 10, (int) (freq[i].amplitude*this.getHeight()));
		}
		for(int i = 0; i < 10; i ++) {
			g2d.drawString(i*100+"hz", i*100, 400);
		}
		
	}
	public void update(Frequency freq[]) {
		this.freq = freq;
		this.repaint();
	}
}
