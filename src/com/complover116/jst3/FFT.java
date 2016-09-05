package com.complover116.jst3;

public class FFT {
	public static Frequency[] Transform(SignalFrame signal) {
		int freqnum = 1000;
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
	public static Frequency[] TransformAndNormalize(SignalFrame signal) {
		Frequency res[] = Transform(signal);
		for(Frequency f : res) {
			//f.frequency
		}
		return res;
	}
}
