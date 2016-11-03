package com.complover116.jst3;

import org.jtransforms.fft.FloatFFT_1D;

public class FFT {
	public static FloatFFT_1D fft;
	public static Frequency[] Transform(SignalFrame signal) {
		int freqnum = JST3.MAXFREQ;
		Frequency frequencies[] = new Frequency[freqnum];
		//System.out.println("FFT started");
		//Loop over all detectable frequencies
		
		for(int freq = 0; freq < freqnum; freq ++) {
			
			float localfreq = freq/JST3.RATE*signal.length;
			
		//	if(freq % 100 == 0) {
		//		System.out.println("Now at "+freq);
		//	}
			float real_part = 0;
			float imaginary_part = 0;
			
			//For each datapoint
			for(int time = 0; time < signal.length; time ++) {
				float rate = (float) (-2*Math.PI*localfreq);
				//How far should the phase be shifted
				float distance = ((float)time/(float)signal.length) * rate;
				
				real_part += signal.data[time]*Math.cos(distance);
				imaginary_part += signal.data[time]*Math.sin(distance);
			}
			
			
			//if(Math.abs(real_part)>0 && Math.abs(imaginary_part)>0) {
			real_part /= signal.length;
			imaginary_part /= signal.length;
			if(Math.abs(imaginary_part)<1e-9) {
				imaginary_part = 0;
			}
			if(Math.abs(real_part)<1e-9) {
				real_part = 0;
			}
			frequencies[freq] = new Frequency(real_part, imaginary_part, freq);
			//}
			
		}
		//System.out.println("FFT completed");
		return frequencies;
	}
	public static Frequency[] TransformNew(SignalFrame signal) {
		Frequency frequencies[] = new Frequency[JST3.MAXFREQ];
		float input[] = new float[signal.length*2];
		for(int i = 0; i < signal.length; i ++)
			input[i*2] = signal.data[i];
		fft.complexForward(input);
		
		for(int i = 0; i < JST3.MAXFREQ; i ++) {
			frequencies[i] = 
					new Frequency((float) Math.sqrt(input[i*2]*input[i*2] + input[i*2+1]*input[i*2+1])/2500, (int) (i*JST3.RATE/signal.length));
		}
		return frequencies;
	}
	
}
