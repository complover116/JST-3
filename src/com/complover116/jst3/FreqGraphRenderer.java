package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics2D;

public class FreqGraphRenderer {
	public static void render(Graphics2D g2d, int width, int height, Frequency freq[]) {
		//g2d.setColor(Color.black);
		//g2d.fillRect(0, 0, this.width, this.height);
		g2d.setBackground(new Color(0, true));
		g2d.clearRect(0, 0, width, height);
		float barwidth = 1;
		if(freq.length!=0)
		barwidth = width/freq.length/2;
		for(int i = 0; i < freq.length; i ++) {
			float scaledAmp = freq[i].amplitude;
			g2d.setColor(new Color((float)Math.min(1, scaledAmp*8), 0, (float)Math.min(1, 0.5+scaledAmp*8)));
			g2d.fillRect(width/2 - (int)(i*barwidth), 0, (int)(barwidth), (int) (scaledAmp*height/2));
			g2d.fillRect(width/2 + (int)(i*barwidth), 0, (int)(barwidth), (int) (scaledAmp*height/2));
			g2d.setColor(new Color((float)Math.min(1, scaledAmp*8), 0, (float)Math.min(1, 0.5+scaledAmp*8), 0.01f));
			for(int glow = 1; glow < 20 ;glow++){		
				g2d.fillRect(width/2 - (int)(i*barwidth)-glow, -glow, (int)(barwidth)+glow*2, (int) (scaledAmp*height/2)+glow*2);
				g2d.fillRect(width/2 + (int)(i*barwidth)-glow, -glow, (int)(barwidth)+glow*2, (int) (scaledAmp*height/2)+glow*2);
			}
		}
		/*for(int i = 0; i < 10; i ++) {
			g2d.drawString(i*100+"hz", 400, i*100);
		}*/
		/*g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Serif", Font.PLAIN, 24));
		g2d.drawString("Overhead: "+overhead +" frames", 0, 20);
		for(int i = 0; i<particles.size(); i ++) {
			particles.get(i).draw(g2d);
		}*/
	}
}
