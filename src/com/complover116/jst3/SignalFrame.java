package com.complover116.jst3;

public class SignalFrame {
	int length;
	float data[];
	public SignalFrame(float data[]) {
		length = data.length;
		this.data = data;
	}
	public SignalFrame(short shortdata[], int start, int size) {
		data = new float[size];
		for(int i = 0; i < size; i++) {
			data[i] = (float)shortdata[start+i]/(float)Short.MAX_VALUE;
		}
		length = data.length;
	}
}
