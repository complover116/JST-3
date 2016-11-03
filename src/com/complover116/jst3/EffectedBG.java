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
	Frequency freq[] = new Frequency[0];
	BufferedImage bg;
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		//g2d.setColor(Color.black);
		//g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setBackground(new Color(0, true));
		g2d.clearRect(0, 0, getWidth(), getHeight());
		try{
			float avgBassAmp = 0;
			for(int i = 1; i < 20; i ++ ){
				avgBassAmp += freq[i].amplitude;
			}
			avgBassAmp /= 19;
		
		g2d.drawImage(bg, (int)(Math.random()*avgBassAmp*100)-10, (int)(Math.random()*avgBassAmp*100)-10, this.getWidth()+10, this.getHeight()+10, null);
		g2d.setColor(new Color(0,0,0,0.2f-(float)Math.min(avgBassAmp*4, 0.2)));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		} catch (ArrayIndexOutOfBoundsException e) {
			//This is fine!	
		}
		FreqGraphRenderer.render(g2d, getWidth(), getHeight(), freq);
	}
	public EffectedBG(BufferedImage bg) {
		this.bg = bg;
	}
	
	public void update(Frequency freq[], int pos) {
		this.freq = freq;
		//TBD
		//double deltaT = (float)(System.nanoTime()-lastTick)/1000000000;
		//lastTick = System.nanoTime();
		//overhead = JST3.lastAnalyzedFrame - pos;
		this.repaint();
	}
}
