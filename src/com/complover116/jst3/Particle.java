package com.complover116.jst3;

import java.awt.Color;
import java.awt.Graphics2D;

public class Particle {
	float x,y,velX,velY,size,decay;
	Color color;
	boolean dead = false;
	public void tick(double deltaT) {
		this.x += velX*deltaT;
		this.y += velY*deltaT;
		this.velY += deltaT*1600;
		this.size -= this.decay*deltaT;
		if(this.size < 0) this.dead = true;
	}
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect((int)(x-size), (int)(y-size), (int)(size*2), (int)(size*2));
	}
}
