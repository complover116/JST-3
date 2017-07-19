package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Renderer {
	public static BufferedImage frame;
	
	public static int WIDTH = 1;
	public static int HEIGHT = 1;
	
	public static void init(int width, int height) {
		System.out.println("Renderer initialized, dimensions:"+width+':'+height);
		frame = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		WIDTH = width;
		HEIGHT = height;
	}
	
	public static void renderFrame(Frequency freq[]) {
		Graphics2D g2d = (Graphics2D) frame.getGraphics();
		instantRender(g2d, freq);
				
	}
	public static void instantRender(Graphics2D g2d, Frequency freq[]) {
		//g2d.setColor(Color.black);
		//g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setBackground(new Color(0, true));
		g2d.clearRect(0, 0, frame.getWidth(), frame.getHeight());
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
			
		float X = Config.newBgAnim ? EffectedBG.imgX : 0;
		float Y = Config.newBgAnim ? EffectedBG.imgY : 0;
		
		if(Config.jitter&&avgBassAmp>0.05) {
			X += (int)(Math.random()*avgBassAmp*200)-(int)(avgBassAmp*100);
			Y += (int)(Math.random()*avgBassAmp*200)-(int)(avgBassAmp*100);
		}
		
		AffineTransform transform = AffineTransform.getTranslateInstance(X-WIDTH*0.1, Y-HEIGHT*0.1);
		transform.concatenate(AffineTransform.getRotateInstance(EffectedBG.imgRot, Images.bg.getWidth()/2, Images.bg.getHeight()/2));
		transform.concatenate(AffineTransform.getScaleInstance((float)WIDTH/(float)Images.bg.getWidth()*1.2, (float)HEIGHT/(float)Images.bg.getHeight()*1.2));
		g2d.drawImage(Images.bg, transform, null);
		g2d.setColor(new Color(0,0,0,1f-(float)Math.min(avgAmp*16+avgBassAmp*8, 1)));
		g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		} catch (ArrayIndexOutOfBoundsException e) {
			//This is fine!	
		}
		FreqGraphRenderer.render(g2d, frame.getWidth(), frame.getHeight(), freq);
		
		for(int i = 0; i<EffectedBG.particles.size(); i ++) {
			try {
				EffectedBG.particles.get(i).draw(g2d);
			} catch (NullPointerException e) {
				//This is fine, thats due to concurrent modification
			}
		}
	}
}
