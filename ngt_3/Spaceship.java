package ngt_3;

import java.awt.Image;
import java.util.ArrayList;

public class Spaceship extends Sprite2D{
	
	int shipLeft,shipRight,shipBottom,shipTop;

	public Spaceship(Image i){
		super(i,null);
		
	}
	public void move(){
		x+=xSpeed;
		if(x<=0){
			x=0;
			xSpeed=0;
		}
		else if(x>=wx-50)
			x=wx-50;
	}
	public boolean collide(ArrayList<Alien> s){
		shipLeft = (int) this.getX();
		shipRight = (int) (this.getX()+this.getWidth());
		shipTop = (int) this.getY();
		shipBottom = (int) (this.getY()+this.getHeight());
		for(Alien a : s){
				if(shipBottom >= a.getY() && shipTop <= (a.getY()+a.getHeight()) && shipLeft< a.getX()+a.getWidth() && shipRight>a.getX())
					return true;	
			} 
		return false;
	}
}
