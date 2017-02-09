package ngt_3;

import java.awt.Graphics;
import java.awt.Image;

public class PlayerBullet extends Sprite2D{

	public PlayerBullet(Image i,double x,double y) {
		super(i,null);
		this.x = x;
		this.y = y;
		
	}
	public void move(){
		y-= xSpeed;
	}
	public void paint(Graphics g){
		g.drawImage(img, (int)x, (int)y,null);
	}	

}
