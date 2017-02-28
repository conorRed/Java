package ngt_3;

import java.awt.*;

import javax.swing.ImageIcon;

public class Sprite2D {
	protected Image img,img2;
	protected double x,y,xSpeed;
	protected static int wx;
	public Sprite2D(Image i,Image i2){
		this.img = i;
		this.img2 = i2;
	}

	public void paint(Graphics g){
		g.drawImage(img, (int)x, (int)y,null);
		
	}		
	public void setPosition(double xx,double yy){
		this.x = xx;
		this.y = yy;
		}

	public void setXSpeed(int sp){
		this.xSpeed  = sp;
	}
	public double getX(){return this.x;}
	public double getY(){return this.y;}
	public double getWidth(){return img.getWidth(null);}
	public double getHeight(){return img.getHeight(null);}
	public static void setWindowWidth(int windowWIDTH){
		wx = windowWIDTH;
	}

}
