package ngt_2;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class MovingSquaresApplication extends JFrame implements Runnable{
	
	private final static Dimension WINDOW_SIZE = new Dimension(600,600);
	private static final int NUM_OBJECTS = 30;
	private GameObject[] GameObjectArray = new GameObject[NUM_OBJECTS];
	private Thread t;
	
		public MovingSquaresApplication(){
			this.setTitle("Moving Colours");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		for(int i =0;i<NUM_OBJECTS;i++){
			GameObjectArray[i] = new GameObject();
		}
		t = new Thread(this);
		t.start();
			Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			int x = screensize.width/2 - WINDOW_SIZE.width/2;
			int y = screensize.height/2 - WINDOW_SIZE.height/2;
			setBounds(x, y, WINDOW_SIZE.width, WINDOW_SIZE.height);
			setResizable(false);
			setVisible(true);
			
		}
		public void paint(Graphics g){
			super.paintComponents(g);
			for(int i =0;i<NUM_OBJECTS;i++){
				GameObjectArray[i].paint(g);
			}
		}
		public static void main(String[] args){
			new MovingSquaresApplication();
			
		}
		@Override
		public void run() {
			while(true){
			try {
				Thread.sleep(500);
				for(int i =0;i<NUM_OBJECTS;i++){
					GameObjectArray[i].move();
				}
			
				this.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			
			}
		}
}
