package com.complover116.jst3;

import org.jtransforms.fft.FloatFFT_1D;

public class AnalysisThread extends Thread  {
	String filename;
	public AnalysisThread(String fname) {
		filename = fname;
	}
	public void run() {
		System.out.println("Sound analysis thread started");
		FFT.fft = new FloatFFT_1D(JST3.SAMPLESIZE);
		Audio.openAudio(filename+".wav");
		SignalFrame sigframe = new SignalFrame(Audio.read(JST3.SAMPLESIZE), 0, JST3.SAMPLESIZE/JST3.UNDERSAMPLING);
		int counter = 0;
		for(counter = 0; counter < JST3.SAMPLENUM; counter ++){
			//if(counter % (int)(JST3.RATE/JST3.SLIDERATE*10) == 0)
			//System.out.println('.');
			short sas[] = Audio.read(JST3.SLIDERATE);
			if(sas== null) break;
			sigframe.slide(sas);
			Frequency res[] = FFT.TransformNew(sigframe);
			res = Frequency.combine(res,100);
			
			JST3.data[counter] = res;
			JST3.lastAnalyzedFrame = counter;
			//Will try its best without stopping, possibly temporary behaviour
			/*if(FreqGraphRenderer.overhead>1000) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
		System.out.println("Sound analysis is complete!");
	}
}
