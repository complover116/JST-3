package com.complover116.jst3;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	static AudioInputStream din = null;
	public static int numChannels = 0;
	public static void openAudio(String filename) {
		File file = new File(filename);
		AudioInputStream in;
		try {
			in = AudioSystem.getAudioInputStream(file);
			
			AudioFormat baseFormat = in.getFormat();
			AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
					1, 2, baseFormat.getSampleRate(), true);
			din = AudioSystem.getAudioInputStream(decodedFormat, in);
			numChannels = baseFormat.getChannels();
			System.out.println("Opened "+filename);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static short[] read(int amount) {
		ArrayList<Short> data = new ArrayList<Short>();
		int numread = 0;
		while(true) {
			numread++;
			byte bytein[] = new byte[2];
			ByteBuffer buf = ByteBuffer.wrap(bytein);
			try {
				if(numread > amount) {
					
					break;
				} else if(din.read(bytein) == -1){
					return null;
				} else {
					if(numread%JST3.UNDERSAMPLING==0)
					data.add(buf.getShort());
					
					//Skip the extra channels
					/*for(int i = 1; i < 2; i++){
						din.read(bytein);
					}*/
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		short array[] = new short[data.size()];
		//System.out.println("Read "+array.length+" datapoints ("+array.length*2+" raw bytes)");
		for(int i = 0; i < array.length; i ++) {
			array[i] = data.get(i);
		}
		return array;
	}
}
