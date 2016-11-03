package com.complover116.jst3;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class NewMode {
	public static void start(String filename) throws IOException {
		
		
		JFrame frame = new JFrame("JST3 - New Mode!");
		
		EffectedBG efbg = new EffectedBG(ImageIO.read(new File("data/bck.jpg")));
		frame.add(efbg);
		efbg.setPreferredSize(new Dimension(1200, 900));
		//frame.setBackground(new Color(0, true));
		efbg.setBackground(new Color(0, true));
		frame.pack();
		frame.setVisible(true);
		
		new AnalysisThread(filename).start();
		
		
		System.out.println("Loading music...");
		Clip music;
		
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File(filename+".wav")));
			music.start();
			System.out.println("Start!");
			int pos = 0;
			efbg.update(JST3.data[pos], pos);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(music.isRunning()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(music.getFramePosition()>pos*JST3.SLIDERATE) {
					pos++;
					if(JST3.data.length>pos && JST3.data[pos]!= null)
					efbg.update(JST3.data[pos], pos);
				}
			}
			System.out.println("Done");
			System.exit(0);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
