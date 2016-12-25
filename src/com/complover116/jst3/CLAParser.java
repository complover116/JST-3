package com.complover116.jst3;

public class CLAParser {

	public static Float getFloat(String args[], int pos, int argpos) {
		try {
			return Float.parseFloat(getString(args, pos, argpos));
		} catch (NumberFormatException e) {
			System.out.println("Incorrect command line arguments!");
			System.out.println("Argument " + args[argpos] + " expected " + args[pos] + " to be a number!");
			System.exit(1);
			return null;
		}
	}

	public static String getString(String args[], int pos, int argpos) {
		if (pos<args.length)
			return args[pos];
		System.out.println("Incorrect command line arguments!");
		System.out.println("Argument " + args[argpos] + " requires more parameters at "+pos+"!");
		System.exit(1);
		return null;
	}

	public static void parse(String args[], int pos) {
		int argpos = pos;
		while(pos<args.length){
		argpos = pos;
		switch (args[pos]) {
		case "-nojitter":
			Config.jitter = false;
			System.out.println("CLA:BGI jitter disabled");
			break;
		case "-particlemul":
			pos++;
			Config.particleMul = getFloat(args, pos, argpos);
			System.out.println("CLA:Particle mul set to "+Config.particleMul);
			break;
		case "-nodecoration":
			Config.windowDecoration = false;
			System.out.println("CLA:Window decoration disabled");
			break;
		case "-fullscreen":
			Config.fullscreen = true;
			System.out.println("CLA:Program will start maximized");
			break;
		case "-forcecolors":
			
			pos++;FreqGraphRenderer.baseR = getFloat(args, pos, argpos);;
			pos++;FreqGraphRenderer.baseG = getFloat(args, pos, argpos);;
			pos++;FreqGraphRenderer.baseB = getFloat(args, pos, argpos);;
			pos++;FreqGraphRenderer.scaleR = getFloat(args, pos, argpos);
			pos++;FreqGraphRenderer.scaleG = getFloat(args, pos, argpos);
			pos++;FreqGraphRenderer.scaleB = getFloat(args, pos, argpos);
			Config.forceColors = true;
			System.out.println("CLA:Colors are forced");
			break;
		default:
			System.out.println("Incorrect command line arguments!");
			System.out.println("Argument " + args[pos] + " was not recognized!");
			System.exit(1);
			break;
		}
		pos++;
		}
	}
}