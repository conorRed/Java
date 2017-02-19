package ngt_3;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerBullet extends Sprite2D{
private InvadersApplication game;

int bulletLeft;		//specifies hit box for bullet
int bulletRight;
int bulletTop;
int bulletBottom;
public boolean alive = true;

	public PlayerBullet(InvadersApplication g,Image i) {
		super(i,i);
		this.game = g;//game its being used in
		
	}
	public void move(){
		y -= xSpeed;
		
		if(y <= 10){
			game.removeSprite(this);
		}
	}

	public Sprite2D collide(ArrayList<Alien> s){
		bulletLeft = (int) this.getX();
		bulletRight = (int) (this.getX()+this.getWidth());
		bulletTop = (int) this.getY();
		bulletBottom = (int) (this.getY()+this.getHeight());
		for(Alien a : s){
				if(bulletBottom >= a.getY() && bulletTop <= (a.getY()+a.getHeight()) && bulletLeft< a.getX()+a.getWidth() && bulletRight>a.getX())
					return a;
			} 
		return null;
	}

}
