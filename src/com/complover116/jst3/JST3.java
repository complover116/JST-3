package com.complover116.jst3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
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

import org.jtransforms.fft.FloatFFT_1D;

public class JST3 {
	public static final float RATE = 44100;
	public static final int SAMPLESIZE = 4410;
	public static final int SLIDERATE = 1000;
	public static final int SAMPLENUM = 100000;
	public static final int MAXFREQ = 2000;
	public static final int UNDERSAMPLING = 1;
	public static Frequency[][] data = new Frequency[SAMPLENUM][];
	public static volatile int lastAnalyzedFrame = 0;
	public static void main(String[] args) {
		System.out.println("I am JST 3!");
		
		boolean useNewMode = true;
		
		String filename = "dedede";
		if(args.length>0) filename = args[0];
		if(args.length>1) {
		FFT.fft = new FloatFFT_1D(SAMPLESIZE);
		Audio.openAudio(filename+".wav");
		SignalFrame sigframe = new SignalFrame(Audio.read(SAMPLESIZE), 0, SAMPLESIZE/UNDERSAMPLING);
		int counter = 0;
		System.out.print("Analysing");
		for(counter = 0; counter < SAMPLENUM; counter ++){
			if(counter % (int)(RATE/SLIDERATE*10) == 0)
			System.out.print('.');
			short sas[] = Audio.read(SLIDERATE);
			if(sas== null) break;
			sigframe.slide(sas);
			Frequency res[] = FFT.TransformNew(sigframe);
			res = Frequency.combine(res,100);
			
			data[counter] = res;
			
		}
		System.out.println();
		System.out.println("Saving to "+filename+".dat");
		Frequency[][] dataNew = new Frequency[counter+1][];
		for(int i = 0; i < counter; i ++) {
			dataNew[i] = data[i];
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename+".dat"));
			oos.writeObject(dataNew);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		} else {
			if(!useNewMode){
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
				graph.update(data[pos], pos);
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
					
					if(music.getFramePosition()>pos*SLIDERATE) {
						pos++;
						if(data.length>pos && data[pos]!= null)
						graph.update(data[pos], pos);
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
		} else {
			try {
				NewMode.start(filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
	}

}
