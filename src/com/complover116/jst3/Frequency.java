package com.complover116.jst3;

import java.io.Serializable;

public class Frequency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -102837265965499503L;
	/***
	 * Signal amplitude (0-1)f!
	 */
	float amplitude;
	/***
	 * Signal phase in radians!
	 */
	//float phase;
	
	//float real;
	//float img;
	int frequency;
	
	public Frequency(float real, float img, int frequency){
		this.amplitude = (float) Math.sqrt(real*real+img*img);
		//phase = (float) Math.atan2(img, real);
		//this.real = real;
		//this.img = img;
		this.frequency = frequency;
	}
	public Frequency(float amp, int frequency){
		this.amplitude = amp;
		//phase = (float) Math.atan2(img, real);
		//this.real = real;
		//this.img = img;
		this.frequency = frequency;
	}
	public static Frequency[] combine(Frequency[] in, int resolution) {
		
		Frequency out[] = new Frequency[resolution];
		
		for(int i = 0; i < resolution; i ++) {
			int num = 0;
			float sum = 0;
			for(int j = 0; j < in.length; j++) {
				if(in[j].frequency >= i*(JST3.MAXFREQ/resolution) && in[j].frequency < (i+1)*(JST3.MAXFREQ/resolution)) {
					sum += in[j].amplitude;
					num ++;
				}
				
			}
			out[i] = new Frequency(sum/num, (int) (i*(JST3.MAXFREQ/resolution)));
		}
		
		return out;
	}
	public static Frequency[] combineScaled(Frequency[] in, int resolution) {
		
		Frequency out[] = new Frequency[resolution];
		
		for(int i = 0; i < resolution; i ++) {
			int num = 0;
			float sum = 0;
			for(int j = (int) (i*(Math.ceil(i/5))); j < (i+1)*Math.ceil(i/5); j++) {
				//if(in[j].frequency >= i*(JST3.MAXFREQ/resolution) && in[j].frequency < (i+1)*(JST3.MAXFREQ/resolution)) {
					sum += in[j].amplitude;
					num ++;
				//}
				
			}
			//num = 2;
			out[i] = new Frequency(sum/num, (int) (i*(JST3.MAXFREQ/resolution)));
		}
		
		return out;
	}
}
