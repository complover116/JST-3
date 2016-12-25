package com.complover116.jst3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Config {
	public static float particleMul = 1f;
	public static boolean jitter = true;
	public static boolean forceColors = false;
	public static boolean windowDecoration = true;
	public static boolean fullscreen = false;
	public static ArrayList<String> playlist = new ArrayList<String>();
	public static boolean shuffle = false;
	public static boolean oneshot = true;
	public static void loadPlayList(String playlist2) {
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(playlist2)));
			String line;
			playlist.clear();
			while((line = in.readLine()) != null) {
				playlist.add(line);
				System.out.println("Added "+line+" to the playlist");
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Error: could not read playlist "+playlist2+"!");
			System.exit(2);
		}
	}
}
