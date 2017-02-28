package GameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameOfLife extends JFrame implements Runnable,MouseListener{
	private final static Dimension WINDOW_SIZE = new Dimension(800,800);
	private int mouseX,mouseY;
	private BufferStrategy strategy;
	private int wx,wy,scale = 40; // makes the overall program easier to change
	private boolean[][] state;
	private boolean initialise = false,clicked = false;
	public GameOfLife() {
		Dimension scr = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		wx = WINDOW_SIZE.width;
		wy = WINDOW_SIZE.height;
		this.setTitle("Mouse Listener excercise");
		this.setBounds(scr.width/2 - wx/2, scr.height/2+10 - wy/2,wx, wy);
		//added 10 so it would fit in my screen
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Thread t = new Thread(this);
		t.start();
		addMouseListener(this);
		state = new boolean[40][40];
		for(int i =0;i<40;i++){
			for(int j =0;j<40;j++){
				state[i][j] = false;
			}
		}
		createBufferStrategy(2);
		strategy = this.getBufferStrategy();
		initialise = true;
	}

	public static void main(String[] args) {
		new GameOfLife();

	}
	public void paint(Graphics g){
		if(initialise){
			g = strategy.getDrawGraphics();
			for(int j =0;j<40;j++){
				for(int i = 0;i<40;i++){
					if(state[j][i]){ // check if true, paint white
						g.setColor(Color.WHITE);	
						g.fillRect(i*wx/scale,j*wy/scale,wx/scale, wy/scale);
					}
					else{ // else must be false paint black
						g.setColor(Color.BLACK);	
						g.fillRect(i*wx/scale,j*wy/scale,wx/scale, wy/scale);
					}
				}
			}
			g.dispose();
			strategy.show();
	}
		
	}	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(clicked){
					for(int j =0;j<40;j++){
						for(int i = 0;i<40;i++){
							if((mouseX >= i*wx/scale) && (mouseY >= j*wy/scale) && (mouseX < (i*wx/scale + wx/scale))
									&& (mouseY < (j*wy/scale + wx/scale))){ // detecting if mouseX and mouseY are inside state rectangle
								state[j][i] = true;
								break; // no need to loop through others if we found selected square
						}
							
					}
				}
					clicked = false;
			}
			
		this.repaint();
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		clicked = true; // alert thread that mouse is clicked
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
