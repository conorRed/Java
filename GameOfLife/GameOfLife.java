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
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.io.*;
import javax.swing.JFrame;

public class GameOfLife extends JFrame implements Runnable,MouseListener,MouseMotionListener{
	private final static Dimension WINDOW_SIZE = new Dimension(800,800);
	private String filename = "C:\\Users\\Conor Redington\\workspace\\NGT_2\\life.txt";
	private int mouseX,mouseY;
	private BufferStrategy strategy;
	Font myFont = new Font("Serif", Font.BOLD, 15); // creating simple for for boxes
	private Graphics offscreenBuffer;
	private int wx,wy,scale = 40; // makes the overall program easier to change
	private boolean[][][] state; // 3d array to implement two states of values on screen so they are not altering each other constantly
	private int mainBuffer,backBuffer;
	private boolean initialise = false,gameRunning=false;
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
		addMouseMotionListener(this);
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
				state[i][j][mainBuffer]= false;
				state[i][j][backBuffer] =false;
			}
		}
	}

	public static void main(String[] args) {
		new GameOfLife();

	}
	// method to save states into file: Active being a '1' and inActive a '0'
	public void save(){
	
		try{
			BufferedWriter writer = new	BufferedWriter(new FileWriter(filename));
			for(int i= 0;i<40;i++){
				for(int j=0;j<40;j++){
					if(state[i][j][mainBuffer]){
						writer.write('1');
					}
					else{
						writer.write('0');
					}
				}
			}
			System.out.println("file write successful"); // confirmation of file write. Could also display this on screen

			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	// method to load: read in file and set states as 1 and 0 accordingly
	public void load(){
		int count = 0,i=0,j=0,allcount=0;
		String textinput = null;
		try {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		// read file into String
		while(reader.readLine() != null){
			textinput += reader.readLine();
			
		}
		for(int c =0;c<textinput.length();c++){
			// use counter to iterate through String
			if(count==39){// keeps track of when the i value should be changed i.e we move down a row
				i++;
			}
			if(textinput.charAt(c) == '1'){ // if char equals 1 we set its corresponding state to active
				state[i][j][mainBuffer]=state[i][j][backBuffer] = true;
			}
			
			j = (j+1) % 40; // increment column value
			count = (count + 1) %40;
			
			
		}
	
		reader.close();
		this.repaint();

		}
		catch (IOException e) { }

	}
	
	public void paint(Graphics g){
		if(initialise){
					g = offscreenBuffer;
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, wx, wy);
					g.setFont(myFont);
				backBuffer = (mainBuffer + 1) % 2;
				if(gameRunning){
						
							for (int x=0;x<40;x++) {
								for (int y=0;y<40;y++) {
									int count = 0;	
									for (int xx=-1;xx<=1;xx++) {
										for (int yy=-1;yy<=1;yy++) {
											if (xx!=0 || yy!=0) {
												try{
													//check current state of screen 
													if(state[x+xx][y+yy][mainBuffer]){	
																count++;	
												}
											}
												catch ( ArrayIndexOutOfBoundsException f)
											       {continue;}
												
											}
											//implementing rules of game on next state of screen
											if(count > 3 || count < 2){
												state[x][y][backBuffer] = false;
											}
											if(count == 3){
												state[x][y][backBuffer] = true;
											}
											if(count == 2){
												state[x][y][backBuffer] = state[x][y][mainBuffer];
											}
									}
								}
								
							}
						}
						mainBuffer = backBuffer;
				}
					//loop through and render all from back buffer to front buffer
					g.setColor(Color.WHITE);
						for(int j =0;j<40;j++){
							for(int i = 0;i<40;i++){
								if(state[j][i][mainBuffer]){
									g.fillRect(i*wx/scale,j*wy/scale,wx/scale, wy/scale);
								}
							}
						}
							//render start and stop and random buttons
							//Don't want states to form over it so rendering after states
							g.setColor(Color.GREEN);
							g.fillRect(30, 40, 80, 20);
							g.fillRect(120, 40, 80, 20);
							g.fillRect(210, 40, 80, 20);
							g.fillRect(300, 40, 80, 20);
							g.setColor(Color.WHITE);
							//want stop button to appear when game running
							if(!gameRunning)
								g.drawString("Start", 35, 55);
							if(gameRunning)
								g.drawString("Stop", 35, 55);
							g.drawString("Random", 125, 55);
							g.drawString("Save", 215, 55);
							g.drawString("Load", 305, 55);
					
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
			if(e.getX()>=30 && e.getX()<=110 && e.getY()>40 && e.getY()<60){
				gameRunning = !gameRunning;
				return;
			}
			if(e.getX()>=120 && e.getX()<=200 && e.getY()>40 && e.getY()<60){
				randomStates();
				return;
			}
			if(e.getX()>=210 && e.getX()<=290 && e.getY()>40 && e.getY()<60){
				save();
				return;
			}
			if(e.getX()>=300 && e.getX()<=380 && e.getY()>40 && e.getY()<60){
				load();
				return;
			}
		
			state[mouseY][mouseX][mainBuffer] = !state[mouseY][mouseX][mainBuffer];	
			this.repaint();
	
	}
	
	//creates true states at random positions when random is clicked
	public void randomStates(){
		// reset in case called multiple times
		for(int i =0;i<40;i++){
			for(int j =0;j<40;j++){
				state[i][j][mainBuffer]= false;
				state[i][j][backBuffer] =false;
			}
		}
		for ( int i = 0; i < 1000; i++ ){
			int x = (int)(Math.random() * 40);
			int y = (int)(Math.random() * 40);
			state[x][y][mainBuffer] = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX()/(wx/scale);
		mouseY = e.getY()/(wx/scale);
		//two if statements to check if inside the start or random region
		
			state[mouseY][mouseX][mainBuffer] = true;	
			this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
