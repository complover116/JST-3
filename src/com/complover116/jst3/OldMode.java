package com.complover116.jst3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class OldMode {
	public static void useOldMode(String filename) {
		Window frame = new Window(null);
		FreqGraph graph = new FreqGraph();
		frame.add(graph);
		graph.setPreferredSize(new Dimension(500, 1000));
		frame.setAlwaysOnTop(true);
		frame.setBounds(frame.getGraphicsConfiguration().getBounds());
		frame.setBackground(new Color(0, true));
		graph.setBackground(new Color(0, true));
		frame.setVisible(true);
		
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*System.out.println("Loading from "+filename+".dat");
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename+".dat"));
			data = (Frequency[][]) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		new AnalysisThread(filename).start();
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		System.out.println("Loading music...");
		Clip music;
		
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File(filename+".wav")));
			music.start();
			System.out.println("Start!");
			int pos = 0;
			graph.update(JST3.data[pos], pos);
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
					graph.update(JST3.data[pos], pos);
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
