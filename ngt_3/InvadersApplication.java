package ngt_3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class InvadersApplication extends JFrame implements Runnable,KeyListener{
	private Image alien1,spaceShip,alien2,bulletImage,logo;
	private static int score = 0,waveNum= 0;
	private final static int WAVE_MAX = 10;
	private ArrayList<PlayerBullet> removebullets = new ArrayList<PlayerBullet>();
	private ArrayList<PlayerBullet> bullets;
	private ArrayList<Alien> alienlist = new ArrayList<Alien>();
	private BufferStrategy strategy;
	private static boolean setupInitialise = false,gameWin = false,gameOver=false,wait_press = true;
	private long fireInterval=500,lastFire = 0;
	private static final int NUM_ALIENS_rows = 4,NUM_ALIENS_columns =5;
	private static final Dimension WINDOW_SIZE = new Dimension(600,700);
	private static Dimension screensize;
	private static String directory;
	private Alien[][] aliens = new Alien[NUM_ALIENS_rows][NUM_ALIENS_columns];
	private Spaceship ship;
	
	private int wx,wy;
	private boolean firePressed = false; 
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
		
		
		
	/**************************************************************************************************/
		initSprites(2);
		addKeyListener(this);
	
		//creating a buffer strategy and signaling completion of constructor
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		setupInitialise = true; //make sure everything is initialize before we try and paint
		gameLoop();
	}

/***********************************************************************************************/
//Main method and paint method
	public static void main(String[] args) {
		directory = System.getProperty("user.dir");
		InvadersApplication game = new InvadersApplication();
	}
	public void paint(Graphics g){
		if(setupInitialise){
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
					score();
					g.dispose();
					strategy.show();
		
		}
	
		
	}
/***********************************************************************************************/
	public void gameLoop(){
		//startMenu();
		Thread t;
		t = new Thread(this);
		t.start();
	
			
	}
	public void score(){
		Graphics g = strategy.getDrawGraphics();
     
        g.setColor(Color.white);
        Font font = new Font("Courier New",Font.BOLD,30);
        g.setFont(font);
        g.drawString("Score: "+score,70,50); 
       
        strategy.show();
	}
	public void startMenu(){
		logo = new ImageIcon(directory + "\\logo2.png").getImage();
		Graphics g = strategy.getDrawGraphics();
	        g.setColor(Color.black);
	        g.fillRect(0, 0, wx, wy);
	        g.drawImage(logo, wx/2 - 150 , wy/2 - 75,300,150,null);
	        g.setColor(Color.white);
	        Font font = new Font("Courier New",Font.BOLD,30);
	        g.setFont(font);
	        g.drawString("press any key to play...",100,wy-100); 
	       
	        strategy.show();
		
	}
	public void gameOver(){
		
		if(gameOver){
			Graphics g = strategy.getDrawGraphics();
	        g.setColor(Color.black);
	        g.fillRect(0, 0, wx, wy);
	        g.setColor(Color.white);
	        Font font = new Font("SANS_SERIF",Font.BOLD,50);
	        g.setFont(font);
	        g.drawString("Game Over!", 50, wy/2 +50);
	        strategy.show();
		}
		if(gameWin){
			waveNum++;
			Graphics g = strategy.getDrawGraphics();
			if(waveNum >WAVE_MAX){
			 g.setColor(Color.black);
		        g.fillRect(0, 0, wx, wy);
		        g.setColor(Color.white);
		        Font font = new Font("SANS_SERIF",Font.BOLD,50);
		        g.setFont(font);
		        g.drawString("You win! congrats", 50, 
		 	           wy/2 +50);
		        strategy.show();
		        return;
			}
			System.out.print("Finished init");
			gameWin=false;
			initSprites(waveNum+5);
			gameLoop();
			
			
		}
	}
			
//Run method for thread that constantly updates the game
	@Override
	public void run() {
		
		while(true){
			
			if(wait_press){
				startMenu();
				break;
			}
			try {
				
				Thread.sleep(50);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
	
		if(gameOver){
			gameOver();
			break;
		}
		if(gameWin){
			gameOver();
			break;
		}
		removebullets.clear();
		if(firePressed){
			shootBullet();
		}
		if(ship.collide(alienlist)){
			gameOver = true;
		}
		ship.move();
		this.repaint();
	
		}
		
		
	}
/**********************************************************************************************/
	//methods to change external classes: move alien sprites and shoot bullets
	
	public void update(){
		for(Alien a: alienlist){
			a.update();
		}
		
	}

void initSprites(int waveSpeed){
	alien1 = new ImageIcon(directory + "\\Spaceinvaders.png").getImage();
	alien2 = new ImageIcon(directory + "\\alien2.png").getImage();
	spaceShip = new ImageIcon(directory + "\\player_ship.png").getImage();
	bulletImage = new ImageIcon(directory + "\\bullet.png").getImage();
	ship = new Spaceship(spaceShip);
	ship.setPosition(wx/2-25, wy-70);
	bullets = new ArrayList<PlayerBullet>();
	Sprite2D.setWindowWidth(wx);
	
			for (int j = 0; j < NUM_ALIENS_rows; j++) {	//move aliens into arraylist so they can be iterated and manipulated easier
				for (int i = 0; i < NUM_ALIENS_columns; i++){
					Alien alien = new Alien(this,alien1,alien2);
					alien.setPosition(i*60,(j)*50);
					alien.setXSpeed(waveSpeed);
					alienlist.add(alien);		
				} 
			}
			
}

public void shootBullet(){
	if(System.currentTimeMillis() - lastFire < fireInterval){
		return;
	}
	lastFire = System.currentTimeMillis();	
	PlayerBullet bullet;
	bullet = new PlayerBullet(this,bulletImage);
	bullet.setPosition(ship.getX()+(ship.getWidth()*0.5 -3), ship.getY()+(ship.getHeight()/2));//put minus 5 in there because the image seemed not to
	bullet.setXSpeed(10);																//be symmetrical 
	bullets.add(bullet);
	firePressed=false;
	
}
public void removeSprite(Sprite2D o){
	if(o instanceof PlayerBullet){
		removebullets.add((PlayerBullet)o);
	}
	if(o instanceof Alien){
		alienlist.remove(o);
		if(alienlist.size()<=0){
			gameWin=true;
			System.out.println("gamewon");
		}
	}
	
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
			firePressed  = true;
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
		if(wait_press){
			wait_press = false;
			gameLoop();
		}
		
	}
}
