package com.complover116.jst3;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
	public static BufferedImage volume;
	
	public static void load() {
		try {
			volume = ImageIO.read(Images.class.getResourceAsStream("/img/volume.png"));
		} catch (IOException e) {
			System.out.println("Failed!");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
