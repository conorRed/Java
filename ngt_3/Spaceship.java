package ngt_3;

import java.awt.Image;

public class Spaceship extends Sprite2D{

	public Spaceship(Image i){
		super(i,null);
		
	}
	public void move(){
		x+=xSpeed;
		if(x<=0){
			x=0;
			xSpeed=0;
		}
		else if(x>=wx)
			x=wx;
	}
}
