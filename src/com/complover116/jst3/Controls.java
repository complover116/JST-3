package com.complover116.jst3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.FloatControl;

public class Controls implements KeyListener {

	@Override
	public void keyPressed(KeyEvent arg0) {
		//System.out.println("sas");
		switch(arg0.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			if(Metadata.volume > 0) {
				Metadata.volume -= 0.05;
			}
			if(NewMode.music!=null){
				FloatControl volControl = 
					    (FloatControl) NewMode.music.getControl(FloatControl.Type.MASTER_GAIN);
				volControl.setValue((float) Math.log10(Metadata.volume+0.001)*20);
			}
			EffectedBG.volumeFade = 3;
		break;
		case KeyEvent.VK_UP:
			if(Metadata.volume < 1) {
				Metadata.volume += 0.05;
			}
			if(NewMode.music!=null){
				FloatControl volControl = 
					    (FloatControl) NewMode.music.getControl(FloatControl.Type.MASTER_GAIN);
				volControl.setValue((float) Math.log10(Metadata.volume+0.001)*20);
			}
			EffectedBG.volumeFade = 3;
		break;
		case KeyEvent.VK_SPACE:
			if(NewMode.music.isRunning())
			NewMode.music.stop();
			else
			NewMode.music.start();
		break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
