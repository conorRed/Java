package GameOfLife;
/*
 * Author: Conor Redington
 * Simple implementation of conway's game of life.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameOfLife extends JFrame implements Runnable,MouseListener{
	private final static Dimension WINDOW_SIZE = new Dimension(800,800);
	private int mouseX,mouseY;
	private BufferStrategy strategy;
	Font myFont = new Font("Serif", Font.BOLD, 15); // creating simple for for boxes
	private Graphics offscreenBuffer;
	private int wx,wy,scale = 40; // makes the overall program easier to change
	private boolean[][][] state; // 3d array to implement two states of values on screen so they are not altering each other constantly
	private static boolean initialise = false,gameRunning=false;
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
		initState();
		createBufferStrategy(2);
		strategy = this.getBufferStrategy();
		offscreenBuffer = strategy.getDrawGraphics();
		initialise = true;
	}
	
	public void initState(){
		state = new boolean[40][40][2];
		for(int i =0;i<40;i++){
			for(int j =0;j<40;j++){
				state[i][j][1] = false;
			}
		}
	}

	public static void main(String[] args) {
		new GameOfLife();

	}
	
	public void paint(Graphics g){
		if(initialise){
					g = offscreenBuffer;
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, wx, wy);
					g.setFont(myFont);
				
				if(gameRunning){
						
							for (int x=0;x<40;x++) {
								for (int y=0;y<40;y++) {
									int count = 0;	
									for (int xx=-1;xx<=1;xx++) {
										for (int yy=-1;yy<=1;yy++) {
											if (xx!=0 || yy!=0) {
												try{
													//check current state of screen 
													if(state[x+xx][y+yy][1]){	
																count++;	
												}
											}
												catch ( ArrayIndexOutOfBoundsException f)
											       {continue;}
												
											}
											//implementing rules of game on next state of screen
											if(count > 3 || count < 2){
												state[x][y][0] = false;
											}
											if(count == 3){
												state[x][y][0] = true;
											}
											if(count == 2){
												state[x][y][0] = state[x][y][1];
											}
									}
								}
								
							}
						}
				
				}
					//loop through and render all from back buffer to front buffer
					g.setColor(Color.WHITE);
						for(int j =0;j<40;j++){
							for(int i = 0;i<40;i++){
								state[j][i][1] = state[j][i][0];
								if(state[j][i][1]){
									g.fillRect(i*wx/scale,j*wy/scale,wx/scale, wy/scale);
								}
							}
						}
							//render start and stop and random buttons
							//Don't want states to form over it so rendering after states
							g.setColor(Color.GREEN);
							g.fillRect(30, 40, 80, 20);
							g.fillRect(120, 40, 80, 20);
							g.setColor(Color.WHITE);
							//want stop button to appear when game running
							if(!gameRunning)
								g.drawString("Start", 35, 55);
							if(gameRunning)
								g.drawString("Stop", 35, 55);
							g.drawString("Random", 125, 55);
							
					strategy.show();
				}
		
	
	}	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		this.repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX()/(wx/scale);
		mouseY = e.getY()/(wx/scale);
		//two if statements to check if inside the start or random region
			if(e.getX()>=30 && e.getX()<=100 && e.getY()>40 && e.getY()<60){
				gameRunning = !gameRunning;
				return;
			}
			if(e.getX()>=110 && e.getX()<=170 && e.getY()>40 && e.getY()<60){
				randomStates();
				return;
			}
			state[mouseY][mouseX][0] = !state[mouseY][mouseX][0];	
			this.repaint();
	
	}
	
	//creates true states at random positions when random is clicked
	public void randomStates(){
		for ( int i = 0; i < 1000; i++ ){

			int x = (int)(Math.random() * 40);

			int y = (int)(Math.random() * 40);
			state[x][y][0] = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
