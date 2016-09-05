package com.complover116.jst3;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JST3 {
	public static final float RATE = 44100;
	public static final float MAXFREQ = 2000;
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
		Audio.openAudio("E:/Dev/JST3/440hz.wav");
		Frequency res[] = FFT.Transform(new SignalFrame(Audio.read(11025), 0, 11025));
		/*for(int i = 0; i < res.length; i ++) {
			if(Math.abs(res[i].amplitude)>1e-5) {
				System.out.println(res[i].frequency+":"+res[i].amplitude);
			}
		}*/
		graph.update(Frequency.combine(res,200));
	}

}
