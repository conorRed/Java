package ngt_2;

import java.awt.Color;
import java.awt.Graphics;

public class GameObject {
	private int x,y;
	private int rand1,rand2,rand3;
	private int w = 30, h = 30;
	private Color c;
	
	public GameObject(){
		x = (int) (Math.random()*600);
		y = (int) (Math.random()*600);
		rand1 = (int)(Math.random()*256);
		rand2 = (int)(Math.random()*256);
		rand3 = (int)(Math.random()*256);
		c = new Color(rand1,rand2,rand3);
		
	}
	public void move(){
		x = (int) (Math.random()*600);
		y = (int) (Math.random()*600); 
	}
	public void paint(Graphics g){
		g.setColor(c);
		g.fillRect((int)(Math.random()*600),(int)(Math.random()*600), 60, 60);
	}
}
