package com.complover116.jst3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class FreqGraph extends JPanel {

	
	Frequency freq[] = new Frequency[0];
	/**
	 * 
	 */
	private static final long serialVersionUID = 6237218907241567762L;
	
	public static long lastTick = 0;
	
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static volatile int overhead = 0;
	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D)g).setBackground(new Color(0, true));
		((Graphics2D)g).clearRect(0, 0, this.getWidth(), this.getHeight());
		FreqGraphRenderer.render((Graphics2D)g, this.getWidth(), this.getHeight(), freq);
	}
	public void update(Frequency freq[], int pos) {
		this.freq = freq;
		double deltaT = (float)(System.nanoTime()-lastTick)/1000000000;
		lastTick = System.nanoTime();
		overhead = JST3.lastAnalyzedFrame - pos;
		/*for(int i = 0; i < freq.length; i ++) {
			if(freq[i].amplitude>0.1f){
				
				Color color = new Color(0, (float)Math.min(1, freq[i].amplitude*8), (float)Math.min(1, 0.5+freq[i].amplitude*4));
				float y = freq[i].frequency+5;
				float velX = freq[i].amplitude*10000f;
				
				Particle sas = new Particle();
				sas.x = 0;
				sas.y = y;
				sas.color = color;
				
				sas.velX = velX;
				sas.velY = 0;
				sas.size = 8f;
				sas.decay = 8f;
				particles.add(sas);
				
				Particle sas2 = new Particle();
				sas2.x = getWidth();
				sas2.y = y;
				sas2.color = color;
				
				sas2.velX = -velX;
				sas2.velY = 0;
				sas2.size = 8f;
				sas2.decay = 8f;
				particles.add(sas2);
			}
		}*/
		for(int i = 0; i<particles.size(); i ++) {
			particles.get(i).tick(deltaT);
			if(particles.get(i).dead){
				particles.remove(i);
			}
		}
		this.repaint();
	}
}
