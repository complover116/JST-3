package com.complover116.jst3;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class EffectedBG extends JPanel implements ComponentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6084337019173607968L;
	Frequency freq[] = new Frequency[0];
	
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	public static long lastTick = 0;
	
	
	
	//UI
	public static float volumeFade = 3;
	public static float volumeDisplay = 0;
	
	public static float imgVelX = 0;
	public static float imgVelY = 0;
	public static float imgRotVel = 0;
	public static float imgRot = -0.02f;
	public static float imgX = -50;
	public static float imgY = -50;
	
	public static float pauseBlink = 0;
	boolean pauseBlinkUp = true;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		Renderer.instantRender(g2d, freq);
		//DRAW THE CONTROLS
		
		g2d.setColor(new Color(1,1,1, Math.min(volumeFade*2, 1)));
		g2d.fill(new Arc2D.Float(Math.max(0, Math.min(1f, 1-volumeFade))*this.getHeight()/10, 
				Math.max(0, Math.min(1f, 1-volumeFade))*this.getHeight()/10, 
				this.getHeight()/10 - Math.max(0, Math.min(1f, 1-volumeFade))*this.getHeight()/5, 
				this.getHeight()/10 - Math.max(0, Math.min(1f, 1-volumeFade))*this.getHeight()/5, 0, Math.min(360, volumeDisplay*360), Arc2D.PIE));
		g2d.setColor(new Color(0,0,0, Math.min(volumeFade*2, 1)));
		g2d.fillOval(this.getHeight()/60, this.getHeight()/60, this.getHeight()/15, this.getHeight()/15);
		
		
		//g2d.setColor(new Color(1,1,1, Math.min(volumeFade, 1)));
		//g2d.drawString(Math.round(Metadata.volume*100)+"%", this.getHeight()/60, this.getHeight()/60);
		
		if(NewMode.music!=null && !NewMode.music.isRunning()) {
			g2d.setColor(new Color(1,1,1, pauseBlink));
			g2d.fillRect(this.getWidth()/9*3, this.getHeight()/4, this.getWidth()/8, this.getHeight()/2);
			g2d.fillRect(this.getWidth()/9*5, this.getHeight()/4, this.getWidth()/8, this.getHeight()/2);
		}
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		g2d.setColor(Color.WHITE);
		if(Config.showFPS) {
			g2d.drawString("Running at "+NewMode.realFPS+"FPS (target: "+Config.FRAMERATE+"), avg frame takes "+Math.round(NewMode.avgTickTime)+
							"ms ("+Math.round(1000/NewMode.avgTickTime)+" FPS possible)", 0, 25);
		}
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.min(volumeFade*2, 1)));
		g2d.drawImage(Images.volume, this.getHeight()/60, this.getHeight()/60, this.getHeight()/15, this.getHeight()/15, null);
		
	}
	public EffectedBG() {
		//this.bg = bg;
		this.addKeyListener(new Controls());
		this.setFocusable(true);
		this.requestFocusInWindow();
		
	}
	
	public void update(Frequency freq[], int pos) {		
		this.freq = freq;
		
		
		float deltaT = (float)(System.nanoTime()-lastTick)/1000000000;
		if(lastTick == 0) deltaT = 0;
		lastTick = System.nanoTime();
		//System.out.println("Frame "+deltaT);
		if(Config.newBgAnim) {
			imgX += imgVelX*deltaT;
			imgY += imgVelY*deltaT;
			imgRot += imgRotVel*deltaT;
			
			imgVelX-=imgX*Math.random()*deltaT*0.2f;
			imgVelY-=imgY*Math.random()*deltaT*0.5f;
			imgRotVel-=imgRot*Math.random()*deltaT*0.4f;
		}
		
		if(NewMode.music!=null && !NewMode.music.isRunning()) {
			for(int i = 0; i < freq.length; i ++) {
				if(freq[i].amplitude > 0)
				freq[i].amplitude *= 0.999;
				if(freq[i].amplitude < 0) {
					freq[i].amplitude = 0;
				}
			}
			if(pauseBlinkUp) {
				pauseBlink += deltaT;
				if(pauseBlink > 0.9) pauseBlinkUp = false;
			} else {
				pauseBlink -= deltaT;
				if(pauseBlink < 0.1) pauseBlinkUp = true;
			}
		}
		//TBD
		//overhead = JST3.lastAnalyzedFrame - pos;
		volumeDisplay += (Metadata.volume - volumeDisplay)*deltaT*5;
		
		if(volumeFade > 0){
			volumeFade -= deltaT;
		}
		if(volumeFade < 0) volumeFade = 0;
		float barwidth = 1;
		if(freq.length!=0)
		barwidth = getWidth()/(freq.length-4)/2;
		for(int i = 0; i < freq.length-4; i ++) {
			float scaledAmp = freq[i+4].amplitude;
			if(Math.random()>(1-scaledAmp*Config.particleMul/32))
			particles.add(new Particle().setPos(getWidth()/2 - (int)(i*barwidth), getHeight()*3/4).setVel((float) (Math.random()*200-100), -scaledAmp*2000-100)
					.setSizeAndDecay((float)Renderer.WIDTH/1920*8, (float)Renderer.WIDTH/1920*4)
					.setColor(new Color((float)Math.min(1, Math.max(0, FreqGraphRenderer.baseR+scaledAmp*FreqGraphRenderer.scaleR)),
							(float)Math.min(1, Math.max(FreqGraphRenderer.baseG+scaledAmp*FreqGraphRenderer.scaleG, 0)),
							(float)Math.min(1, Math.max(FreqGraphRenderer.baseB+scaledAmp*FreqGraphRenderer.scaleB, 0)), 0.5f)));
			if(Math.random()>(1-scaledAmp*Config.particleMul/32))
			particles.add(new Particle().setPos(getWidth()/2 + (int)(i*barwidth), getHeight()*3/4).setVel((float) (Math.random()*200-100), -scaledAmp*2000-100)
					.setSizeAndDecay((float)Renderer.WIDTH/1920*8, (float)Renderer.WIDTH/1920*4)
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
	}
	@Override
	public void componentResized(ComponentEvent e) {
		Renderer.WIDTH = this.getWidth();
		Renderer.HEIGHT = this.getHeight();
	}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
}
