package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class EffectedBG extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6084337019173607968L;
	Frequency freq[] = new Frequency[0];
	BufferedImage bg;
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static long lastTick = 0;
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		//g2d.setColor(Color.black);
		//g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setBackground(new Color(0, true));
		g2d.clearRect(0, 0, getWidth(), getHeight());
		try{
			float avgBassAmp = 0;
			for(int i = 1; i < 10; i ++ ){
				avgBassAmp += freq[i].amplitude;
			}
			avgBassAmp /= 9;
		
			float avgAmp = 0;
			for(int i = 1; i < 100; i ++ ){
				avgAmp += freq[i].amplitude;
			}
			avgAmp /= 99;
		g2d.drawImage(bg, (int)(Math.random()*avgBassAmp*200)-10-(int)(avgBassAmp*100), (int)(Math.random()*avgBassAmp*200)-10-(int)(avgBassAmp*100), this.getWidth()+20, this.getHeight()+20, null);
		g2d.setColor(new Color(0,0,0,1f-(float)Math.min(avgAmp*16+avgBassAmp*8, 1)));
		g2d.fillRect(0, 0, getWidth(), getHeight());
		} catch (ArrayIndexOutOfBoundsException e) {
			//This is fine!	
		}
		FreqGraphRenderer.render(g2d, getWidth(), getHeight(), freq);
		
		for(int i = 0; i<particles.size(); i ++) {
			particles.get(i).draw(g2d);
		}
	}
	public EffectedBG(BufferedImage bg) {
		this.bg = bg;
	}
	
	public void update(Frequency freq[], int pos) {
		this.freq = freq;
		
		double deltaT = (float)(System.nanoTime()-lastTick)/1000000000;
		lastTick = System.nanoTime();
		//TBD
		//overhead = JST3.lastAnalyzedFrame - pos;
		
		
		float barwidth = 1;
		if(freq.length!=0)
		barwidth = getWidth()/(freq.length-4)/2;
		for(int i = 0; i < freq.length-4; i ++) {
			float scaledAmp = freq[i+4].amplitude;
			if(Math.random()>1-scaledAmp/2)
			particles.add(new Particle().setPos(getWidth()/2 - (int)(i*barwidth), getHeight()*3/4).setVel((float) (Math.random()*200-100), -scaledAmp*2000-100).setSizeAndDecay(8, 4)
					.setColor(new Color((float)Math.min(1, Math.max(0, FreqGraphRenderer.baseR+scaledAmp*FreqGraphRenderer.scaleR)),
							(float)Math.min(1, Math.max(FreqGraphRenderer.baseG+scaledAmp*FreqGraphRenderer.scaleG, 0)),
							(float)Math.min(1, Math.max(FreqGraphRenderer.baseB+scaledAmp*FreqGraphRenderer.scaleB, 0)), 0.5f)));
			if(Math.random()>1-scaledAmp/2)
			particles.add(new Particle().setPos(getWidth()/2 + (int)(i*barwidth), getHeight()*3/4).setVel((float) (Math.random()*200-100), -scaledAmp*2000-100).setSizeAndDecay(8, 4)
					.setColor(new Color((float)Math.min(1, Math.max(0, FreqGraphRenderer.baseR+scaledAmp*FreqGraphRenderer.scaleR)),
							(float)Math.min(1, Math.max(0, FreqGraphRenderer.baseG+scaledAmp*FreqGraphRenderer.scaleG)),
							(float)Math.min(1, Math.max(0, FreqGraphRenderer.baseB+scaledAmp*FreqGraphRenderer.scaleB)), 0.5f)));
		}
		
		
		
		
		for(int i = 0; i<particles.size(); i ++) {
			particles.get(i).tick(deltaT);
			if(particles.get(i).dead){
				particles.remove(i);
			}
		}
		this.repaint();
	}
}
