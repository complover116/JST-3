package com.complover116.jst3;

public class CLITest {
	public static void main(String args[]) {
		Frequency freq[] = FFT.Transform(new SignalFrame(new float[]{1, 0, 0, 1}));
		for(int i = 0; i < freq.length; i ++) {
			System.out.println(freq[i].amplitude);
		}
	}
}
