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
			System.out.println("CLA:Background jitter disabled");
			break;
		case "-oldanim":
			Config.newBgAnim = false;
			System.out.println("CLA:Disabled new background animation");
			break;
		case "-particlemul":
			pos++;
			Config.particleMul = getFloat(args, pos, argpos);
			System.out.println("CLA:Particle mul set to "+Config.particleMul);
			break;
		case "-fps":
			pos++;
			Config.FRAMERATE = getFloat(args, pos, argpos);
			System.out.println("CLA:Target FPS set to "+Config.FRAMERATE);
			break;
		case "-reporttime":
			pos++;
			Config.reportTime = getFloat(args, pos, argpos);
			System.out.println("CLA:Statistics are reported every "+Config.reportTime+" seconds");
			break;
		case "-delay":
			pos++;
			Config.delay = getFloat(args, pos, argpos);
			System.out.println("CLA:Will delay for "+Config.delay);
			break;
		case "-unlimitedfps":
			Config.FRAMERATE = Float.POSITIVE_INFINITY;
			System.out.println("CLA:FPS limit disabled: target FPS set to "+Config.FRAMERATE);
			break;
		case "-nodecoration":
			Config.windowDecoration = false;
			System.out.println("CLA:Window decoration disabled");
			break;
		case "-playlist":
			pos++;
			String playlist = getString(args, pos, argpos);
			System.out.println("CLA:Reading playlist from "+playlist);
			Config.loadPlayList(playlist);
			break;
		case "-fullscreen":
			Config.fullscreen = true;
			System.out.println("CLA:Program will start maximized");
			break;
		case "-shuffle":
			Config.shuffle = true;
			System.out.println("CLA:Shuffle mode enabled");
			break;
		case "-showfps":
			Config.showFPS = true;
			System.out.println("CLA:FPS Counter enabled");
			break;
		case "-endless":
			Config.oneshot = false;
			System.out.println("CLA:The playlist will be repeated forever");
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