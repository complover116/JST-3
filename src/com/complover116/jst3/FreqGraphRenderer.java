package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics2D;

public class FreqGraphRenderer {
	
	public static float baseR = 0;
	public static float baseG = 0;
	public static float baseB = 0.5f;
	public static float scaleR = 0;
	public static float scaleG = 8;
	public static float scaleB = 8;
	public static void render(Graphics2D g2d, int width, int height, Frequency freq[]) {
		//g2d.setColor(Color.black);
		//g2d.fillRect(0, 0, this.width, this.height);
		
		float barwidth = 1;
		if(freq.length!=0)
		barwidth = width/(freq.length-4)/2;
		for(int i = 0; i < freq.length-4; i ++) {
			float scaledAmp = freq[i+4].amplitude;
			g2d.setColor(new Color((float)Math.min(1, Math.max(0, baseR+scaledAmp*scaleR)), (float)Math.min(1, Math.max(0, baseG+scaledAmp*scaleG)), (float)Math.min(1, Math.max(baseB+scaledAmp*scaleB, 0))));
			g2d.fillRect(width/2 - (int)(i*barwidth), height - (int) (scaledAmp*height) - height/4, (int)(barwidth), (int) (scaledAmp*height)*2);
			g2d.fillRect(width/2 + (int)(i*barwidth), height - (int) (scaledAmp*height) - height/4, (int)(barwidth), (int) (scaledAmp*height)*2);
			g2d.setColor(new Color((float)Math.min(0, Math.max(baseR+scaledAmp*scaleR, 1)), (float)Math.min(1, Math.max(baseG+scaledAmp*scaleG, 0)), (float)Math.min(1, Math.max(baseB+scaledAmp*scaleB, 0)), 0.01f));
			for(int glow = 1; glow < 20 ;glow++){		
				g2d.fillRect(width/2 - (int)(i*barwidth)-glow, height+glow - (int) (scaledAmp*height) - height/4, (int)(barwidth)+glow*2, (int) (scaledAmp*height)*2+glow*4);
				g2d.fillRect(width/2 + (int)(i*barwidth)-glow, height+glow - (int) (scaledAmp*height) - height/4, (int)(barwidth)+glow*2, (int) (scaledAmp*height)*2+glow*4);
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
