package ngt_3;

import java.awt.Graphics;
import java.awt.Image;

public class Alien extends Sprite2D {
	
	private Image alien2;
	private int framesDrawn=0;
	private InvadersApplication game;
	public Alien(InvadersApplication g,Image i,Image j) {
		super(i,j);
		this.alien2 = j;
		this.game = g;
	}
	public void paint(Graphics g){//overriding the sprite2d paint class to allow for two images
		framesDrawn++;
		if ( framesDrawn%30<10 )
		g.drawImage(img,(int)x, (int)y,50,50, null);
		else
		g.drawImage(alien2, (int)x, (int)y,50,50, null);
	}
	public void move(){
		

		x+=xSpeed;
		if((x+=xSpeed)>=wx-50 && x>0){
			game.update();
		}
		if((x < 0) && (x<10)){
			game.update();
		}
	}	
	public void update(){
		xSpeed = -xSpeed;
		y+=30;
	}	
}
