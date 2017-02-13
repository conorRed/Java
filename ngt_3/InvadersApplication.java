package ngt_3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class InvadersApplication extends JFrame implements Runnable,KeyListener{
	private Image alien,spaceShip,alien2,bulletImage;
	private ArrayList<PlayerBullet> removebullets = new ArrayList<PlayerBullet>();
	private ArrayList<PlayerBullet> bullets;
	private ArrayList<Alien> alienlist = new ArrayList<Alien>();
	private BufferStrategy strategy;
	private static boolean initialise = false;
	private Thread t;
	private static final int NUM_ALIENS_rows = 4,NUM_ALIENS_columns =5;
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
		
		
		for (int j = 0; j < NUM_ALIENS_rows; j++) { //Used this to set up aliens in matrix
				for (int i = 0; i < NUM_ALIENS_columns; i++) {
					aliens[j][i] = new Alien(this,alien,alien2);
					aliens[j][i].setPosition(i*60,(j)*50);
					aliens[j][i].setXSpeed(2);			
				} 
			}
		/**********************************************************************************************/
		for (int j = 0; j < NUM_ALIENS_rows; j++) {	//move aliens into arraylist so they can be iterated and manipulated easier
			for (int i = 0; i < NUM_ALIENS_columns; i++) {
				alienlist.add(aliens[j][i]);		
			} 
		}
		
	/**************************************************************************************************/
		ship = new Spaceship(spaceShip);
		ship.setPosition(wx/2-25, wy-70);
		bullets = new ArrayList<PlayerBullet>();
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
		new InvadersApplication();
	}
	public void paint(Graphics g){
		if(initialise){
		g = strategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,wx,wy);
		for(Alien a: alienlist){
			a.paint(g);
		}
		for(PlayerBullet b: bullets){
				b.paint(g);
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
				for (Alien a: alienlist) {	
						a.move();				
				}
				Iterator<PlayerBullet> iter = bullets.iterator();
				PlayerBullet b;
				while(iter.hasNext()){
					
						b = iter.next();
						b.move();
				
						
						if (b.collide(alienlist)!= null) {
							removeSprite(b.collide(alienlist));
							iter.remove();
						}
						
					
						
				}
				removebullets.clear();
				ship.move();
				this.repaint();
		
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
/**********************************************************************************************/
	//methods to change external classes: move alien sprites and shoot bullets
	
	public void update(){
		for(Alien a: alienlist){
			a.update();
		}
		
	}

public void shootBullet(){
	PlayerBullet bullet;
	bullet = new PlayerBullet(this,bulletImage);
	bullet.setPosition(ship.getX()+(ship.getWidth()*0.5 -3), ship.getY()+(ship.getHeight()/2));//put minus 5 in there because the image seemed not to
	bullet.setXSpeed(10);																//be symmetrical 
	bullets.add(bullet);
	
}
public void removeSprite(Sprite2D o){
	if(o instanceof PlayerBullet){
		removebullets.add((PlayerBullet)o);
	}
	if(o instanceof Alien)
		alienlist.remove(o);
	
}
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
			shootBullet();
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
