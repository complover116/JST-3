package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class EffectedBG extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6084337019173607968L;

	BufferedImage bg;
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		//g2d.setColor(Color.black);
		//g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setBackground(new Color(0, true));
		g2d.clearRect(0, 0, getWidth(), getHeight());
		g2d.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		
	}
	public EffectedBG(BufferedImage bg) {
		this.bg = bg;
	}
}
