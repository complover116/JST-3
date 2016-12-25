package com.complover116.jst3;

import java.io.IOException;

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
		System.out.print("Loading resources...");
		Images.load();
		System.out.println("Done!");
		
		boolean useNewMode = true;
		
		String filename = "dedede";
		if(args.length<2){
			System.out.println("Err: required params <filename> <bgfname> are missing");
			System.exit(0);
		}
		
		filename = args[0];
		if(!useNewMode){
			OldMode.useOldMode(filename);
		} else {
			
			//PARSE CLA!
			Config.playlist.add(filename);
			CLAParser.parse(args, 2);
			try {
				NewMode.start(args[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
