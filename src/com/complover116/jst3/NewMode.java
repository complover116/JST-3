package com.complover116.jst3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class NewMode {
	public static Clip music;
	static EffectedBG efbg;
	public static void start(String bgfname) throws IOException {
		
		System.out.print("NewMode selected, initializing graphics...");
		
		JFrame frame = new JFrame("JST3 - New Mode!");
		if(!Config.windowDecoration) frame.setUndecorated(true);
		if(Config.fullscreen) frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File(bgfname));
		} catch (IOException e) {
				System.out.println("Failed!");
				e.printStackTrace();
				System.out.println("The backround image file is missing!");
				System.exit(0);
		}
		efbg = new EffectedBG(img);
		
		float avgR = 0, avgG = 0, avgB = 0;
		WritableRaster raster = img.getRaster();
		float rgba[] = new float[4];
		for(int i = 0; i < img.getWidth(); i ++)
			for(int j = 0; j < img.getHeight(); j ++){
				raster.getPixel(i, j, rgba);
				avgR += rgba[0];
				avgG += rgba[1];
				avgB += rgba[2];
			}
		avgR /= img.getWidth()*img.getHeight()*255;
		avgG /= img.getWidth()*img.getHeight()*255;
		avgB /= img.getWidth()*img.getHeight()*255;
		
		/*float minColorValue = Math.min(avgR, Math.min(avgB, avgG));
		
		avgR -= minColorValue;
		avgG -= minColorValue;
		avgB -= minColorValue;*/
		//Different system which does not mess up the color balance
		if(avgR < avgG) {
			if(avgR<avgB) {
				avgR = 0;
			} else {
				avgB = 0;
			}
		} else {
			if(avgB<avgG) {
				avgB = 0;
			} else {
				avgG = 0;
			}
		}
		
		
		
		float maxColorValue = Math.max(avgR, Math.max(avgG, avgB));
		
		avgR *= 0.5f/maxColorValue;
		avgG *= 0.5f/maxColorValue;
		avgB *= 0.5f/maxColorValue;
		
		FreqGraphRenderer.baseR = avgR;
		FreqGraphRenderer.baseG = avgG;
		FreqGraphRenderer.baseB = avgB;
		
		//This will only let the brightest color become the base
		if(avgR < avgG) {
			if(avgB<avgG) {
				FreqGraphRenderer.baseR = 0;
				FreqGraphRenderer.baseB = 0;
			} else {
				FreqGraphRenderer.baseR = 0;
				FreqGraphRenderer.baseG = 0;
			}
		} else {
			if(avgB<avgR) {
				FreqGraphRenderer.baseG = 0;
				FreqGraphRenderer.baseB = 0;
			} else {
				FreqGraphRenderer.baseG = 0;
				FreqGraphRenderer.baseR = 0;
			}
		}
		
		
		FreqGraphRenderer.scaleR = avgR*16;
		FreqGraphRenderer.scaleG = avgG*16;
		FreqGraphRenderer.scaleB = avgB*16;
		
		
		
		frame.add(efbg);
		efbg.setPreferredSize(new Dimension(1200, 900));
		//frame.setBackground(new Color(0, true));
		efbg.setBackground(new Color(0, true));
		frame.pack();
		frame.setVisible(true);
		System.out.println("Done!");
		
		
		
		System.out.println("Avg RGB: "+Math.round(avgR*255)+":"+Math.round(avgG*255)+":"+Math.round(avgB*255));
		int id = 0;
		while(Config.playlist.size()>0){
			
			if(Config.shuffle) id = (int)(Math.random()*Config.playlist.size());
			else {
				
				if(!Config.oneshot) {
					id++;
					if(id >= Config.playlist.size()) id = 0;
				}
			}
			switchMusic(Config.playlist.get(id));
			if(Config.oneshot)Config.playlist.remove(id);
		}
		System.exit(0);
	}
	public static void switchMusic(String filename){
		System.out.println("Loading filename...");
		new AnalysisThread(filename).start();
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File(filename+".wav")));
			FloatControl volControl = 
				    (FloatControl) NewMode.music.getControl(FloatControl.Type.MASTER_GAIN);
			volControl.setValue((float) Math.log10(Metadata.volume+0.001)*20);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			music.start();
			
			System.out.println("Start!");
			int pos = 0;
			efbg.update(JST3.data[pos], pos);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while(music.getMicrosecondPosition() < music.getMicrosecondLength()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				while(music.getFramePosition()>pos*JST3.SLIDERATE) {
					pos++;
				}
				if(JST3.data.length>pos && JST3.data[pos]!= null)
					efbg.update(JST3.data[pos], pos);
			}
			System.out.println("Done");
			
		} catch (LineUnavailableException e) {
			System.out.println("Failed!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed!");
			e.printStackTrace();
			System.out.println("The sound file is missing!");
			System.exit(2);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Failed!");
			e.printStackTrace();
		}
	}
}
