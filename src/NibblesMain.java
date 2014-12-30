import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;


public class NibblesMain implements ActionListener, KeyListener {

	private int miScore;
	private int miSnakeLength;
	private int miSnakeDirection;
	private int miTicks;
	private int miSpeed;

	private boolean mbGameOver; 
	private boolean mbGamePause;

	public ArrayList<Point> malSnakeParts;

	public NibblesMain snake;

	private Timer mTimer;

	public Point pHead;
	public Point pCherry;

	private RenderPanel mRenderPanel;

	private Dimension mDimension;
	private Toolkit mToolkit;
	private JFrame mjFrame;


	public NibblesMain() {

		initObjects();
		
		// Set frame properties
		mjFrame.setSize(Constants.WIDTH, Constants.HEIGHT);
		mjFrame.setResizable(false);
		mjFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mjFrame.add(mRenderPanel);
		mjFrame.setVisible(true);
		
		// Set listener for arrow keys to move the snake
		mjFrame.addKeyListener(this);

		// Initialize all components for the game
		startGame();
	}


	private void initObjects() {
		mDimension = Toolkit.getDefaultToolkit().getScreenSize();

		mToolkit = Toolkit.getDefaultToolkit();

		mjFrame = new JFrame("Nibbles");
		mRenderPanel = new RenderPanel(this);

		malSnakeParts = new ArrayList<Point>();

		pCherry = new Point();

		mTimer = new Timer(Constants.REFRESH_TIME, this);
	}


	/* Initialize all components required for a NEW GAME */
	private void startGame() {
		mRenderPanel.showPanel(Constants.GAME_ON);
		malSnakeParts.clear();

		miScore = 0;
		miSnakeLength = 3;
		miSnakeDirection = Constants.DOWN;
		miTicks = 0;
		miSpeed = 5;

		mbGameOver = false;
		mbGamePause = false;

		showSnake();

		showCherry();
		
		showScore();

		mTimer.start();
	}
	
	
	private void gameOver() {
		mRenderPanel.showPanel(Constants.GAME_OVER);
		mbGameOver = true;
		mTimer.stop();
	}


	/* Set co-ordinates to show snake */
	private void showSnake() { 
		
		// Snake should start from 0,0 in every game
		if(miScore == 0) {  
			pHead = new Point(0, 0);
		}
		
		if(malSnakeParts.isEmpty()) {
			// This is a new game, so add to the snake equal to the length
			for(int i = 0; i < miSnakeLength; i++) {
				malSnakeParts.add(new Point(pHead.x, pHead.y));
			}
		} else {
			// Snake has consumed a cherry, so add a new point to the snake
			malSnakeParts.add(new Point(pHead.x, pHead.y));
		}
	}
	
	
	/* Checks if snake collides with itself */
	private boolean didSnakeSelfCollide() {
		
		if(malSnakeParts.contains(pHead)) {
			return true;
		}
		
		return false;
	}
	
	
	private void showScore() {
		mRenderPanel.repaintScore(miScore);
	}
	

	//TODO: Improve on this
	/* Show a cherry at random co-ordinates on screen
	 * 
	 * The MAX_X and MAX_Y values is a guess work based on the Panel height and width.
	 * These would change if the panel height changes
	 */
	private void showCherry() {
		int MIN = 0;
		int MAX_X = (Constants.WIDTH/Constants.SCALE) - 3;
		int MAX_Y = (Constants.HEIGHT/Constants.SCALE) - 5;

		Random random = new Random();

		// Generate random number for X
		int x = random.nextInt(MAX_X - MIN) + MIN;

		// Generate random number for Y
		int y = random.nextInt(MAX_Y - MIN) + MIN;

		pCherry = new Point(x, y);
	}


	public static void main(String[] args) {
		new NibblesMain();
	}


	/* @Override - ActionListener */
	public void actionPerformed(ActionEvent e) {
		mRenderPanel.repaint();
		miTicks++;

		if(miTicks % miSpeed == 0 && !mbGamePause && !mbGameOver) {

			// Move sake to next point
			malSnakeParts.add(new Point(pHead.x, pHead.y));
			malSnakeParts.remove(0);

			if(miSnakeDirection == Constants.DOWN) {
				if (pHead.y != (Constants.HEIGHT/Constants.SCALE)) { 
					// Within wall boundaries
					pHead = new Point(pHead.x, pHead.y + 1);
				} else { 
					//Collided with wall
					gameOver();
				}
			}

			if(miSnakeDirection == Constants.UP) {
				if(pHead.y >= 0 ) { 
					// Within wall boundaries
					pHead = new Point(pHead.x, pHead.y - 1);
				} else {
					//Collided with wall
					gameOver();
				}
			}

			if(miSnakeDirection == Constants.RIGHT) {
				if(pHead.x != (Constants.WIDTH / Constants.SCALE)) { 
					// Within wall boundaries
					pHead = new Point(pHead.x+1,  pHead.y);
				} else {
					//Collided with wall
					gameOver();
				}
			}

			if(miSnakeDirection == Constants.LEFT) {
				if(pHead.x > 0 ) { 
					// Within wall boundaries
					pHead = new Point(pHead.x-1,  pHead.y);
				} else {
					//Collided with wall
					gameOver();
				}
			}

			if(didSnakeSelfCollide()) {
				gameOver();
			}
			
			// Check if cherry is eaten
			if(pHead.equals(pCherry)) {
				miScore++;				// 1. Increase score
				miSnakeLength++;		// 2. Increase snake size
				showSnake();			// 3. Repaint snake to show increased size
				showCherry();			// 4. Show cherry at new position
				showScore();			// 5. Show increased score
			}

		}
	}


	/* @Override - KeyListener 1 of 3 */
	public void keyPressed(KeyEvent event) {

		/* Use arrow keys to get these working
		 *         Up
		 *  Left  Down  Right 
		 */

		int i = event.getKeyCode();

		// Going Down
		if(i == KeyEvent.VK_DOWN && miSnakeDirection != Constants.UP) {
			miSnakeDirection = Constants.DOWN;
		}

		// Going Up
		if(i == KeyEvent.VK_UP && miSnakeDirection != Constants.DOWN) {
			miSnakeDirection = Constants.UP;
		}

		// Going Right
		if(i == KeyEvent.VK_RIGHT && miSnakeDirection != Constants.LEFT) {
			miSnakeDirection = Constants.RIGHT;
		}

		// Going Left
		if(i == KeyEvent.VK_LEFT && miSnakeDirection != Constants.RIGHT) {
			miSnakeDirection = Constants.LEFT;
		}
		
		// Pause / Resume / Restart Game
		if(i == KeyEvent.VK_SPACE) {
			if(!mbGamePause && !mbGameOver) {
				// Pause game
				mbGamePause = true;
				mTimer.stop();
				mRenderPanel.showPanel(Constants.GAME_PAUSE);
			} else if(mbGamePause && !mbGameOver){ 
				// Resume Game
				mbGamePause = false;
				mTimer.restart();
				mRenderPanel.showPanel(Constants.GAME_ON);
			} else if(mbGameOver) {
				// Restart Game
				startGame();
			}
		}

	}


	/* @Override - KeyListener 2 of 3 */
	public void keyReleased(KeyEvent event) { }


	/* @Override - KeyListener 3 of 3 */
	public void keyTyped(KeyEvent event) { }

}
