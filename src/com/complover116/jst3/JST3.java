package com.complover116.jst3;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JST3 {
	public static final float RATE = 44100;
	public static final int SAMPLESIZE = 4410;
	public static final float MAXFREQ = 2000;
	public static Frequency[][] data = new Frequency[100][];
	public static void main(String[] args) {
		System.out.println("I am JST 3!");
		JFrame frame = new JFrame("JST3");
		JPanel panel = new JPanel();
		frame.add(panel);
		FreqGraph graph = new FreqGraph();
		panel.add(graph);
		graph.setPreferredSize(new Dimension(1000, 500));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(false) {
		Audio.openAudio("E:/Dev/JST3/dedede.wav");
		for(int i = 0; i < 100; i ++){
			Frequency res[] = FFT.Transform(new SignalFrame(Audio.read(SAMPLESIZE), 0, SAMPLESIZE));
			res = Frequency.combine(res,200);
			graph.update(res);
			data[i] = res;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("out.dat"));
			oos.writeObject(data);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} else {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("out.dat"));
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
			}
		}
		Clip music;
		
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(new File("E:/Dev/JST3/dedede.wav")));
			music.start();
			int pos = 0;
			graph.update(data[pos]);
			while(music.isRunning()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(music.getFramePosition()>pos*SAMPLESIZE) {
					pos++;
					graph.update(data[pos]);
				}
			}
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
