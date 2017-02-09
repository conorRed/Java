package ngt_3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class InvadersApplication extends JFrame implements Runnable,KeyListener{
	private Image alien,spaceShip,alien2,bulletImage;
	private BufferStrategy strategy;
	private PlayerBullet bullet;
	private static boolean initialise = false;
	private Thread t;
	private static final int NUM_ALIENS_rows = 3,NUM_ALIENS_columns =3;
	private static final Dimension WINDOW_SIZE = new Dimension(600,700);
	private static Dimension screensize;
	private static String directory;
	private Alien[][] aliens = new Alien[NUM_ALIENS_rows][NUM_ALIENS_columns];
	private Spaceship ship;
	
	private int wx,wy; 
/***************************************************************************/
//Invaders application constructor
public InvadersApplication(){
		//setting up screen
		screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		wx = WINDOW_SIZE.width;
		wy = WINDOW_SIZE.height;
		setBounds(screensize.width/2 - wx/2,screensize.height/2 - wy/2,wx,wy);
		this.setTitle("Space Invaders");
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Instantiating all the alien , spaceship and bullet objects
		alien = new ImageIcon(directory + "\\Spaceinvaders.png").getImage();
		alien2 = new ImageIcon(directory + "\\alien2.png").getImage();
		spaceShip = new ImageIcon(directory + "\\player_ship.png").getImage();
		bulletImage = new ImageIcon(directory + "\\bullet.png").getImage();
		for (int j = 0; j < NUM_ALIENS_rows; j++) {
				for (int i = 0; i < NUM_ALIENS_columns; i++) {
					aliens[j][i] = new Alien(this,alien,alien2);
					aliens[j][i].setPosition(i*60,(j)*50);
					aliens[j][i].setXSpeed(2);			
				} 
			}
		
		ship = new Spaceship(spaceShip);
		ship.setPosition(wx/2-25, wy-70);
		Sprite2D.setWindowWidth(wx);
		addKeyListener(this);
		
		//defining main thread and starting it
		t = new Thread(this);
		t.start();
		
		//creating a buffer strategy and signaling completion of constructor
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		initialise = true; //make sure everything is initialize before we try and paint
	}

/***********************************************************************************************/
//Main method and paint method
	public static void main(String[] args) {
		directory = System.getProperty("user.dir");
		//System.out.println(directory);
		new InvadersApplication();
	}
	public void paint(Graphics g){
		if(initialise){
		g = strategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,wx,wy);
		for (int j = 0; j < NUM_ALIENS_rows; j++) {
			for (int i = 0; i < NUM_ALIENS_columns; i++) {
				aliens[j][i].paint(g);
			} 
		}
		ship.paint(g);
		g.dispose();
		strategy.show();
		}
	}
/***********************************************************************************************/
//Run method for thread that constantly updates the game
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);

				for (int j = 0; j < NUM_ALIENS_rows; j++) {
					for (int i = 0; i < NUM_ALIENS_columns; i++) {
						aliens[j][i].move();
					} 
				}
				ship.move();
				shootBullet();
				this.repaint();
		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
/**********************************************************************************************/
	//methods to change external classes: move alien sprites and shoot bullets
	
public void update(){
	for (int j = 0; j < NUM_ALIENS_rows; j++) {
		for (int i = 0; i < NUM_ALIENS_columns; i++) {
			aliens[j][i].update();
		} 
	}
}

public void shootBullet(){
			bullet = new PlayerBullet(bulletImage,ship.getX()+ship.getWidth(),ship.getY()+ship.getHeight());
			bullet.setXSpeed(10);
			bullet.move();
	

	
/***********************************************************************************************/
//Key Listeners and intractability  

	@Override
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_RIGHT){		
		ship.setXSpeed(10);
	}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			ship.setXSpeed(-10);	
	}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			bulletActivate = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
			ship.setXSpeed(0);
		}
	
			
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
